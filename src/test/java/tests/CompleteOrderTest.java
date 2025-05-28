package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import utils.DriverFactory;
import utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;

public class CompleteOrderTest {
    WebDriver driver;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void completeOrderSuccessfully() {
        test = ExtentReportManager.getReportInstance().createTest("Complete Order Test");

        try {
            new LoginPage(driver).login("standard_user", "secret_sauce");
            test.log(Status.INFO, "Login successful");

            ProductPage productPage = new ProductPage(driver);
            productPage.addFirstProductToCart();
            productPage.goToCart();
            test.log(Status.INFO, "Product added to cart");

            CartPage cartPage = new CartPage(driver);
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.fillCheckoutForm("John", "Doe", "12345");
            checkoutPage.clickContinue();
            checkoutPage.finishOrder();
            test.log(Status.INFO, "Order completed");

            String confirmationMessage = checkoutPage.getConfirmationMessage();
            Assert.assertTrue(confirmationMessage.toLowerCase().contains("thank you"), "Confirmation message not found");

            test.log(Status.PASS, "Order completed successfully. Confirmation: " + confirmationMessage);

        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        ExtentReportManager.getReportInstance().flush();
    }
}
