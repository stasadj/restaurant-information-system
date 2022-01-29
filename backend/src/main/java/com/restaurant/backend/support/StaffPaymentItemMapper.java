package com.restaurant.backend.support;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.restaurant.backend.domain.StaffPaymentItem;
import com.restaurant.backend.dto.StaffPaymentItemDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StaffPaymentItemMapper extends GenericObjectMapper<StaffPaymentItem, StaffPaymentItemDTO> {
	private ModelMapper modelMapper;

	@Override
	public StaffPaymentItemDTO convert(StaffPaymentItem staffPaymentItem) {
		return modelMapper.map(staffPaymentItem, StaffPaymentItemDTO.class);
	}

}
