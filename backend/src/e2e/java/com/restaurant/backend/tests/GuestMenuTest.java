package com.restaurant.backend.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.restaurant.backend.pages.GuestPage;

public class GuestMenuTest {
	private static WebDriver driver;

	private static GuestPage guestPage;

	@BeforeAll
	public static void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		// ChromeOptions options = new ChromeOptions();
		// options.setHeadless(true);
		driver = new ChromeDriver();

		// navigate
		driver.navigate().to("http://localhost:4200/guest");

		// pages
		guestPage = PageFactory.initElements(driver, GuestPage.class);
	}

	@Test
	public void guestMenuTest() {
		// sort criteria 1, filter option 2
		guestPage.selectSortCriteria(1);
		guestPage.selectFilterOption(2);
		assertTrue(guestPage.namePresent("Spaghetti carbonara"));
		assertEquals(2, guestPage.numberOfResults());

		driver.navigate().refresh();

		// sort criteria 4, filter option 2
		guestPage.selectSortCriteria(4);
		guestPage.selectFilterOption(2);
		assertTrue(guestPage.namePresent("Chicken tikka masala"));
		assertTrue(guestPage.pricePresent("Price: RSD1,200"));
		assertEquals(2, guestPage.numberOfResults());

		driver.navigate().refresh();

		// sort critera 2, filter option 0, correct search input
		guestPage.selectSortCriteria(2);
		guestPage.selectFilterOption(0);
		guestPage.search("chicken");
		assertEquals(1, guestPage.numberOfResults());
		assertTrue(guestPage.namePresent("Chicken tikka masala"));

		driver.navigate().refresh();

		// sort critera 0, filter option 0, no results for given input
		guestPage.selectSortCriteria(0);
		guestPage.selectFilterOption(0);
		guestPage.search("asdf");
		assertTrue(guestPage.nameNotPresent("Chicken tikka masala"));

	}

	@AfterAll
	public static void closeSelenium() {
		driver.quit();
	}

}
