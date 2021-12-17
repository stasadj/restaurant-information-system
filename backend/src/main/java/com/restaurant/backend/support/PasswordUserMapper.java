package com.restaurant.backend.support;

import com.restaurant.backend.domain.Admin;
import com.restaurant.backend.domain.Manager;
import com.restaurant.backend.domain.PasswordUser;
import com.restaurant.backend.dto.PasswordUserDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordUserMapper extends GenericObjectMapper<PasswordUser, PasswordUserDTO> {
    private ModelMapper modelMapper;

    @Override
    public PasswordUserDTO convert(PasswordUser passwordUser) {
        PasswordUserDTO dto = modelMapper.map(passwordUser, PasswordUserDTO.class);
        dto.setPassword(null); // #security
        return dto;
    }

    public PasswordUser convertToDomain(PasswordUserDTO dto) {
        switch (dto.getRole()) {
            case "admin":
                return modelMapper.map(dto, Admin.class);
            case "manager":
                return modelMapper.map(dto, Manager.class);
            default:
                return null;
        }
    }
}