package com.restaurant.backend.support;

import com.restaurant.backend.domain.User;
import com.restaurant.backend.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper extends GenericObjectMapper<User, UserDTO> {
    private ModelMapper modelMapper;

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User convertToDomain(UserDTO dto) {
        User user = modelMapper.map(dto, User.class);
        return user;
    }
}
