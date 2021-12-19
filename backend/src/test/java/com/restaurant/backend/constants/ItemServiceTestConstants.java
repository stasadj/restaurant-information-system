
package com.restaurant.backend.constants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.dto.requests.ChangePriceDTO;

public final class ItemServiceTestConstants {

    public static final Long CATEGORY_ID = 2L;
    public static final Category VALID_CATEGORY = new Category(CATEGORY_ID, null);
    public static final Long NONEXISTENT_CATEGORY_ID = 1111L;

    public static final Long TAG1_ID = 1L;
    public static final Long TAG2_ID = 2L;
    public static final Tag VALID_TAG1 = new Tag(TAG1_ID, null);
    public static final Tag VALID_TAG2 = new Tag(TAG2_ID, null);
    public static final Long NONEXISTENT_TAG_ID = 1111L;

    public static final String ITEM_NAME = "Potato salad";
    public static final String ITEM_DESCRIPTION = "Potato and egg salad dressed with vinegar with a side of red oniom";
    public static final String ITEM_URL = "";
    public static final Long NONEXISTENT_ITEM_ID = 222222L;

    public static final BigDecimal ITEM_PURCHASE_PRICE = new BigDecimal(505.5);
    public static final BigDecimal ITEM_SELLING_PRICE = new BigDecimal(605.5);

    public static final ItemValue VALID_ITEM_VALUE = new ItemValue(null, ITEM_PURCHASE_PRICE, ITEM_SELLING_PRICE,
            LocalDateTime.now().minusMonths(1), null);

    public static final Item VALID_ITEM = new Item(null, ITEM_NAME, VALID_CATEGORY, ITEM_DESCRIPTION, null,
            List.of(VALID_TAG1, VALID_TAG2), true, List.of(VALID_ITEM_VALUE), ItemType.FOOD, false);

    public static final Long VALID_ITEM_ID = 1L;
    public static final Item EXISTENT_ITEM = new Item(VALID_ITEM_ID, ITEM_NAME, VALID_CATEGORY, ITEM_DESCRIPTION, null,
            List.of(VALID_TAG1, VALID_TAG2), true, List.of(VALID_ITEM_VALUE), ItemType.FOOD, false);

    public static final Long NULL_ID = null;

    public static final ChangePriceDTO NEW_ITEM_VALUE_DTO = new ChangePriceDTO(BigDecimal.valueOf(1000.5),
            BigDecimal.valueOf(1500.5), LocalDateTime.now().plusMonths(2), VALID_ITEM_ID);
    public static final ItemValue NEW_ITEM_VALUE = new ItemValue(null, NEW_ITEM_VALUE_DTO.getPurchasePrice(),
            NEW_ITEM_VALUE_DTO.getSellingPrice(), NEW_ITEM_VALUE_DTO.getFromDate(), VALID_ITEM);

}