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

public class CheckoutDataDrivenTest {
    WebDriver driver;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return ExcelUtil.readExcelData("testdata/CheckoutData.xlsx", "FormData");
    }

    @Test(dataProvider = "checkoutData")
    public void checkoutFormTest(String firstName, String lastName, String zip) {
        test = ExtentReportManager.getReportInstance().createTest("Checkout Form Test - " + firstName);

        try {
            new LoginPage(driver).login("standard_user", "secret_sauce");
            test.log(Status.INFO, "Logged in as standard_user");

            ProductPage productPage = new ProductPage(driver);
            productPage.addFirstProductToCart();
            test.log(Status.INFO, "Product added to cart");

            productPage.goToCart();

            CartPage cartPage = new CartPage(driver);
            cartPage.clickCheckout();
            test.log(Status.INFO, "Navigated to Checkout Page");

            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.fillCheckoutForm(firstName, lastName, zip);
            test.log(Status.INFO, "Filled form with: " + firstName + ", " + lastName + ", " + zip);

            checkoutPage.clickContinue();
            test.log(Status.PASS, "Form submitted successfully");

        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        ExtentReportManager.getReportInstance().flush();
    }
}
