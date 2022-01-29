package com.restaurant.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManagerPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id='mat-tab-label-1-0']")
    private WebElement itemsTab;

    @FindBy(xpath = "//*[@id='mat-tab-label-1-1']")
    private WebElement newItemTab;

    public void itemsTabClick(){
        this.itemsTab.click();
    }

    public void newItemTabClick(){
        this.newItemTab.click();
    }
    
}
