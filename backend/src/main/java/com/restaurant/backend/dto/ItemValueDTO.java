package com.restaurant.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.restaurant.backend.domain.ItemValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemValueDTO {
    
    protected Long id;

    protected BigDecimal purchasePrice;

    protected BigDecimal sellingPrice;

    protected LocalDate fromDate;

    public static ItemValue toObject(ItemValueDTO dto){
        ItemValue itemValue = new ItemValue();
        itemValue.setId(dto.getId());
        itemValue.setPurchasePrice(dto.getPurchasePrice());
        itemValue.setSellingPrice(dto.getSellingPrice());
        itemValue.setFromDate(dto.getFromDate());
        return itemValue;

    }
}
