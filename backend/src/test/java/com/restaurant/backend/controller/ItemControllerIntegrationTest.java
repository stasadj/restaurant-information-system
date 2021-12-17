package com.restaurant.backend.controller;

import static com.restaurant.backend.constants.ItemServiceTestConstants.*;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.dto.ItemDTO;
import com.restaurant.backend.dto.TagDTO;
import com.restaurant.backend.support.ItemMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:item_service_integration.sql")
@WithMockUser(authorities = { "ROLE_MANAGER" })
@Transactional
public final class ItemControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemMapper itemMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void getById_successful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/" + VALID_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Spaghetti carbonara"));

    }

    @Test
    public void findOne_invalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/" + NONEXISTENT_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    public void getAllMenuItems() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/in-menu"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].inMenu").value(hasItem(true)));
    }

    @Test
    public void addToMenu_successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/add-to-menu/" + VALID_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inMenu").value(true));
    }

    @Test
    public void addToMenu_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/add-to-menu/" + NONEXISTENT_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void removeFromMenu_successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/remove-from-menu/" + VALID_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inMenu").value(false));
    }

    @Test
    public void removeFromMenu_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/remove-from-menu/" + NONEXISTENT_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteItem_successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/" + VALID_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteItem_unsuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item/" + NONEXISTENT_ITEM_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createItem() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemMapper.convert(VALID_ITEM))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(VALID_ITEM.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(VALID_ITEM.getDescription()));

    }

    @Test
    public void createItem_invalidCategory() throws Exception {

        ItemDTO itemDTO = itemMapper.convert(VALID_ITEM);
        itemDTO.getCategory().setId(NONEXISTENT_CATEGORY_ID);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/item/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void createItem_invalidTags() throws Exception {

        ItemDTO itemDTO = itemMapper.convert(VALID_ITEM);

        itemDTO.setTags(new ArrayList<>() {
            {
                add(new TagDTO(NONEXISTENT_TAG_ID, null));
            }
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    public void updateItem() throws Exception {

    ItemDTO updatedItemDTO = itemMapper.convert(EXISTENT_ITEM);
    String VALUE_FOR_CONCAT = "ABC";
    updatedItemDTO.setName(updatedItemDTO.getName() + VALUE_FOR_CONCAT);
    updatedItemDTO.setDescription(updatedItemDTO.getDescription() +
    VALUE_FOR_CONCAT);
    // updatedItem.setImageURL(); //TODO write separate tests for image upload
    updatedItemDTO.setItemType(ItemType.DRINK);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/item/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedItemDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(VALID_ITEM.getName() + VALUE_FOR_CONCAT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(VALID_ITEM.getDescription() + VALUE_FOR_CONCAT));

    }

    @Test
    public void updateItem_missingId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VALID_ITEM)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateItem_invalidCategory() throws Exception {

        ItemDTO existantItemDTO = itemMapper.convert(EXISTENT_ITEM); 
        existantItemDTO.getCategory().setId(NONEXISTENT_CATEGORY_ID);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existantItemDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void updateItem_invalidTags() throws Exception {

        ItemDTO existantItemDTO = itemMapper.convert(EXISTENT_ITEM);

        existantItemDTO.setTags(new ArrayList<>() {
            {
                add(new TagDTO(NONEXISTENT_TAG_ID, null));
            }
        });

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item/edit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(existantItemDTO)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void changePrice() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/item/change-price")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(NEW_ITEM_VALUE_DTO)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.sellingPrice").value(NEW_ITEM_VALUE_DTO.getSellingPrice()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.purchasePrice").value(NEW_ITEM_VALUE_DTO.getPurchasePrice()));

    }       


    //TODO: add more change price tests

}
