package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PopupHandler;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    By cartItem = By.className("cart_item");
    By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // Check if product exists in cart
    public boolean isProductInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartItem));
            return driver.findElements(cartItem).size() > 0;
        } catch (Exception e) {
            System.out.println("No product found in cart.");
            return false;
        }
    }

    public void clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Close popup first
        PopupHandler.closeGooglePasswordPopup(driver);

        // Click checkout button
        WebElement checkout = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        checkout.click();
    }
}
