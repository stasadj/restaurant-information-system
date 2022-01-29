package com.restaurant.backend.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.ManagerPage;
import com.restaurant.backend.pages.ReportPage;
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
public class ReportTest {
    private WebDriver chromeDriver;

    private LoginPage loginPage;
    private ManagerPage managerPage;
    private ReportPage reportPage;

    @BeforeAll
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

        chromeDriver = new ChromeDriver();

        loginPage = PageFactory.initElements(chromeDriver, LoginPage.class);
        managerPage = PageFactory.initElements(chromeDriver, ManagerPage.class);
        reportPage = PageFactory.initElements(chromeDriver, ReportPage.class);

        chromeDriver.navigate().to("http://localhost:4200/login");
        assertEquals("http://localhost:4200/login", chromeDriver.getCurrentUrl());
        loginPage.loginWithCredentials("morgan", "test");
        assertTrue(Utilities.urlWait(chromeDriver, "http://localhost:4200/manager", 5));

    }

    @Test
    @Order(1)
    public void itemsCreationTest() throws InterruptedException {

        managerPage.reportTabClick();

        int numberOfItems = reportPage.getNumberOfItems();
        assertEquals(1, numberOfItems); // Only total

        reportPage.selectYearlyGranularity();
        reportPage.inputFromDateStr(LocalDate.of(2021, 10, 1));
        assertNotEquals(numberOfItems, reportPage.getReportItemCountMoreThan(numberOfItems));

        assertEquals("Spaghetti carbonara", reportPage.getFirstItem());
    }

    @AfterAll
    public void closeSelenium() {
        chromeDriver.quit();
    }
}
