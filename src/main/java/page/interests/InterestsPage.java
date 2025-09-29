package page.interests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static locators.Locators.ID_ON_TABLE;
import static locators.Locators.NAME_ON_TABLE;

public class InterestsPage extends BasePage {
    // кнопка перехода к странице создания Лида
    private static final SelenideElement CREATE_INTERESTS_BUTTON = $(byText("Создать Лид"));

    @Step("Выбор кнопки 'Создать Лид'")
    public CreateInterestsPage openCreateInterests() {
        return openPage(CREATE_INTERESTS_BUTTON, CreateInterestsPage.class);
    }

    @Step("Получение id созданного Лида из таблицы")
    public String getIdInterest() {
        return ID_ON_TABLE.shouldBe(visible).getText();
    }

    @Step("Получение названия созданного Лида из таблицы")
    public String getNameInterest() {
        return NAME_ON_TABLE.shouldBe(visible).getText();
    }
}
