package tests;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.DriverFactory;
import utils.PopupHandler;
public class CheckoutValidationTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        DriverFactory.openUrl("https://www.saucedemo.com/");

    }


    @Test
    public void testEmptyCheckoutForm() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        new ProductPage(driver).addFirstProductToCart();
        new ProductPage(driver).goToCart();
        new CartPage(driver).clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver);

        // Close popup BEFORE clicking continue
        PopupHandler.closeGooglePasswordPopup(driver);

        checkout.clickContinue();

        String currentUrl = driver.getCurrentUrl();
        assert currentUrl.contains("checkout-step-one") : "Checkout should not proceed with empty form";
    }

    @AfterMethod
    public void teardown() {
        DriverFactory.quitDriver();
    }
}