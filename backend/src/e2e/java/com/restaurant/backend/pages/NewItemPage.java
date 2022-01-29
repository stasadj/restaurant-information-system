package com.restaurant.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewItemPage extends ManagerPage {
    
    private WebDriver driver;

    @FindBy(id = "item_name")
    private WebElement nameInput;

    @FindBy(id = "item_description")
    private WebElement descriptionInput;

    @FindBy(id = "item_category")
    private WebElement categorySelect;

    @FindBy(xpath="//*[@id='mat-option-0']/span")
    private WebElement appetizerOption;

    @FindBy(id = "item_purchase_price")
    private WebElement purchasePriceInput;

    @FindBy(id = "item_selling_price")
    private WebElement sellingPriceInput;

    @FindBy(xpath = "//*[@id='checkboxes_container']/div[1]/input")
    private WebElement veganCheckBox;

    @FindBy(xpath = "//*[@id='checkboxes_container']/div[3]/input")
    private WebElement glutenFreeCheckBox;

    @FindBy(id = "save_item_button")
    private WebElement saveButton;


    public NewItemPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setNameInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.nameInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void setDescriptionInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.descriptionInput, 10);
        el.clear();
        el.sendKeys(value);
    }
    public void categorySelectClick() {
		Utilities.visibilityWait(driver, this.categorySelect, 10).click();
		Utilities.visibilityWait(driver, this.appetizerOption, 10).click();
	}

    public void setPurchasePriceInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.purchasePriceInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void setSellingPriceInput(String value) {
        WebElement el = Utilities.visibilityWait(driver, this.sellingPriceInput, 10);
        el.clear();
        el.sendKeys(value);
    }

    public void selectCheckBoxes(){
        Utilities.visibilityWait(driver, this.veganCheckBox, 10).click();
		Utilities.visibilityWait(driver, this.glutenFreeCheckBox, 10).click();
    }

    public void saveButtonClick(){
		Utilities.clickableWait(driver, this.saveButton, 10).click();
    }
}
