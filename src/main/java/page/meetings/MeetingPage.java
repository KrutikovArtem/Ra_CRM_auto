package page.meetings;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static locators.Locators.ID_ON_TABLE;
import static locators.Locators.NAME_ON_TABLE;

public class MeetingPage extends BasePage {
    // кнопка перехода к странице создания дела
    private static final SelenideElement CREATE_MEETING_BUTTON = $(byText("Создать Дело"));

    @Step("Выбор кнопки 'Создать дело'")
    public CreateMeetingPage openCreateMeeting() {
        return openPage(CREATE_MEETING_BUTTON, CreateMeetingPage.class);
    }

    @Step("Достаём текст названия созданного дела")
    public String getNameMeeting() {
        return NAME_ON_TABLE.shouldBe(visible).getText();
    }

    @Step("Достаём id дела в таблице")
    public String getIdMeeting() {
        return ID_ON_TABLE.shouldBe(visible).getText();
    }
}
