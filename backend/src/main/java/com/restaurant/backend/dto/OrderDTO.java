package com.restaurant.backend.dto;

import com.restaurant.backend.validation.interfaces.CreateInfo;
import com.restaurant.backend.validation.interfaces.EditInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    @NotNull(message = "Id cannot be null.", groups = EditInfo.class)
    @Null(message = "Id should be null.", groups = CreateInfo.class)
    private Long id;

    private LocalDateTime createdAt;

    private String note;

    @NotNull(message = "Table id cannot be null.")
    private Integer tableId;

    @NotEmpty(message = "There must be at least 1 order item.")
    private List<@Valid OrderItemDTO> orderItems;

    @NotNull(message = "Waiter id cannot be null.", groups = CreateInfo.class)
    private Long waiterId;
}
