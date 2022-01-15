package com.restaurant.backend.service.unit;

import com.restaurant.backend.constants.StaffServiceTestConstants;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.Order;
import com.restaurant.backend.domain.OrderItem;
import com.restaurant.backend.domain.Waiter;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.domain.enums.OrderStatus;
import com.restaurant.backend.dto.OrderDTO;
import com.restaurant.backend.dto.OrderItemDTO;
import com.restaurant.backend.exception.BadRequestException;
import com.restaurant.backend.repository.OrderRepository;
import com.restaurant.backend.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
public class OrderServiceUnitTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private OrderItemService orderItemService;
    @MockBean
    private StaffService staffService;
    @MockBean
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create_tableHasAnOrder() {
        List<OrderItemDTO> items = new ArrayList<>();
        OrderItemDTO item = new OrderItemDTO(1L, 2, 1L, OrderStatus.PENDING, 1L, null, null, null);
        items.add(item);
        OrderDTO orderDTO = new OrderDTO(1L, LocalDateTime.now(), "note", 1, items, 1L);

        when(orderRepository.findByTableId(eq(orderDTO.getTableId()))).thenReturn(Optional.of(new Order()));

        assertThrows(BadRequestException.class, () -> orderService.create(orderDTO), "BadRequestException was expected");
    }

    @Test
    public void create() {
        Waiter waiter = generateWaiter();
        List<OrderItemDTO> items = new ArrayList<>();
        OrderItemDTO itemDTO = new OrderItemDTO(1L, 2, 1L, OrderStatus.PENDING, 1L, null, null, null);
        items.add(itemDTO);
        OrderDTO orderDTO = new OrderDTO(1L, LocalDateTime.now(), "note", 1, items, 1L);

        Item item = new Item(1L, "Pizza", null, "", "", null, true, null, ItemType.FOOD, false);
        Order order = new Order(LocalDateTime.now(), orderDTO.getNote(), orderDTO.getTableId(), waiter);

        when(staffService.findOne(eq(orderDTO.getWaiterId()))).thenReturn(waiter);
        when(orderRepository.findByTableId(eq(orderDTO.getTableId()))).thenReturn(Optional.empty());
        when(itemService.findOne(eq(itemDTO.getItemId()))).thenReturn(item);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderService.findAllForWaiter(eq(orderDTO.getWaiterId()))).thenReturn(Collections.singletonList(order));
        doNothing().when(orderItemService).save(any(OrderItem.class));

        Order result = orderService.create(orderDTO);

        assertEquals(1, result.getTableId());
        assertSame(waiter, result.getWaiter());

        verify(staffService, times(1)).findOne(anyLong());
        verify(orderRepository, times(1)).findByTableId(anyInt());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemService, times(1)).save(any(OrderItem.class));
        verify(itemService, times(1)).findOne(anyLong());
    }

    @Test
    public void editOrder() {
        List<OrderItemDTO> itemsEdited = new ArrayList<>();
        OrderItemDTO item1 = new OrderItemDTO(1L, 2, 1L, OrderStatus.PENDING, 1L, null, null, null);
        OrderItemDTO item2 = new OrderItemDTO(null, 2, 1L, OrderStatus.PENDING, 1L, null, null, null);
        itemsEdited.add(item1);
        itemsEdited.add(item2);
        OrderDTO orderDTO = new OrderDTO(1L, LocalDateTime.now(), "note", 1, itemsEdited, 1L);

        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        when(orderRepository.findById(eq(orderDTO.getId()))).thenReturn(Optional.of(order));
        when(itemService.findOne(eq(item1.getItemId()))).thenReturn(new Item());
        when(itemService.findOne(eq(item2.getItemId()))).thenReturn(new Item());
        when(orderItemService.findOne(eq(1L))).thenReturn(new OrderItem(){{setAmount(1);}});
        doNothing().when(orderItemService).save(any(OrderItem.class));

        orderService.editOrder(orderDTO);

        verify(itemService, times(1)).findOne(anyLong());
        verify(orderItemService, times(2)).save(any(OrderItem.class));
        verify(orderItemService, times(1)).findOne(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    // need to write additional tests for editOrder

    @Test
    public void finalizeOrder_orderItemNotReady() {
       OrderItem item = new OrderItem();
       item.setOrderStatus(OrderStatus.IN_PROGRESS);
       Order order = new Order();
       order.setOrderItems(Collections.singletonList(item));

       when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(BadRequestException.class, () -> orderService.finalizeOrder(1L), "BadRequestException was expected");
    }

    private Waiter generateWaiter() {
        Waiter waiter = new Waiter();
        waiter.setFirstName(StaffServiceTestConstants.STAFF_FIRST_NAME);
        waiter.setLastName(StaffServiceTestConstants.STAFF_LAST_NAME);
        waiter.setPin(StaffServiceTestConstants.STAFF_PIN);
        waiter.setMonthlyWage(StaffServiceTestConstants.STAFF_WAGE);
        waiter.setPhoneNumber(StaffServiceTestConstants.STAFF_PHONE_NUMBER);
        return waiter;
    }
}
