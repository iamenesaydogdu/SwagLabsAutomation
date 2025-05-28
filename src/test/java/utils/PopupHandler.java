package utils;

import org.openqa.selenium.*;
import java.util.List;

public class PopupHandler {

    public static void closeGooglePasswordPopup(WebDriver driver) {
        try {
            // Try clicking the "OK" button
            List<WebElement> popupButtons = driver.findElements(By.cssSelector("div[role='dialog'] button"));
            if (!popupButtons.isEmpty()) {
                popupButtons.get(0).click();
                System.out.println("Popup closed by clicking.");
                Thread.sleep(500); // small delay
                return;
            }

            // Retry 2 more times in case popup appears late
            for (int i = 0; i < 2; i++) {
                Thread.sleep(1000); // wait a bit
                popupButtons = driver.findElements(By.cssSelector("div[role='dialog'] button"));
                if (!popupButtons.isEmpty()) {
                    popupButtons.get(0).click();
                    System.out.println("Popup closed by retry.");
                    return;
                }
            }

            // Use JavaScript to remove the popup if still not gone
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("let popup = document.querySelector(\"div[role='dialog']\"); if (popup) popup.remove();");
            System.out.println("Popup forcefully removed via JavaScript.");

        } catch (Exception e) {
            System.out.println("Popup could not be found or removed.");
        }
    }
}
