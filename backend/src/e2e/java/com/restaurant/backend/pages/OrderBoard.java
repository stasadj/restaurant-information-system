package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrderBoard {
    private final WebDriver driver;

    public OrderBoard(WebDriver driver) {
        this.driver = driver;
    }

    public void waitWebSocketConnection() {
        Utilities.visibilityWait(driver, By.xpath("//mat-spinner"), 2);
        Utilities.invisibilityWait(driver, By.xpath("//mat-spinner"), 10);
    }

    public boolean waitAppearedOrderInList(String tableId, String listName, int prevNumber) {
        List<WebElement> cards = Utilities.waitNumbOfElements(driver,
                By.xpath("//*[@id='"+listName+"-list']//app-order-card//mat-card-title"), 10, prevNumber+1);
        return Utilities.textWait(driver, cards.get(cards.size()-1), "Table "+tableId, 10);
    }

    public int getNumberOfCardsInList(String listName) {
        List<WebElement> cards = driver.findElements(By.xpath("//*[@id='"+listName+"-list']//app-order-card//mat-card-title"));
        return cards.size();
    }

    public void acceptLastOrder(int minNumber) {
        List<WebElement> orderItems = Utilities.waitNumbOfElementsMoreThan(driver,
                By.xpath("//*[@id='pending-list']//app-order-card[last()]//mat-list-option"), 10, minNumber-1);
        for (WebElement el : orderItems) el.click();
        WebElement acceptBtn = Utilities.presenceWait(driver, By.xpath("//*[@id='pending-list']//app-order-card[last()]//button[2]"), 2);
        acceptBtn.click();
    }

    public void prepareLastOrder() {
        List<WebElement> orderItems = Utilities.waitNumbOfElementsMoreThan(driver,
                By.xpath("//*[@id='doing-list']//app-order-card[last()]//mat-list-option"), 10, 0);
        for (WebElement el : orderItems) el.click();
        WebElement prepareBtn = Utilities.presenceWait(driver, By.xpath("//*[@id='doing-list']//app-order-card[last()]//button"), 2);
        prepareBtn.click();
    }

}
