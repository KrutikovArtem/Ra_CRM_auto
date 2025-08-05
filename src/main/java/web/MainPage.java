package web;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

import io.qameta.allure.Step;
import page.BasePage;
import page.meetings.MeetingPage;

public class MainPage extends BasePage{

    private SelenideElement hrefSearch = $x("//input[@placeholder='Поиск...']");
    private SelenideElement hrefMeeting = $x("//a[@href='/meetings']");
    private SelenideElement hrefContacts = $x("//a[@href='/contacts']");
    private SelenideElement hrefInterests = $x("//a[@href='/interests']");




    public ContactsPage openContactsPage(){
        hrefContacts.shouldBe(visible).click();
        return new ContactsPage();
    }

    // проверяем отображение кнопки "Дела", выбираем её
    @Step("Открытие сущности")
    public MeetingPage open() {
        return openPage(hrefMeeting, MeetingPage.class);
    }
}
