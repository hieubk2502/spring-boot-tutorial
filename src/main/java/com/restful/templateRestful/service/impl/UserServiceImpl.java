package com.restful.templateRestful.service.impl;

import com.restful.templateRestful.dto.request.UserRequestDTO;
import com.restful.templateRestful.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public int addUser(UserRequestDTO userRequestDTO) {

        if (userRequestDTO.getFirstName().equals("HIEU")){
            throw new ResourceNotFoundException("firstName: 'Hieu' not exist!");
        }
        return new Random().nextInt();
    }
}
