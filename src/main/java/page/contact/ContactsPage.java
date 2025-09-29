package page.contact;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import page.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ContactsPage extends BasePage {

    SelenideElement buttonCreateNewContact = $x("//span[text()='Создать Контакт']");
    // кнопка перехода к странице создания контакта
    private static final SelenideElement CREATE_CONTACT_BUTTON = $(byText("Создать Контакт"));
    // локатор id контакта в таблице с контактами
    private static final SelenideElement CONTACT_ID = $x(".//tr[@class = 'clickable-JNIQAI']//td[@data-cell-name='id']/div");
    // локатор наименования контакта в таблице с делами
    private static final SelenideElement CONTACT_NAME = $x(".//tr[@class = 'clickable-JNIQAI']//td[@data-cell-name='topic']/div");

    public CreateNewContact clickButtonCreateNewContact(){
        buttonCreateNewContact.shouldBe(Condition.enabled).click();
        return new CreateNewContact();
    }

    @Step("Достаём текст названия созданного контакта")
    public String getNameContact() {
        return CONTACT_NAME.shouldBe(visible).getText();
    }

    @Step("Достаём id контакта в таблице")
    public String getIdContact() {
        return CONTACT_ID.shouldBe(visible).getText();
    }
}
