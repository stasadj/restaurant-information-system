package com.restaurant.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StaffProfilePage {
	private WebDriver driver;

	@FindBy(id = "user-profile")
	private WebElement userProfileButton;

	@FindBy(id = "staff-profile-close")
	private WebElement closeButton;

	@FindBy(id = "current-wage")
	private WebElement currentWage;

	@FindBy(id = "average")
	private WebElement average;

	public StaffProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	public void userProfileButtonClick() {
		Utilities.clickableWait(driver, userProfileButton, 15).click();
	}

	public void closeButtonClick() {
		Utilities.clickableWait(driver, closeButton, 15).click();
	}

	public boolean monthlyWagePresent(String value) {
		return Utilities.textWait(driver, currentWage, value, 15);
	}

	public boolean averagePresent(String value) {
		return Utilities.textWait(driver, average, value, 15);
	}

}
