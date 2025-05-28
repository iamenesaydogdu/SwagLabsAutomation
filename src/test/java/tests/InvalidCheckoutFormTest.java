package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import utils.DriverFactory;
import utils.ExcelUtil;
import utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;

public class InvalidCheckoutFormTest {
    WebDriver driver;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "invalidCheckoutData")
    public Object[][] getInvalidCheckoutData() {
        return ExcelUtil.readExcelData("testdata/InvalidCheckoutData.xlsx", "InvalidFormData");
    }

    @Test(dataProvider = "invalidCheckoutData")
    public void invalidCheckoutTest(String firstName, String lastName, String zipCode) {
        test = ExtentReportManager.getReportInstance()
                .createTest("Invalid Checkout Test - " + firstName + " " + lastName + " " + zipCode);

        try {
            new LoginPage(driver).login("standard_user", "secret_sauce");
            test.log(Status.INFO, "Logged in as standard_user");

            ProductPage productPage = new ProductPage(driver);
            productPage.addFirstProductToCart();
            productPage.goToCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.clickCheckout();

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.fillCheckoutForm(firstName, lastName, zipCode);
            checkoutPage.clickContinue();

            // ❗ Error message kontrolü
            String errorMessage = checkoutPage.getErrorMessage();
            Assert.assertTrue(errorMessage.toLowerCase().contains("error"), "Expected an error message");
            test.log(Status.PASS, "Validation passed - Error Message: " + errorMessage);

        } catch (AssertionError ae) {
            test.log(Status.FAIL, "Assertion failed: " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            test.log(Status.FAIL, "Unexpected failure: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        ExtentReportManager.getReportInstance().flush();
    }
}
