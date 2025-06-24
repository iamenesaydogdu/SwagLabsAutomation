package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import utils.PopupHandler;

public class CheckoutPage {
    WebDriver driver;

    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueBtn = By.id("continue");
    By errorMsg = By.cssSelector("h3[data-test='error']");
    By finishBtn = By.id("finish");
    By confirmationMsg = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillCheckoutForm(String fname, String lname, String zip) {
        driver.findElement(firstName).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(postalCode).sendKeys(zip);
    }

    public void clickContinue() {
        PopupHandler.closeGooglePasswordPopup(driver);
        driver.findElement(continueBtn).click();
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMsg).getText();
        } catch (NoSuchElementException e) {
            return "No error message found";
        }
    }

    public void finishOrder() {
        driver.findElement(finishBtn).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(confirmationMsg).getText();
    }
}
