package page.meetings;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import page.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MeetingPage extends BasePage {

    // кнопка перехода к странице создания дела
    private static final SelenideElement CREATE_MEETING_BUTTON = $x("//button[.//span[text()='Создать дело']]");
    // локатор id дела в таблице с делами
    private static final SelenideElement ID_MEETING_ON_TABLE = $x(".//tr[@class = 'clickable-JNIQAI']//td[@data-cell-name='id']/div");
    // локатор наименования дела
    private static final SelenideElement MEETING_NAME = $x("//div[@class='ant-page-header-heading']//div[@class='page-header-title-content-WVt9FJ']");


    @Step("Выбор кнопки 'Создать дело'")
    public CreateMeetingPage openCreateMeeting() {
        return openPage(CREATE_MEETING_BUTTON, CreateMeetingPage.class);
    }

    @Step("Достаём текст названия дела")
    public String getNameMeeting() {
        return MEETING_NAME.shouldBe(visible).getText();
    }

    @Step("Достаём id дела в таблице")
    public String getIdMeetingOnTable() {
        return ID_MEETING_ON_TABLE.shouldBe(visible).getText();
    }
}
