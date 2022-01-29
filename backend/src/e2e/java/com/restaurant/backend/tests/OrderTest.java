package com.restaurant.backend.tests;

import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.TableOrderDialog;
import com.restaurant.backend.pages.Utilities;
import com.restaurant.backend.pages.WaiterPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTest {
    private WebDriver chromeDriver;

    private LoginPage loginPage;
    private WaiterPage waiterPage;
    private TableOrderDialog tableOrderDialog;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        chromeDriver = new ChromeDriver();

        chromeDriver.manage().window().maximize();

        loginPage = PageFactory.initElements(chromeDriver, LoginPage.class);
        waiterPage = PageFactory.initElements(chromeDriver, WaiterPage.class);
        tableOrderDialog = PageFactory.initElements(chromeDriver, TableOrderDialog.class);
    }

    @Test
    public void test() {
        chromeDriver.navigate().to("http://localhost:4200/login");
        loginPage.loginWithPin("1234");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/waiter", 5));
        waiterPage.waitWebSocketConnection();
        assertTrue(waiterPage.clickTable("9"));
        tableOrderDialog.waitNoOrderText();
        tableOrderDialog.menuTabClick();

        assertTrue(tableOrderDialog.clickCategory(1));
        assertTrue(tableOrderDialog.clickItem(0));

        tableOrderDialog.clickPlus(3);
        tableOrderDialog.clickMinus(1);
        tableOrderDialog.clickAdd();

        tableOrderDialog.orderTabClick();


//        assertEquals(3, tableOrderDialog.getOrderItems().size());
    }

    @AfterAll
    public void closeSelenium() {
        //chromeDriver.quit();
    }
}
