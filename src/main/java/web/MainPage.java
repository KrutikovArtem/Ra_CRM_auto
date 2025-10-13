package web;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import io.qameta.allure.Step;
import page.BasePage;
import page.contact.ContactsPage;

public class MainPage extends BasePage {

    private SelenideElement hrefSearch = $x("//input[@placeholder='Поиск...']");
    public static SelenideElement MEETING = $x(".//a[text() = 'Дела']");
    public static SelenideElement CONTACTS = $x("//a[text() = 'Контакты']");
    public static SelenideElement INTERESTS = $x("//a[text() = 'Лиды']");
    public static SelenideElement DASHBOARDS = $x(".//a[text() = 'Дашборды']");
    public static SelenideElement DEALS = $x("//a[text() = 'Сделки']");
    public static SelenideElement ADD_AGREEMENTS = $x("//a[text() = 'Доп. соглашения']");
    public static SelenideElement IMPL_TERMS = $x("//a[text() = 'Конструктор УР']");
    public static SelenideElement MORTGAGE = $x("//a[text() = 'Ипотека']");
    public static SelenideElement SETTLEMENTS = $x("//a[text() = 'Заселение']");
    public static SelenideElement SERVICES = $x("//div[contains(@data-menu-id, 'Сервисы')]");
    public static SelenideElement TRADE_IN = $x(".//a[text() = 'Трейд-ин']");
    public static SelenideElement SBER_CITY = $x(".//a[text() = 'СберСити+']");
    public static SelenideElement SCORING = $x(".//a[text() = 'Скоринг']");
    public static SelenideElement INFOMETRICS = $x(".//a[text() = 'Инфометрика']");
    public static SelenideElement CATALOG = $x(".//a[text() = 'Каталог']");
    public static SelenideElement IMPORT = $x(".//a[text() = 'Импорт ОН']");
    public static SelenideElement AGREEMENTS = $x(".//a[text() = 'Согласования']");
    public static SelenideElement CALENDAR = $x(".//a[text() = 'Календарь']");
    public static SelenideElement PAYMENTS = $x(".//a[text() = 'Платежи']");
    public static SelenideElement NOTIFICATION = $x(".//a[text() = 'Уведомления']");

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
