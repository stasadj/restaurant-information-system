package com.restaurant.backend.tests;

import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.Utilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.PageFactory;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {
    private WebDriver chromeDriver;
    //private WebDriver edgeDriver;

    private LoginPage loginPage;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        //System.setProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");

        chromeDriver = new ChromeDriver();
        //edgeDriver = new EdgeDriver();

        chromeDriver.manage().window().maximize();

        loginPage = PageFactory.initElements(chromeDriver, LoginPage.class);
    }

    @Test
    public void loginTest() {
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.managerTabClick();
        loginPage.setUsernameInput("morgan");
        loginPage.setPasswordInput("test");
        loginPage.loginButtonClick();
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));
    }

    @Test
    public void pinLoginTest() {
        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.staffTabClick();
        loginPage.setPinInput("1234");
        loginPage.pinLoginButtonClick();
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/waiter", 5));
    }

    @AfterAll
    public void closeSelenium() {
        chromeDriver.quit();
    }
}
