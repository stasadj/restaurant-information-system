package com.restaurant.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RoomsCanvas {
    private final WebDriver driver;

    @FindBy(className = "table-in-restaurant")
    private List<WebElement> tables;

    @FindBy(id = "add-table-btn")
    private WebElement addTableButton;

    @FindBy(tagName = "app-canvas")
    private WebElement canvas;

    @FindBy(id = "room-input")
    private WebElement pinInput;

    public RoomsCanvas(WebDriver driver) {
        this.driver = driver;
    }

    public void dragDropLastTable(int xOffset, int yOffset) {
        Actions act = new Actions(driver);
        act.clickAndHold(tables.get(tables.size()-1)).pause(1000)
                .moveToElement(canvas, xOffset, yOffset)
                .release()
                .build().perform();
    }

    public void addTableButtonClick() {
        Utilities.clickableWait(driver, this.addTableButton, 10).click();
    }


}
