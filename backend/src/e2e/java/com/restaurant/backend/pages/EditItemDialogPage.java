package com.restaurant.backend.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditItemDialogPage extends ManagerPage {
    
    private WebDriver driver;

    @FindBy(id = "item_name")
    private WebElement nameInput;

    @FindBy(id = "item_description")
    private WebElement descriptionInput;

    @FindBy(id = "item_category")
    private WebElement categorySelect;

    @FindBy(xpath="//*[@id='mat-option-4']/span")
    private WebElement appetizerOption;

    @FindBy(xpath="//*[@id='mat-option-6']/span")
    private WebElement dessertOption;


    @FindBy(id = "item_purchase_price")
    private WebElement purchasePriceInput;

    @FindBy(id = "item_selling_price")
    private WebElement sellingPriceInput;


    @FindBy(xpath = "//*[@id='checkboxes_container']/div[3]/input")
    private WebElement glutenFreeCheckBox;

    @FindBy(xpath = "//*[@id='checkboxes_container']/div[4]/input")
    private WebElement dairyFreeCheckBox;

    @FindBy(id = "save_item_button")
    private WebElement saveButton;

    @FindBy(id = "cancel_button")
    private WebElement cancelButton;


    public EditItemDialogPage(WebDriver driver) {
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
    public void categorySelectChange() {
		Utilities.visibilityWait(driver, this.categorySelect, 10).click();
		Utilities.visibilityWait(driver, this.dessertOption, 10).click();
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

    public void changeCheckboxes(){
		Utilities.visibilityWait(driver, this.glutenFreeCheckBox, 10).click();
        Utilities.visibilityWait(driver, this.dairyFreeCheckBox, 10).click();

    }

    public void saveButtonClick(){
		Utilities.clickableWait(driver, this.saveButton, 10).click();
    }

    public void cancelButtonClick(){
		Utilities.clickableWait(driver, this.cancelButton, 10).click();
    }


}