package com.restful.templateRestful.repository;

import com.restful.templateRestful.dto.response.PageResponse;
import com.restful.templateRestful.model.Address;
import com.restful.templateRestful.model.User;
import com.restful.templateRestful.repository.criteria.SearchCriteria;
import com.restful.templateRestful.repository.criteria.UserSearchQueryCriteriaConsumer;
import com.restful.templateRestful.util.AppConst;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.restful.templateRestful.util.AppConst.SEARCH_OPERATOR;
import static com.restful.templateRestful.util.AppConst.SORT_BY;

@Component
@Slf4j
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String LIKE_FORMAT = "%%%s%%";

    // jpa, hibernate, criteria, specification

    public PageResponse<?> searchUser(int pageNo, int pageSize, String address, String sortBy, String... search) {

        // 1 lay ra danh sach user

        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (search != null) {
            for (String s : search) {
                // firstName:value

                Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }

            }
        }

        // lay ra so luong ban ghi

        List<User> users = getUsers(pageNo, pageSize, address, criteriaList, sortBy);

        Long totalElements = getTotalElements(criteriaList, address);

        return PageResponse.builder()
                .page(pageNo) // vi tri ban ghi trong danh sach
                .size(users.size())
                .total(totalElements.intValue())
                .items(users)
                .build();

    }

    private List<User> getUsers(int pageNo, int pageSize, String address, List<SearchCriteria> criteriaList, String sortBy) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Root<User> root = query.from(User.class);

        // xu ly cac dieu kien tim kiem
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer queryConsumer = new UserSearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);

        if (StringUtils.hasLength(address)) {
            Join<Address, User> addressUserJoin = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.equal(addressUserJoin.get("city"), address );
            query.where(predicate, addressPredicate);

        } else {
            criteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.where(predicate);
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
                }
            }
        }
        return entityManager
                .createQuery(query)
                .setFirstResult(pageNo)
                .setMaxResults(pageSize)
                .getResultList();

    }

    private Long getTotalElements(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);

        if(StringUtils.hasLength(address)){
            Join<Address, User> addressUserJoin = root.join("addresses");
            Predicate addressUserPredicate = criteriaBuilder.like(addressUserJoin.get("city"), address);
            // tim kiem tren thuoc tinh address
            query.select(criteriaBuilder.count(root));
            query.where(predicate,addressUserPredicate);
        }else {
            criteriaList.forEach(searchConsumer);
            query.select(criteriaBuilder.count(root));
            query.where(predicate);
        }

        return entityManager.createQuery(query).getSingleResult();
    }


    public PageResponse<?> getAllUsersWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {
        log.info("Execute search user with keyword={}", search);

        StringBuilder sqlQuery = new StringBuilder("SELECT new com.restful.templateRestful.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName, u.phone, u.email) FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" AND lower(u.firstName) like lower(:firstName)");
            sqlQuery.append(" OR lower(u.lastName) like lower(:lastName)");
            sqlQuery.append(" OR lower(u.email) like lower(:email)");
        }

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(AppConst.SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        // Get list of users
        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("firstName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("lastName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("email", String.format(LIKE_FORMAT, search));
        }
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        List<?> users = selectQuery.getResultList();


        // Count users
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" WHERE lower(u.firstName) like lower(?1)");
            sqlCountQuery.append(" OR lower(u.lastName) like lower(?2)");
            sqlCountQuery.append(" OR lower(u.email) like lower(?3)");
        }

        Query countQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(3, String.format(LIKE_FORMAT, search));
            countQuery.getSingleResult();
        }

        Long totalElements = (Long) countQuery.getSingleResult();

        log.info("totalElements={}", totalElements);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<?> page = new PageImpl<>(users, pageable, totalElements);

        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(page.getTotalPages())
                .items(page.stream().toList())
                .build();
    }

    /**
     * Advance search user by criterias
     *
     * @param offset
     * @param pageSize
     * @param sortBy
     * @param search
     * @return
     */
    public PageResponse<?> searchUserByCriteria(int offset, int pageSize, String sortBy, String address, String... search) {
        log.info("Search user with search={} and sortBy={}", search, sortBy);

        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (search.length > 0) {
            Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
            for (String s : search) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            for (String s : search) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<User> users = getUsers(offset, pageSize, criteriaList, address, sortBy);

        Long totalElements = getTotalElements(criteriaList);

        Page<User> page = new PageImpl<>(users, PageRequest.of(offset, pageSize), totalElements);

        return PageResponse.builder()
                .page(offset)
                .size(pageSize)
                .total(page.getTotalPages())
                .items(users)
                .build();
    }

    private List<User> getUsers(int offset, int pageSize, List<SearchCriteria> criteriaList, String address, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);

        Predicate userPredicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(userPredicate, criteriaBuilder, userRoot);

        if (StringUtils.hasLength(address)) {
            Join<Address, User> userAddressJoin = userRoot.join("addresses");
            Predicate addressPredicate = criteriaBuilder.equal(userAddressJoin.get("city"), address);
            query.where(userPredicate, addressPredicate);
        } else {
            criteriaList.forEach(searchConsumer);
            userPredicate = searchConsumer.getPredicate();
            query.where(userPredicate);
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String fieldName = matcher.group(1);
                String direction = matcher.group(3);
                if (direction.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(userRoot.get(fieldName)));
                } else {
                    query.orderBy(criteriaBuilder.desc(userRoot.get(fieldName)));
                }
            }
        }

        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }

    /**
     * Count users with conditions
     *
     * @param params
     * @return
     */
    private Long getTotalElements(List<SearchCriteria> params) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(predicate, criteriaBuilder, root);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.select(criteriaBuilder.count(root));
        query.where(predicate);

        return entityManager.createQuery(query).getSingleResult();
    }
}
