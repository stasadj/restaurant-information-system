package com.restaurant.backend.support;

import com.restaurant.backend.domain.Staff;
import com.restaurant.backend.dto.StaffDTO;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StaffMapper extends GenericObjectMapper<Staff, StaffDTO> {
    private ModelMapper modelMapper;

    @Override
    public StaffDTO convertToDto(Staff staff) {
        StaffDTO dto = modelMapper.map(staff, StaffDTO.class);
        return dto;
    }

    @Override
    public Staff convertToDomain(StaffDTO dto) {
        Staff staff = modelMapper.map(dto, Staff.class);
        return staff;
    }

}
