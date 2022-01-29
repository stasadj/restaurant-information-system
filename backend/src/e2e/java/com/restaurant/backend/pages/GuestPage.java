package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GuestPage {
	private WebDriver driver;

	@FindBy(id = "search-input")
	private WebElement searchInput;

	@FindBy(id = "search-button")
	private WebElement searchButton;

	public GuestPage(WebDriver driver) {
		this.driver = driver;
	}

	public void selectSortCriteria(int criteria) {
		Utilities.visibilityWaitByLocator(driver, By.xpath("//mat-select[@id='guest-page-sort']"), 15).click();
		Utilities.visibilityWaitByLocator(driver, By.xpath("//mat-option[@value='" + criteria + "']"), 15).click();
	}

	public void selectFilterOption(int option) {
		Utilities.visibilityWaitByLocator(driver, By.xpath("//mat-chip[@id='" + option + "']"), 15).click();
	}

	public boolean namePresent(String name) {
		try {
			return Utilities.visibilityWaitByLocator(driver,
					By.xpath("//mat-card-title[text()[contains(.,'" + name + "')]]"), 15).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e1) {
			this.driver.navigate().refresh();
			return namePresent(name);

		}
	}

	public boolean pricePresent(String price) {
		try {
			return Utilities.visibilityWaitByLocator(driver, By.xpath("//h3[text()[contains(.,'" + price + "')]]"), 15)
					.isDisplayed();
		} catch (TimeoutException e) {
			return false;
		} catch (StaleElementReferenceException e1) {
			this.driver.navigate().refresh();
			return pricePresent(price);
		}
	}

	public boolean nameNotPresent(String name) {
		return !Utilities.isPresent(driver, By.xpath("//mat-card-title[text()[contains(.,'" + name + "')]]"));
	}

	public boolean priceNotPresent(String price) {
		return !Utilities.isPresent(driver, By.xpath("//h3[text()[contains(.,'" + price + "')]]"));
	}

	public int numberOfResults() {
		return Utilities.visibilityWait(driver, By.xpath("//mat-card-title[contains(@class, 'item-name')]"), 15).size();
	}

	public boolean noResults() {
		return !Utilities.isPresent(driver, By.xpath("//mat-card-title[contains(@class, 'item-name')]"));
	}

	private WebElement getSearchInput() {
		return Utilities.visibilityWait(driver, searchInput, 15);
	}

	private void setSearchInput(String searchInput) {
		WebElement el = getSearchInput();
		el.clear();
		el.sendKeys(searchInput);
	}

	public void searchButtonClick() {
		Utilities.clickableWait(driver, searchButton, 15).click();
	}

	public void search(String searchInput) {
		setSearchInput(searchInput);
		searchButtonClick();
	}

}
