package web;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import io.qameta.allure.Step;
import page.BasePage;
import page.contact.ContactsPage;

public class MainPage extends BasePage{

    private SelenideElement hrefSearch = $x("//input[@placeholder='Поиск...']");
    public static SelenideElement MEETING = $x(".//a[text() = 'Дела']");
    public static SelenideElement CONTACTS = $x("//a[text() = 'Контакты']");
    public static SelenideElement INTERESTS = $x("//a[text() = 'Лиды']");
    public static SelenideElement DASHBOARDS = $x(".//a[text() = 'Дашборды']");

    public ContactsPage openContactsPage(){
        CONTACTS.shouldBe(visible).click();
        return new ContactsPage();
    }

    // проверяем отображение выбранной сущности и тапаем на неё
    @Step("Открытие сущности")
    @Override
    public <T extends BasePage> T openPage(SelenideElement pageElement, Class<T> pageClass) {
        return super.openPage(pageElement, pageClass);
    }
}
