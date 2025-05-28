package tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.DriverFactory;
import utils.PopupHandler;

public class AddToCartTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver();
        DriverFactory.openUrl("https://www.saucedemo.com/");
    }

    @Test
    public void testAddToCart() {
        new LoginPage(driver).login("standard_user", "secret_sauce");

        ProductPage productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();
        productPage.goToCart();

        CartPage cartPage = new CartPage(driver);
        PopupHandler.closeGooglePasswordPopup(driver); // Close popup if appears
        Assert.assertTrue(cartPage.isProductInCart(), "Product was not added to cart");

        cartPage.clickCheckout(); // Click the checkout button
    }

    @AfterMethod
    public void teardown() {
        DriverFactory.quitDriver();
    }
}
