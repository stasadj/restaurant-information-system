package com.restaurant.backend.tests;

import com.restaurant.backend.pages.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoomsTest {
    private WebDriver driver;

    private LoginPage loginPage;
    private RoomsCanvas roomsCanvas;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        roomsCanvas = PageFactory.initElements(driver, RoomsCanvas.class);
    }

    @Test
    public void test() {
        driver.navigate().to("http://localhost:4200/login");
        loginPage.loginWithCredentials("jeff.goldblum", "test");
        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/admin", 5));

        roomsCanvas.addTableButtonClick();

        roomsCanvas.dragDropLastTable(500, 500);
    }

    @AfterAll
    public void closeSelenium() {
        driver.quit();
    }
}
