package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "username-input")
    private WebElement usernameInput;

    @FindBy(id = "password-input")
    private WebElement passwordInput;

    @FindBy(id = "login-btn")
    private WebElement loginButton;

    @FindBy(id = "pin-input")
    private WebElement pinInput;

    @FindBy(id = "pin-login-btn")
    private WebElement pinLoginButton;

    @FindBy(xpath = "//*[@id='mat-tab-label-0-0']")
    private WebElement staffTab;

    @FindBy(xpath = "//*[@id='mat-tab-label-0-1']")
    private WebElement managerTab;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void staffTabClick() {
        staffTab.click();
    }

    public void managerTabClick() {
        managerTab.click();
    }

    public void setUsernameInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.usernameInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.passwordInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void setPinInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.pinInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void loginButtonClick() {
        Utilities.clickableWait(driver, this.loginButton, 10).click();
    }

    public void pinLoginButtonClick() {
        Utilities.clickableWait(driver, this.pinLoginButton, 10).click();
    }

    public void loginWithCredentials(String username, String password) {
        managerTabClick();
        setUsernameInput(username);
        setPasswordInput(password);
        loginButtonClick();
    }

    public void loginWithPin(String pin) {
        staffTabClick();
        setPinInput(pin);
        pinLoginButtonClick();
    }

    public boolean errorMessagePresent(String text) {
        return false;
    }

    public void waitWebSocketConnection() {
        Utilities.visibilityWait(driver, By.xpath("//mat-spinner"), 2);
        Utilities.invisibilityWait(driver, By.xpath("//mat-spinner"), 10);
    }
}
