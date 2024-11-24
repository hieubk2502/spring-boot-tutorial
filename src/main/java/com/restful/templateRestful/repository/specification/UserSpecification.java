package com.restful.templateRestful.repository.specification;

import com.restful.templateRestful.model.User;
import com.restful.templateRestful.util.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSpecification implements Specification <User>{

    SpecSearchCriteria criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<User> root,@NonNull CriteriaQuery<?> query,@NonNull CriteriaBuilder builder) {

        return switch (criteria.getOperation()) {
            case EQUALITY -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()),criteria.getValue().toString());
            case LIKE -> builder.like(root.get(criteria.getKey()), "%"+ criteria.getValue().toString()+"%");
            case STARTS_WITH -> builder.like(root.get(criteria.getKey()), criteria.getValue() +"%");
            case ENDS_WITH -> builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> builder.like(root.get(criteria.getKey()), "%" +criteria.getValue() +"%");
        };
    }
}
