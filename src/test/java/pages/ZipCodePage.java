package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ZipCodePage extends BasePage {

    private static final String URL = "https://www.sharelane.com/cgi-bin/register.py";

    private final By zipInput = By.name("zip_code");
    private final By continueButton = By.cssSelector("input[value='Continue']");

    public ZipCodePage(WebDriver driver) {
        super(driver);
    }

    public ZipCodePage open() {
        driver.get(URL);
        visible(zipInput);
        return this;
    }

    public SignUpPage continueWithValidZip(String zip) {
        type(zipInput, zip);
        click(continueButton);
        return new SignUpPage(driver);
    }

    public ZipCodePage continueWithInvalidZip(String zip) {
        type(zipInput, zip);
        click(continueButton);
        return this;
    }
}
