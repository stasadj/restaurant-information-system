package com.restaurant.backend.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.restaurant.backend.pages.ItemsPage;
import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.ManagerPage;
import com.restaurant.backend.pages.NewItemPage;
import com.restaurant.backend.pages.Utilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemsTest {
    private WebDriver chromeDriver;
    //private WebDriver edgeDriver;

    private ItemsPage itemsPage;
    private NewItemPage newItemPage;
    private LoginPage loginPage;
    private ManagerPage managerPage;


    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        //System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");

        chromeDriver = new ChromeDriver();
        //edgeDriver = new EdgeDriver();

        loginPage = PageFactory.initElements(chromeDriver, LoginPage.class);
        itemsPage = PageFactory.initElements(chromeDriver, ItemsPage.class);
        newItemPage = PageFactory.initElements(chromeDriver, NewItemPage.class);
        managerPage = PageFactory.initElements(chromeDriver, ManagerPage.class);

    }

    @Test
    public void test() {

        //login
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.loginWithCredentials("morgan", "test");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));

        //saving starting number of cards
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

        //go to items tab
        managerPage.itemsTabClick();

        //checking if number of cards incremented
        int numberOfItemsAfterAdd = itemsPage.getItemCountAfterCreate(numberOfItemCards);
        assertEquals(numberOfItemCards + 1, numberOfItemsAfterAdd);

        //checking if data on last card matches new data
        assertTrue(itemsPage.lastItemTitleHasText("Quattro pizaa"));

        //deleting created item
        itemsPage.lastItemDeleteClick();

        //checking if number of cards decremented
        int numberOfItemsAfterDelete = itemsPage.getItemCountAfterDelete(numberOfItemsAfterAdd);
        assertEquals(numberOfItemsAfterAdd - 1, numberOfItemsAfterDelete);


    }


    @AfterAll
    public void closeSelenium() {
        chromeDriver.quit();
    }
}
