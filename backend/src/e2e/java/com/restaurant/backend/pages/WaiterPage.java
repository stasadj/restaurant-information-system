package com.restaurant.backend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

public class WaiterPage {
    private final WebDriver driver;

    @FindBy(className = "table-in-restaurant")
    private List<WebElement> tables;

    @FindBy(className = "notification")
    private List<WebElement> notifications;

    public WaiterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitWebSocketConnection() {
        Utilities.visibilityWait(driver, By.xpath("//mat-spinner"), 2);
        Utilities.invisibilityWait(driver, By.xpath("//mat-spinner"), 10);
    }

    public boolean clickTable(String tableId) {
        for (WebElement el : tables)
            if (Objects.equals(el.getText(), tableId)) {
                el.click();
                return true;
            }
        return false;
    }

    public boolean waitAppearedNotification(String tableId, int prevNumber) {
        List<WebElement> notifications = Utilities.waitNumbOfElementsMoreThan(driver,
                By.className("notification"), 10, prevNumber);
        return Utilities.textWait(driver, notifications.get(notifications.size()-1).findElement(By.className("mat-line")), "Table "+tableId, 10);
    }

    public int getNumberOfNotifications() {
        return notifications.size();
    }

    public void clickLastNotification() {
        notifications.get(notifications.size()-1).click();
    }
}
