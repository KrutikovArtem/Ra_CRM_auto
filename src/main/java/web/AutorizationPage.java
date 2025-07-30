package web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.net.URL;

import static com.codeborne.selenide.Selenide.$x;

public class AutorizationPage {

    private SelenideElement inputEmail = $x("//input[@id='email']");
    private SelenideElement inputPassword = $x("//input[@id='password']");
    private SelenideElement buttonSubmit = $x("//button[@type='submit']");

    public AutorizationPage(String url) {
        Selenide.open(url);
    }

    public MainPage autorization(String login, String password){
        inputEmail.val(login);
        inputPassword.val(password);
        buttonSubmit.click();
        return new MainPage();
    }
}
