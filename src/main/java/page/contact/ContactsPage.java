package page.contact;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static locators.Locators.ID_ON_TABLE;
import static locators.Locators.NAME_ON_TABLE;

public class ContactsPage extends BasePage {

    SelenideElement buttonCreateNewContact = $x("//span[text()='Создать Контакт']");
    // кнопка перехода к странице создания контакта
    private static final SelenideElement CREATE_CONTACT_BUTTON = $(byText("Создать Контакт"));

    public CreateNewContact clickButtonCreateNewContact(){
        buttonCreateNewContact.shouldBe(Condition.enabled).click();
        return new CreateNewContact();
    }

    @Step("Достаём текст названия созданного контакта")
    public String getNameContact() {
        return NAME_ON_TABLE.shouldBe(visible).getText();
    }

    @Step("Достаём id контакта в таблице")
    public String getIdContact() {
        return ID_ON_TABLE.shouldBe(visible).getText();
    }
}
