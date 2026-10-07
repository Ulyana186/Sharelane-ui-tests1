package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;
import pages.ZipCodePage;

public class RegisterTest extends BaseTest {

    private static final String ZIP_ERROR = "ZIP code should have 5 digits";
    private static final String FORM_ERROR = "Some of your fields have invalid data";

    private String uniqueEmail() {
        return "ulyana" + System.currentTimeMillis() + "@test.com";
    }

    @Test(description = "Позитивный: регистрация с корректными данными")
    public void positiveRegistration() {
        SignUpPage page = new ZipCodePage(driver).open()
                .continueWithValidZip("12345")
                .fillForm("Ulyana", "Mokrushina", uniqueEmail(), "1111", "1111")
                .submit();

        String message = page.messageContaining("Account is created!");
        Assert.assertTrue(message.contains("Account is created!"),
                "Ожидалось сообщение 'Account is created!', получено: " + message);
        Assert.assertTrue(page.generatedEmailText().contains("@"),
                "На странице подтверждения должен отображаться email нового аккаунта.");
    }

    @Test(description = "Негативный: ZIP-код короче 5 цифр")
    public void negativeZipTooShort() {
        ZipCodePage page = new ZipCodePage(driver).open().continueWithInvalidZip("1234");

        String message = page.messageContaining(ZIP_ERROR);
        Assert.assertTrue(message.contains(ZIP_ERROR),
                "Ожидалась ошибка ZIP-кода, получено: " + message);
    }

    @Test(description = "Негативный: ZIP-код с буквами")
    public void negativeZipWithLetters() {
        ZipCodePage page = new ZipCodePage(driver).open().continueWithInvalidZip("12ab5");

        String message = page.messageContaining(ZIP_ERROR);
        Assert.assertTrue(message.contains(ZIP_ERROR),
                "Ожидалась ошибка ZIP-кода, получено: " + message);
    }

    @Test(description = "Негативный: пустое имя")
    public void negativeEmptyFirstName() {
        SignUpPage page = new ZipCodePage(driver).open()
                .continueWithValidZip("12345")
                .fillForm("", "Mokrushina", uniqueEmail(), "1111", "1111")
                .submit();

        String message = page.messageContaining(FORM_ERROR);
        Assert.assertTrue(message.contains(FORM_ERROR),
                "Ожидалась ошибка формы, получено: " + message);
    }

    @Test(description = "Негативный: email без символа @")
    public void negativeEmailWithoutAt() {
        SignUpPage page = new ZipCodePage(driver).open()
                .continueWithValidZip("12345")
                .fillForm("Ulyana", "Mokrushina", "ulyana.test.com", "1111", "1111")
                .submit();

        String message = page.messageContaining(FORM_ERROR);
        Assert.assertTrue(message.contains(FORM_ERROR),
                "Ожидалась ошибка формы, получено: " + message);
    }

    @Test(description = "Негативный: пароль короче 4 символов")
    public void negativeShortPassword() {
        SignUpPage page = new ZipCodePage(driver).open()
                .continueWithValidZip("12345")
                .fillForm("Ulyana", "Mokrushina", uniqueEmail(), "123", "123")
                .submit();

        String message = page.messageContaining(FORM_ERROR);
        Assert.assertTrue(message.contains(FORM_ERROR),
                "Ожидалась ошибка формы, получено: " + message);
    }
}
