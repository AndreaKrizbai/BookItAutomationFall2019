package com.bookit.pages;

import com.bookit.utilities.BrowserUtilities;
import com.bookit.utilities.ConfigurationReader;
import com.bookit.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    @FindBy(linkText = "my")
    protected WebElement my;

    @FindBy(linkText = "self")
    protected WebElement self;

    @FindBy(css = ".navbar-brand > div")
    protected WebElement navBarToggle;

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20);
    protected Actions actions = new Actions(Driver.getDriver());
    protected String browser = ConfigurationReader.getProperty("browser").toLowerCase();

    public BasePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public void goToSelfPage(){
        BrowserUtilities.wait(5);
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("my")));
//        wait.until(ExpectedConditions.elementToBeClickable(my)).click();
//        self.click();
        if (!browser.contains("ios") && !browser.contains("android")) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("my")));
            wait.until(ExpectedConditions.elementToBeClickable(my)).click();
            self.click();
        } else {
            navBarToggle.click();
            self.click();
        }
    }

}
