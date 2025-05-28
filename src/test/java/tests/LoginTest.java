package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;
import utils.ExcelUtil;
import utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class LoginTest {
    WebDriver driver;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @DataProvider(name = "excelLoginData")
    public Object[][] getLoginData() {
        return ExcelUtil.readExcelData("testdata/LoginData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelLoginData")
    public void loginTest(String username, String password, String expectedResult) {
        test = ExtentReportManager.getReportInstance().createTest("Login Test: " + username);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        String currentUrl = driver.getCurrentUrl();
        boolean isSuccess = currentUrl.contains("inventory.html");

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(isSuccess, "Login should succeed for: " + username);
            test.log(Status.PASS, "Login successful for: " + username);
        } else {
            Assert.assertFalse(isSuccess, "Login should fail for: " + username);
            test.log(Status.PASS, "Correctly blocked: " + username);
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        ExtentReportManager.getReportInstance().flush();
    }
}
