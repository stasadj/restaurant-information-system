package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TableOrderDialog {
    private WebDriver driver;

    @FindBy(id = "close-btn")
    private WebElement closeButton;

    @FindBy(xpath = "//*[@id='order-items-table']/tbody/tr")
    private List<WebElement> orderItems;

    @FindBy(xpath = "//*[@id='order-menu-tabs']//*[@role='tab'][1]")
    private WebElement orderTab;

    @FindBy(xpath = "//*[@id='order-menu-tabs']//*[@role='tab'][2]")
    private WebElement menuTab;

    @FindBy(id = "plus-btn")
    private WebElement plusButton;

    @FindBy(id = "minus-btn")
    private WebElement minusButton;

    @FindBy(id = "add-btn")
    private WebElement addButton;

    @FindBy(id = "note-input")
    private WebElement noteInput;

    @FindBy(id = "create-btn")
    private WebElement createButton;

    @FindBy(id = "update-btn")
    private WebElement updateButton;

    @FindBy(id = "finalize-btn")
    private WebElement finalizeButton;

    public TableOrderDialog(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getOrderItems() {
        Utilities.visibilityWait(driver, By.xpath("//*[@id='order-items-table']"), 5);
        System.out.println(orderItems.get(0).findElement(By.className("mat-column-status")).getText());
        return orderItems;
    }

    public void orderTabClick() {
        orderTab.click();
    }

    public void menuTabClick() {
        menuTab.click();
    }

    public void waitNoOrderText() {
        Utilities.presenceWait(driver, By.id("no-order-txt"), 5);
    }

    public boolean clickCategory(int i) {
        List<WebElement> categoryList =
        Utilities.waitNumbOfElementsMoreThan(driver, By.xpath("//*[@id='category-select']/mat-list-option"), 5, 0);
        if (categoryList.size() > i) {
            categoryList.get(i).click();
            return true;
        }
        return false;
    }

    public boolean clickItem(int i) {
        List<WebElement> itemList =
                Utilities.waitNumbOfElementsMoreThan(driver, By.xpath("//*[@id='items-table']/tbody/tr"), 5, 0);
        if (itemList.size() > i) {
            itemList.get(i).click();
            return true;
        }
        return false;
    }

    public void clickPlus(int times) {
        WebElement btn = Utilities.clickableWait(driver, plusButton, 3);
        for (int i = 0; i < times; i++) btn.click();
    }

    public void clickMinus(int times) {
        WebElement btn = Utilities.clickableWait(driver, minusButton, 3);
        for (int i = 0; i < times; i++) btn.click();
    }

    public void clickAdd() {
        Utilities.clickableWait(driver, addButton, 3).click();
    }
}
