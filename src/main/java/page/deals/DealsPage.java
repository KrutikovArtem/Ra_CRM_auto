package page.deals;

import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static locators.Locators.ID_ON_TABLE;
import static locators.Locators.NAME_ON_TABLE;

public class DealsPage extends BasePage {

    @Step("Получение id созданного Лида из таблицы")
    public String getIdInterest() {
        return ID_ON_TABLE.shouldBe(visible).getText();
    }

    @Step("Получение названия созданного Лида из таблицы")
    public String getNameInterest() {
        return NAME_ON_TABLE.shouldBe(visible).getText();
    }
}
