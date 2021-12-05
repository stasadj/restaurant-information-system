package com.restaurant.backend.constants;

import com.restaurant.backend.domain.*;
import com.restaurant.backend.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

public class OrderItemServiceTestConstants {
    public static final Cook A_COOK = new Cook();
    public static final Barman A_BARMAN = new Barman();
    public static final Order AN_ORDER = new Order();
    public static final Item A_FOOD_ITEM = new Item(1L, "Food", new Category(), "", null,
            new ArrayList<>(), true, List.of(new ItemValue()), ItemType.FOOD, false);
    public static final Item A_DRINK_ITEM = new Item(2L, "Drink", new Category(), "", null,
            new ArrayList<>(), true, List.of(new ItemValue()), ItemType.DRINK, false);
}
