import api.UserJSON;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import page.meetings.MeetingPage;
import rest.SupportREST;
import web.MainPage;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rest.SupportREST.DELETE_MEETING_API;
import static rest.SupportREST.MEETING_LIST_API;
import static web.MainPage.hrefMeeting;

@ExtendWith(AllureJunit5.class)
public class TestCreateMeeting extends BaseSelenidePage {

    // инициализация REST запросов
    SupportREST supportREST = new SupportREST();
    // токен для авторизации по REST запросу
    private String token;
    // id дела
    private int id;

    @Test
    public void meetingCreate() {
        // получение токена
        token = supportREST.getToken(new UserJSON(InitialData.REGISTRATION_EMAIL,
                InitialData.REGISTRATION_PASSWORD,
                InitialData.REMEMBER_ME));

        // создание экземпляра класса с главной страницей
        MainPage mainPage = new MainPage();
        // открытие таблицы с делами
        MeetingPage meetingPage = mainPage.openPage(hrefMeeting, MeetingPage.class);
        // создание дела
        meetingPage.openCreateMeeting().createNewMeeting();
        // получение id созданного дела
        id = supportREST.getId(token, MEETING_LIST_API);

        assertAll("Созданное дело отображается в таблице",
                () -> assertEquals(meetingPage.getIdMeeting(), String.valueOf(id)),
                () -> assertEquals(meetingPage.getNameMeeting(), String.valueOf(supportREST.getName(token, MEETING_LIST_API)))
        );
    }

    @AfterEach
    public void tearDown() {
        // удаление дела
        supportREST.deleteEntity(token, id, DELETE_MEETING_API);
        Selenide.closeWebDriver();
    }
}
