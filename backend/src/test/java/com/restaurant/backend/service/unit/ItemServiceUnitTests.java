
package com.restaurant.backend.service.unit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.transaction.Transactional;

import com.restaurant.backend.domain.Category;
import com.restaurant.backend.domain.Item;
import com.restaurant.backend.domain.ItemValue;
import com.restaurant.backend.domain.enums.ItemType;
import com.restaurant.backend.repository.ItemRepository;
import com.restaurant.backend.service.CategoryService;
import com.restaurant.backend.service.ItemService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;


@Transactional
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class ItemServiceUnitTests {
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Test
    public void createItem_successful() {
        Category category = new Category();
        category.setId(2L);

        when(categoryService.findOne(eq(2L)))
                .thenReturn(category);

        Item item = createDummyValidItem();
        itemService.create(item);
    }

    private Item createDummyValidItem() {
        Item item = new Item();
        item.setName("Potato salad");
        item.setDescription("Potato and egg salad dressed with vinegar with a side of red oniom");
        item.setImageURL(""); //TODO add test with correct and incorrect image urls when image upload is implemented
        item.setInMenu(true);
        item.setItemType(ItemType.FOOD);

        Category category = new Category();
        category.setId(2L);
        item.setCategory(category);

        ItemValue itemValue = new ItemValue();
        itemValue.setFromDate(LocalDateTime.now());
        itemValue.setPurchasePrice(new BigDecimal(500.5));
        itemValue.setSellingPrice(new BigDecimal(600.6));
        item.setItemValues(new ArrayList<>());

        item.setItemValues(new ArrayList<>() {{
            add(itemValue);
        }});

        item.setTags(new ArrayList<>());

        return item;
    }

    // @Test
    // public void create_saveStaffMember_conflictingPin() {
    //     Waiter staff = generateWaiter();

    //     // No pin is valid anymore
    //     when(staffRepository.findByPin(anyInt())).thenReturn(Optional.of(staff));

    //     BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
    //         staffService.create(staff);
    //     }, "BadRequestException was expected");

    //     assertEquals(String.format("Staff member with pin %d already exists.", staff.getPin()), thrown.getMessage());
    // }

    // @Test
    // public void changePin_changeStaffPin_successful() {
    //     Waiter staff = generateWaiter();
    //     when(staffRepository.findByPin(eq(StaffServiceTestConstants.STAFF_PIN)))
    //             .thenReturn(Optional.of(staff));

    //     ChangePinDTO dto = new ChangePinDTO();
    //     dto.setCurrentPin(StaffServiceTestConstants.STAFF_PIN);
    //     dto.setNewPin(StaffServiceTestConstants.OTHER_PIN);

    //     staffService.changePin(dto);
    // }

    // @Test
    // public void changePin_changeStaffPin_pinTaken() {
    //     Waiter staff = generateWaiter();
    //     Integer staffPin = StaffServiceTestConstants.STAFF_PIN;
    //     Integer otherPin = StaffServiceTestConstants.OTHER_PIN;
        
    //     when(staffRepository.findByPin(eq(staffPin)))
    //             .thenReturn(Optional.of(staff));
    //     when(staffRepository.findByPin(eq(otherPin)))
    //             .thenReturn(Optional.of(staff));

    //     ChangePinDTO dto = new ChangePinDTO();
    //     dto.setCurrentPin(otherPin);
    //     dto.setNewPin(staffPin);

    //     BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
    //         staffService.changePin(dto);
    //     }, "BadRequestException was expected");

    //     assertEquals("New pin is already in use.", thrown.getMessage());
    // }

    // @Test
    // public void changePin_changeStaffPin_nonexistantPin() {
    //     ChangePinDTO dto = new ChangePinDTO();
    //     dto.setCurrentPin(StaffServiceTestConstants.OTHER_PIN);
    //     dto.setNewPin(StaffServiceTestConstants.STAFF_PIN);

    //     NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
    //         staffService.changePin(dto);
    //     }, "NotFoundException was expected");

    //     assertEquals("Current pin does not exist.", thrown.getMessage());
    // }

    // @Test
    // public void changeSalary_changeStaffSalary_successful() {
    //     Waiter staff = generateWaiter();
    //     Long staffId = StaffServiceTestConstants.STAFF_ID;
    //     when(staffRepository.findById(eq(staffId)))
    //             .thenReturn(Optional.of(staff));
        
    //     SetSalaryDTO dto = new SetSalaryDTO();
    //     dto.setMonthlyWage(BigDecimal.valueOf(1000));
    //     dto.setStaffId(staffId);

    //     staffService.changeSalary(dto);
    // }

    // @Test
    // public void changeSalary_changeStaffSalary_nonexistentStaff() {
    //     SetSalaryDTO dto = new SetSalaryDTO();
    //     dto.setMonthlyWage(BigDecimal.valueOf(1000));
    //     Long staffId = StaffServiceTestConstants.STAFF_ID;
    //     dto.setStaffId(staffId);

    //     NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
    //         staffService.changeSalary(dto);
    //     }, "NotFoundException was expected");

    //     assertEquals(String.format("No staff member with id %d has been found", staffId), thrown.getMessage());
    // }

    // private Waiter generateWaiter() {
    //     Waiter waiter = new Waiter();
    //     waiter.setFirstName(StaffServiceTestConstants.STAFF_FIRST_NAME);
    //     waiter.setLastName(StaffServiceTestConstants.STAFF_LAST_NAME);
    //     waiter.setPin(StaffServiceTestConstants.STAFF_PIN);
    //     waiter.setMonthlyWage(StaffServiceTestConstants.STAFF_WAGE);
    //     waiter.setPhoneNumber(StaffServiceTestConstants.STAFF_PHONE_NUMBER);
    //     return waiter;
    // }
}
