package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class TableOrderDialog {
    private final WebDriver driver;

    @FindBy(id = "close-btn")
    private WebElement closeButton;

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

    @FindBy(xpath = "//*[@id='order-items-table']/tbody/tr[last()]")
    private WebElement lastOrderItem;

    @FindBy(xpath = "//*[@id='order-items-table']/tbody/tr[last()]/td[5]")
    private WebElement lastOrderItemStatus;

    public TableOrderDialog(WebDriver driver) {
        this.driver = driver;
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

    public void waitOrderItemsTable() {
        Utilities.presenceWait(driver, By.id("order-items-table"), 5);
    }

    public boolean clickCategory(int i) {
        List<WebElement> categoryList =
        Utilities.waitNumbOfElementsMoreThan(driver, By.xpath("//*[@id='category-select']/mat-list-option"), 5, 0);
        if (categoryList.size() > i) {
            Utilities.clickableWait(driver, categoryList.get(i), 3);
            categoryList.get(i).click();
            return true;
        }
        return false;
    }

    public String clickItem(int i) {
        List<WebElement> itemList =
                Utilities.waitNumbOfElementsMoreThan(driver, By.xpath("//*[@id='items-table']/tbody/tr"), 10, 0);
        if (itemList.size() > i) {
            itemList.get(i).click();
            return itemList.get(i).findElement(By.className("mat-column-name")).getText();
        }
        return null;
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

    public boolean isAdded(String itemName, int amount) {
        Utilities.textWait(driver, lastOrderItem.findElement(By.className("mat-column-name")), itemName, 3);
        List<WebElement> orderItems =
                Utilities.waitNumbOfElementsMoreThan(driver, By.xpath("//*[@id='order-items-table']/tbody/tr"), 5, 0);
        for (WebElement el : orderItems) {
            if (Objects.equals(el.findElement(By.className("mat-column-name")).getText(), itemName)
                    && Integer.parseInt(el.findElement(By.className("mat-column-amount")).getText())>=amount) {
                return true;
            }
        }
        return false;
    }

    public boolean isCreated() {
        return Utilities.textWait(driver, lastOrderItemStatus, "PENDING", 10);
    }

    public void clickCreate() {
        Utilities.clickableWait(driver, createButton, 3).click();
    }

    public void clickUpdate() {
        Utilities.clickableWait(driver, updateButton, 3).click();
    }

    public void clickFinalize() {
        Utilities.clickableWait(driver, finalizeButton, 3).click();
    }

    public void clickClose() {
        Utilities.clickableWait(driver, closeButton, 3).click();
    }
}
