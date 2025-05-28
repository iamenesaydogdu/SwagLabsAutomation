package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("signon.rememberSignons", false); // Parola kaydetmeyi engelle
            options.addPreference("signon.autofillForms", false);   // Otomatik doldurmayı engelle
            options.addPreference("network.cookie.cookieBehavior", 0); // Tüm çerezlere izin ver
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");

            driver = new FirefoxDriver(options);
        }
        return driver;
    }

    public static void openUrl(String url) {
        driver.get(url);
        // Firefox'ta popup çıkmaz, bu satır opsiyonel
        // PopupHandler.closeGooglePasswordPopup(driver);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
