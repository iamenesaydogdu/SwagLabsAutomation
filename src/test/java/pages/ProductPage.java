package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PopupHandler;

import java.time.Duration;

public class ProductPage {
    WebDriver driver;
    By firstProductAddToCartBtn = By.xpath("(//button[contains(text(),'Add to cart')])[1]");
    By cartIcon = By.className("shopping_cart_link");
    By googlePasswordPopup = By.cssSelector("div[role='dialog'] button");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addFirstProductToCart() {
        PopupHandler.closeGooglePasswordPopup(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Handle Google Password popup if shown
        try {
            WebElement popupClose = wait.until(ExpectedConditions.elementToBeClickable(googlePasswordPopup));
            popupClose.click();
            System.out.println("Google password popup closed on product page.");
        } catch (Exception e) {
            System.out.println("Google popup not visible, proceeding...");
        }

        // Click add to cart
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(firstProductAddToCartBtn));
        addButton.click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }
}
