package com.restaurant.backend.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.restaurant.backend.pages.LoginPage;
import com.restaurant.backend.pages.StaffProfilePage;

public class StaffProfileTest {
	private static WebDriver driver;

	private static StaffProfilePage staffProfilePage;
	private static LoginPage loginPage;

	@BeforeAll
	public static void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();

		// navigate
		driver.navigate().to("http://localhost:4200/login");

		// pages
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		staffProfilePage = PageFactory.initElements(driver, StaffProfilePage.class);
	}

	@Test
	public void staffProfileTest() {
		// waiter
		loginPage.loginWithPin("1234");
		staffProfilePage.userProfileButtonClick();
		assertTrue(staffProfilePage.monthlyWagePresent("€340.00"));
		assertTrue(staffProfilePage.averagePresent("€338.00"));

		staffProfilePage.closeButtonClick();
		loginPage.logout();
		driver.navigate().to("http://localhost:4200/login");

		// cook
		loginPage.loginWithPin("1111");
		staffProfilePage.userProfileButtonClick();
		assertTrue(staffProfilePage.monthlyWagePresent("€340.00"));
		assertTrue(staffProfilePage.averagePresent("€340.00"));

		staffProfilePage.closeButtonClick();
		loginPage.logout();
		driver.navigate().to("http://localhost:4200/login");

		// barman
		loginPage.loginWithPin("2222");
		staffProfilePage.userProfileButtonClick();
		assertTrue(staffProfilePage.monthlyWagePresent("€340.00"));
		assertTrue(staffProfilePage.averagePresent("€338.00"));

		staffProfilePage.closeButtonClick();
		loginPage.logout();
	}

	@AfterAll
	public static void closeSelenium() {
		driver.quit();
	}

}
