package page.deals;

import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static locators.Locators.ID_ON_TABLE;
import static locators.Locators.NAME_ON_TABLE;

public class DealsPage extends BasePage {

    @Step("Получение id созданной Сделки из таблицы")
    public String getIdDeals() {
        return ID_ON_TABLE.shouldBe(visible).getText();
    }

    @Step("Получение названия созданной Сделки из таблицы")
    public String getNameDeals() {
        return NAME_ON_TABLE.shouldBe(visible).getText();
    }
}
