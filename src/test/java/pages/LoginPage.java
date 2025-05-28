package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class LoginPage extends BasePage {
    private WebDriver driver;

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");

    public void sendKeys(By locator, String value) {
        driver.findElement(locator).sendKeys(value);
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }


    public LoginPage(WebDriver driver) {
        super(driver);            // BasePage'e driver gönder
        this.driver = driver;     // Kendi driver nesnemizi de set edelim
    }

    public void login(String username, String password) {
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        click(loginButton);
    }
}
