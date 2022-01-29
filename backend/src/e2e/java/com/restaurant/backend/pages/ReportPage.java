package com.restaurant.backend.pages;

import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportPage extends ManagerPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id='mat-input-7']")
    WebElement fromDateInput;

    @FindBy(xpath = "//*[@id='mat-tab-content-1-3']/div/app-report-board/app-report-item-table/table/tbody/tr[1]/td[1]")
    WebElement firstItem;

    public ReportPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean lastItemTitleHasText(String text) {

        try {
            By locator = By.xpath("//mat-card-title[contains(text(), '" + text + "')]");
            List<WebElement> els = Utilities.visibilityWait(driver, locator, 10);
            return els.get(0).getText().equals(text);
        } catch (StaleElementReferenceException ex) {
            System.out.println("Stale ref ex caught");
            this.driver.navigate().refresh();
            return lastItemTitleHasText(text);
        }

    }

    public String getFirstItem() {
        return firstItem.getText();
    }

    public int getNumberOfItems() {
        return Utilities
                .visibilityWait(driver, By.xpath(
                        "//app-report-item-table/table/tbody//tr"),
                        10)
                .size();
    }

    public int getReportItemCountMoreThan(int numberOfItemsBefore) {
        return Utilities
                .waitNumbOfElementsMoreThan(driver, By.xpath(
                        "//app-report-board/app-report-item-table/table/tbody//tr"),
                        10, numberOfItemsBefore)
                .size();
    }

    public int getReportItemCountLessThan(int numberOfItemsBefore) {
        return Utilities
                .waitNumbOfElementsLessThan(driver, By.xpath(
                        "//app-report-board/app-report-item-table/table/tbody//tr"),
                        10, numberOfItemsBefore)
                .size();
    }

    public void inputFromDateStr(LocalDate date) {
        Utilities.visibilityWait(driver, fromDateInput, 10).sendKeys(String.valueOf(date.getDayOfMonth()));
        Utilities.visibilityWait(driver, fromDateInput, 10).sendKeys(Keys.RIGHT);
        Utilities.visibilityWait(driver, fromDateInput, 10).sendKeys(String.valueOf(date.getMonthValue()));
        Utilities.visibilityWait(driver, fromDateInput, 10).sendKeys(Keys.RIGHT);
        Utilities.visibilityWait(driver, fromDateInput, 10).sendKeys(String.valueOf(date.getYear()));
    }

}
