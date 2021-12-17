package com.restaurant.backend.support;

import com.restaurant.backend.domain.Barman;
import com.restaurant.backend.domain.Cook;
import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.domain.Waiter;
import com.restaurant.backend.dto.StaffDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StaffMapper extends GenericObjectMapper<Staff, StaffDTO> {
    private ModelMapper modelMapper;

    @Override
    public StaffDTO convert(Staff staff) {
        return modelMapper.map(staff, StaffDTO.class);
    }

    public Staff convertToDomain(StaffDTO dto) {
        switch (dto.getRole()) {
            case "waiter":
                return modelMapper.map(dto, Waiter.class);
            case "cook":
                return modelMapper.map(dto, Cook.class);
            case "barman":
                return modelMapper.map(dto, Barman.class);
            default:
                return null;
        }
    }
}
