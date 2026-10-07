package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends BasePage {

    private final By firstName = By.name("first_name");
    private final By lastName = By.name("last_name");
    private final By email = By.name("email");
    private final By password = By.name("password1");
    private final By confirmPassword = By.name("password2");
    private final By registerButton = By.cssSelector("input[value='Register']");
    private final By generatedEmail = By.xpath("//td[normalize-space()='Email']/following-sibling::td/b");

    public SignUpPage(WebDriver driver) {
        super(driver);
        visible(firstName);
    }

    public SignUpPage fillForm(String first, String last, String mail, String pass, String confirm) {
        type(firstName, first);
        type(lastName, last);
        type(email, mail);
        type(password, pass);
        type(confirmPassword, confirm);
        return this;
    }

    public SignUpPage submit() {
        click(registerButton);
        return this;
    }

    /** Email, который сайт показывает на странице подтверждения. */
    public String generatedEmailText() {
        return visible(generatedEmail).getText();
    }
}
