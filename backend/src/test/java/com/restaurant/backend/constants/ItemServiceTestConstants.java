
package com.restaurant.backend.constants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.Tag;
import com.restaurant.backend.domain.enums.ItemType;


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
    public static final BigDecimal ITEM_SELLING_PRICE = new  BigDecimal(605.5);

    public static final Item VALID_ITEM = generateValidItem();
    public static final Long VALID_ITEM_ID = 1L;
    public static final Item EXISTENT_ITEM = generateValidItemWithId(); 

    public static final Long NULL_ID = null;


    public static Item generateValidItem() {
        Item item = new Item();
        item.setName(ITEM_NAME);
        item.setDescription(ITEM_DESCRIPTION);
        item.setImageURL(ITEM_URL); 
        item.setInMenu(true);
        item.setItemType(ItemType.FOOD);

        item.setCategory(VALID_CATEGORY);

        ItemValue itemValue = new ItemValue();
        itemValue.setFromDate(LocalDateTime.now());
        itemValue.setPurchasePrice(ITEM_PURCHASE_PRICE);
        itemValue.setSellingPrice(ITEM_SELLING_PRICE);

        item.setItemValues(new ArrayList<>() {
            {
                add(itemValue);
            }
        });

        item.setTags(new ArrayList<>() {
            {
                add(VALID_TAG1);
                add(VALID_TAG2);
            }
        });
        return item;
    }

    public static Item generateValidItemWithId(){
        Item item = generateValidItem();
        item.setId(VALID_ITEM_ID);
        return item;
    }




}