package com.restaurant.backend.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemsPage extends ManagerPage {
    private WebDriver driver;

    @FindBy(className = "item-card")
    private List<WebElement> itemCards;

    @FindBy(className = "mat-card-title")
    private List<WebElement> itemCardsTitles;

    @FindBy(className = "delete-button")
    private List<WebElement> deleteButtons;

    @FindBy(className = "add-to-menu-button")
    private List<WebElement> addToMenuButtons;

    @FindBy(className = "remove-from-menu-button")
    private List<WebElement> removeFromMenuButtons;

    @FindBy(className = "edit-button")
    private List<WebElement> editButtons;

    @FindBy(xpath = "//*[@id='mat-dialog-0']")
    private WebElement editDialog;

    public ItemsPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getNumberOfItems() {
        return Utilities.visibilityWait(driver, By.className("item-card"), 10).size();
    }

    public boolean lastItemTitleHasText(String text) {

        By locator = By.className("mat-card-title");
        this.itemCardsTitles = Utilities.visibilityWait(driver, locator , 10);
        return Utilities.textWait(driver, this.itemCardsTitles.get(this.itemCardsTitles.size()-1), text, 10);

    }

    public void lastItemAddToMenuButtonClick() {
        this.addToMenuButtons = Utilities.visibilityWait(driver, By.className("add-to-menu-button"), 5);
        Utilities.clickableWait(driver, this.addToMenuButtons.get(this.addToMenuButtons.size() - 1), 10).click();

    }

    public boolean lastItemRemoveFromMenuButtonDisplayed() {
        By childLocator = By.className("remove-from-menu-button");
        return Utilities.childPresenceWait(driver, this.itemCards.get(this.itemCards.size() - 1), childLocator, 10)
                .isDisplayed();
    }

    public boolean lastItemAddToMenuButtonDisplayed() {
        By childLocator = By.className("add-to-menu-button");
        return Utilities.childPresenceWait(driver, this.itemCards.get(this.itemCards.size() - 1), childLocator, 10)
                .isDisplayed();
    }

    public void lastItemRemoveFromMenuClick() {
        By childLocator = By.className("remove-from-menu-button");
        Utilities.childPresenceWait(driver, this.itemCards.get(this.itemCards.size() - 1), childLocator, 10).click();
    }

    public void lastItemEditClick() {
        Utilities.clickableWait(driver, this.editButtons.get(this.editButtons.size() - 1), 10).click();

    }

    public boolean editItemDialogIsPresent() {
        return Utilities.visibilityWait(driver, editDialog, 10).isDisplayed();

    }

    public void lastItemDeleteClick() {
        By childLocator = By.className("delete-button");
        this.itemCards = Utilities.visibilityWait(driver, By.className("item-card"), 5);
        Utilities.childPresenceWait(driver, this.itemCards.get(this.itemCards.size() - 1), childLocator, 10).click();

    }

    public int getItemCountAfterCreate(int numberOfItemsBeforeAdd) {
        return Utilities.waitNumbOfElementsMoreThan(driver, By.className("item-card"), 10, numberOfItemsBeforeAdd)
                .size();

    }

    public int getItemCountAfterDelete(int numberOfItemsBeforeDelete) {
        return Utilities.waitNumbOfElementsLessThan(driver, By.className("item-card"), 10, numberOfItemsBeforeDelete)
                .size();

    }



}
