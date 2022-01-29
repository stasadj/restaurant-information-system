package com.restaurant.backend.tests;

import com.restaurant.backend.pages.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderTest {
    private WebDriver waitersDriver, cooksDriver;

    private LoginPage loginPage1, loginPage2;
    private WaiterPage waiterPage;
    private TableOrderDialog tableOrderDialog;
    private OrderBoard orderBoard;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        waitersDriver = new ChromeDriver();
        cooksDriver = new ChromeDriver();

        loginPage1 = PageFactory.initElements(waitersDriver, LoginPage.class);
        loginPage2 = PageFactory.initElements(cooksDriver, LoginPage.class);
        waiterPage = PageFactory.initElements(waitersDriver, WaiterPage.class);
        tableOrderDialog = PageFactory.initElements(waitersDriver, TableOrderDialog.class);
        orderBoard = PageFactory.initElements(cooksDriver, OrderBoard.class);
    }

    @Test
    public void test() {
        // WAITER LOGIN
        waitersDriver.navigate().to("http://localhost:4200/login");
        loginPage1.loginWithPin("1234");
        assertTrue(Utilities.urlWait(waitersDriver, "http://localhost:4200/waiter", 5));
        waiterPage.waitWebSocketConnection();

        // COOK LOGIN
        cooksDriver.navigate().to("http://localhost:4200/login");
        loginPage2.loginWithPin("1111");
        assertTrue(Utilities.urlWait(cooksDriver, "http://localhost:4200/cook", 5));
        orderBoard.waitWebSocketConnection();
        int nCards = orderBoard.getNumberOfCardsInList("pending");

        // WAITER CREATES ORDER
        String TABLE_ID = "8";
        assertTrue(waiterPage.clickTable(TABLE_ID));
        tableOrderDialog.waitNoOrderText();
        tableOrderDialog.menuTabClick();

        assertTrue(tableOrderDialog.clickCategory(1));
        String itemName = tableOrderDialog.clickItem(0);
        assertNotNull(itemName);

        tableOrderDialog.clickPlus(3);
        tableOrderDialog.clickMinus(1);
        tableOrderDialog.clickAdd();

        tableOrderDialog.orderTabClick();

        assertTrue(tableOrderDialog.isAdded(itemName, 2));
        tableOrderDialog.clickCreate();
        assertTrue(tableOrderDialog.isCreated());

        tableOrderDialog.clickClose();

        assertTrue(orderBoard.waitAppearedOrderInList(TABLE_ID, "pending", nCards));

        // WAITER EDITS ORDER
        assertTrue(waiterPage.clickTable(TABLE_ID));
        tableOrderDialog.waitOrderItemsTable();
        tableOrderDialog.menuTabClick();

        assertTrue(tableOrderDialog.clickCategory(1));
        itemName = tableOrderDialog.clickItem(1);
        assertNotNull(itemName);

        tableOrderDialog.clickPlus(3);
        tableOrderDialog.clickMinus(1);
        tableOrderDialog.clickAdd();

        tableOrderDialog.orderTabClick();

        assertTrue(tableOrderDialog.isAdded(itemName, 2));
        tableOrderDialog.clickUpdate();

        tableOrderDialog.clickClose();

        // COOK GETS ORDER

        nCards = orderBoard.getNumberOfCardsInList("doing");
        // COOK ACCEPTS ORDER
        orderBoard.acceptLastOrder(2);
        assertTrue(orderBoard.waitAppearedOrderInList(TABLE_ID, "doing", nCards));

        int numberOfNotifications = waiterPage.getNumberOfNotifications();

        nCards = orderBoard.getNumberOfCardsInList("ready");
        // COOK PREPARES ORDER
        orderBoard.prepareLastOrder();
        assertTrue(orderBoard.waitAppearedOrderInList(TABLE_ID, "ready", nCards));

        // WAITER GETS NOTIFICATION
        assertTrue(waiterPage.waitAppearedNotification(TABLE_ID, numberOfNotifications));

        waiterPage.clickLastNotification();
        tableOrderDialog.waitOrderItemsTable();
        // WAITER FINALIZES ORDER
        tableOrderDialog.clickFinalize();
        tableOrderDialog.waitNoOrderText();
        tableOrderDialog.clickClose();
    }

    @AfterAll
    public void closeSelenium() {
        waitersDriver.quit();
        cooksDriver.quit();
    }
}
