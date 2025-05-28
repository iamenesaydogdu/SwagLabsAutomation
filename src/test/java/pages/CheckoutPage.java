package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PopupHandler;

public class CheckoutPage {
    WebDriver driver;
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueBtn = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillCheckoutForm(String fname, String lname, String zip) {
        driver.findElement(firstName).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(postalCode).sendKeys(zip);
    }


    public void clickContinue() {
        // Close popup before clicking
        PopupHandler.closeGooglePasswordPopup(driver);

        driver.findElement(continueBtn).click();
    }

    public String getErrorMessage() {
        return driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
    }
    public void finishOrder() {
        driver.findElement(By.id("finish")).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(By.className("complete-header")).getText();
    }


}
