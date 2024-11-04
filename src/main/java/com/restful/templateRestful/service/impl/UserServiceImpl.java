package com.restful.templateRestful.service.impl;

import com.restful.templateRestful.dto.request.UserRequestDTO;
import com.restful.templateRestful.dto.response.UserDetailResponse;
import com.restful.templateRestful.exception.ResourceNotFoundException;
import com.restful.templateRestful.model.Address;
import com.restful.templateRestful.model.User;
import com.restful.templateRestful.repository.UserRepository;
import com.restful.templateRestful.service.UserService;
import com.restful.templateRestful.util.UserStatus;
import com.restful.templateRestful.util.UserType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    UserRepository userRepository;

    @Override
    public int addUser(UserRequestDTO userRequestDTO) {

        if (userRequestDTO.getFirstName().equals("HIEU")){
            throw new ResourceNotFoundException("firstName: 'Hieu' not exist!");
        }
        return new Random().nextInt();
    }

    @Override
    public long saveUser(UserRequestDTO request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .phone(request.getPhone())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .status(request.getStatus())
                .type(UserType.valueOf(request.getType().toUpperCase()))
                .build();
        request.getAddresses().forEach(a ->
                user.saveAddress(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build()));

        userRepository.save(user);

        log.info("User has save!");

        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {

    }

    @Override
    public void changeStatus(long userId, UserStatus status) {

    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public UserDetailResponse getUser(long userId) {
        return null;
    }
}
