package com.restaurant.backend.dto.requests;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class OrderItemIds {

    @NotEmpty(message = "There must be at least 1 order item id.")
    public List<Long> ids;
}
