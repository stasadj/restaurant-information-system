package com.restaurant.backend.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemsPage extends ManagerPage{
    private WebDriver driver;

    @FindBy(className = "item-card")
    private List<WebElement> item_cards;

    @FindBy(className = "mat-card-title")
    private List<WebElement> item_cards_titles;

    @FindBy(className = "delete-button")
    private List<WebElement> delete_buttons;


    public ItemsPage(WebDriver driver) {
        this.driver = driver;
    }


    public int getNumberOfItems(){
        return Utilities.visibilityWait(driver, By.className("item-card"), 10).size();
    }

    public boolean lastItemTitleHasText(String text) {
		return Utilities.textWait(driver, this.item_cards_titles.get(this.item_cards_titles.size()-1), text, 10);

	}

    public void lastItemDeleteClick(){
         Utilities.clickableWait(driver, this.delete_buttons.get(this.delete_buttons.size()-1), 10).click();

    }

    
    public int getItemCountAfterCreate(int numberOfItemsBeforeAdd){
        return Utilities.waitNumbOfElementsMoreThan(driver, By.className("item-card"), 10, numberOfItemsBeforeAdd).size();

    }

    public int getItemCountAfterDelete(int numberOfItemsBeforeDelete){
        return Utilities.waitNumbOfElementsLessThan(driver, By.className("item-card"), 10, numberOfItemsBeforeDelete).size();

    }
}
