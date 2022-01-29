package com.restaurant.backend.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.restaurant.backend.pages.EditItemDialogPage;
import com.restaurant.backend.pages.ItemsPage;
import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.ManagerPage;
import com.restaurant.backend.pages.NewItemPage;
import com.restaurant.backend.pages.Utilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class ItemsTest {
    private WebDriver chromeDriver;

    private ItemsPage itemsPage;
    private NewItemPage newItemPage;
    private LoginPage loginPage;
    private ManagerPage managerPage;
    private EditItemDialogPage editItemDialogPage;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        chromeDriver = new ChromeDriver();

        loginPage = PageFactory.initElements(chromeDriver, LoginPage.class);
        itemsPage = PageFactory.initElements(chromeDriver, ItemsPage.class);
        newItemPage = PageFactory.initElements(chromeDriver, NewItemPage.class);
        managerPage = PageFactory.initElements(chromeDriver, ManagerPage.class);
        editItemDialogPage = PageFactory.initElements(chromeDriver, EditItemDialogPage.class);

    }

    @Test
    @Order(1)
    public void login() {
        //login
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.loginWithCredentials("morgan", "test");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));
    }

    @Test
    @Order(2)
    public void itemsCreationTest() throws InterruptedException {

        // saving starting number of cards
        int numberOfItemCards = itemsPage.getNumberOfItems();

        // create new item
        managerPage.newItemTabClick();
        newItemPage.setNameInput("Quattro pizaa");
        newItemPage.setDescriptionInput("Traditional italian recipe");
        newItemPage.categorySelectClick();
        newItemPage.setPurchasePriceInput("5000");
        newItemPage.setSellingPriceInput("7000");
        newItemPage.selectCheckBoxes();
        newItemPage.saveButtonClick();

        // go to items tab
        managerPage.itemsTabClick();

        // checking if number of cards incremented
        int numberOfItemsAfterAdd = itemsPage.getItemCountAfterCreate(numberOfItemCards);
        assertEquals(numberOfItemCards + 1, numberOfItemsAfterAdd);

        // checking if data on last card matches new data
        assertTrue(itemsPage.lastItemTitleHasText("Quattro pizaa"));

    }

    @Test
    @Order(3)
    public void itemsMenuAddRemoveTest() throws InterruptedException {

        // login
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.loginWithCredentials("morgan", "test");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));

        // adding created item to menu
        itemsPage.lastItemAddToMenuButtonClick();

        // checking if remove from menu button appeared
        assertTrue(itemsPage.lastItemRemoveFromMenuButtonDisplayed());

        // removing from menu
        itemsPage.lastItemRemoveFromMenuClick();

        // checking if add to menu button reappeared
        assertTrue(itemsPage.lastItemAddToMenuButtonDisplayed());

       

    }

    @Test
    @Order(4)
    public void itemsEdit() throws InterruptedException {

         // editing created item
         itemsPage.lastItemEditClick();

         // checking if edit dialog opened up
         assertTrue(itemsPage.editItemDialogIsPresent());
 
         // changing name field, and cancelling
         editItemDialogPage.setNameInput("Some disposable name");
         editItemDialogPage.cancelButtonClick();
 
         // check if changes are disposed
         assertTrue(itemsPage.lastItemTitleHasText("Quattro pizaa"));
 
         // editing created item again, and saving
         itemsPage.lastItemEditClick();
 
         editItemDialogPage.setNameInput("Quattro formaggi pizza");
         editItemDialogPage.saveButtonClick();
 
         // check if changes are saved
         assertTrue(itemsPage.lastItemTitleHasText("Quattro formaggi pizza"));
    }

    @Test
    @Order(5)
    public void itemsDeleteTest() throws InterruptedException {

        // login
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.loginWithCredentials("morgan", "test");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));

        // saving starting number of cards
        int numberOfItemCards = itemsPage.getNumberOfItems();

        // deleting created item
        itemsPage.lastItemDeleteClick();

        // checking if number of cards decremented
        int numberOfItemsAfterDelete = itemsPage.getItemCountAfterDelete(numberOfItemCards);
        assertEquals(numberOfItemCards - 1, numberOfItemsAfterDelete);

    }

    @AfterAll
    public void closeSelenium() {
        chromeDriver.quit();
    }
}
