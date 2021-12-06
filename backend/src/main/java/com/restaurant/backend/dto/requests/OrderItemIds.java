package com.restaurant.backend.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemIds {

    @NotEmpty(message = "There must be at least 1 order item id.")
    private List<Long> ids;
}
