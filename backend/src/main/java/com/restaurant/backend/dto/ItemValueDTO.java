package com.restaurant.backend.dto;

import com.restaurant.backend.domain.ItemValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemValueDTO {
    
    protected Long id;

    @Digits(integer = 11, fraction = 2, message = "Item purchase price can contain a maximum of 11 digits and 2 in fraction")
    @Positive(message = "Item purchase price must be a positive number")
    @NotNull(message = "Item purchase price is missing.")
    protected BigDecimal purchasePrice;

    @Digits(integer = 11, fraction = 2, message = "Item selling price can contain a maximum of 11 digits and 2 in fraction")
    @Positive(message = "Item selling price must be a positive number")
    @NotNull(message = "Item selling price is missing.")
    protected BigDecimal sellingPrice;

    protected LocalDateTime fromDate;

    public static ItemValue toDomain(ItemValueDTO dto) {
        ItemValue itemValue = new ItemValue();
        itemValue.setId(dto.getId());
        itemValue.setPurchasePrice(dto.getPurchasePrice());
        itemValue.setSellingPrice(dto.getSellingPrice());
        itemValue.setFromDate(dto.getFromDate());
        return itemValue;
    }

    public ItemValueDTO(ItemValue itemValue) {
        id = itemValue.getId();
        purchasePrice = itemValue.getPurchasePrice();
        sellingPrice = itemValue.getSellingPrice();
        fromDate = itemValue.getFromDate();
    }
}
