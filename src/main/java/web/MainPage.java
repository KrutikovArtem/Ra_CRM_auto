package web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private SelenideElement hrefSearch = $x("//input[@placeholder='Поиск...']");
    private SelenideElement hrefMeting = $x("//a[@href='/meetings']");
    private SelenideElement hrefContacts = $x("//a[@href='/contacts']");
    private SelenideElement hrefInterests = $x("//a[@href='/interests']");

    private String test = "ТЕст";



    public ContactsPage openContactsPage(){
        hrefContacts.shouldBe(Condition.visible).click();
        return new ContactsPage();
    }


}
