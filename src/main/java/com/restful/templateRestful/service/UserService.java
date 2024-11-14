package com.restful.templateRestful.service;

import com.restful.templateRestful.dto.request.UserRequestDTO;
import com.restful.templateRestful.dto.response.PageResponse;
import com.restful.templateRestful.dto.response.UserDetailResponse;
import com.restful.templateRestful.util.UserStatus;

import java.awt.print.Pageable;

public interface UserService {

    int addUser(UserRequestDTO userRequestDTO);

    long saveUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(long userId);

    UserDetailResponse getUser(long userId);

    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, String... sorts);

    PageResponse<?> getAllUsersWithSortByColumnsAndSearch(int pageNo, int pageSize, String search, String sortBy);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search);

//    PageResponse<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address);
}

