package com.restaurant.backend.support;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends GenericObjectMapper<User, UserDTO> {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        return dto;
    }

    @Override
    public User convertToDomain(UserDTO dto) {
        User user = modelMapper.map(dto, User.class);
        return user;
    }
}
