import api.UserJSON;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import page.meetings.MeetingPage;
import support.SupportREST;
import web.AutorizationPage;
import web.MainPage;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static web.MainPage.hrefMeeting;

@ExtendWith(AllureJunit5.class)
public class TestCreateMeeting extends BaseSelenidePage {

    // инициализация REST запросов
    SupportREST supportREST = new SupportREST();
    // токен для авторизации по REST запросу
    public String token;

    @Test
    public void meetingCreate() {
        // получение токена
        token = supportREST.getToken(new UserJSON(InitialData.REGISTRATION_EMAIL,
                InitialData.REGISTRATION_PASSWORD,
                InitialData.REMEMBER_ME));

        AutorizationPage autorizationPage = new AutorizationPage(InitialData.AUTH_PAGE_URL);
        autorizationPage.authorization(InitialData.REGISTRATION_EMAIL, InitialData.REGISTRATION_PASSWORD);

        // создаём экземпляр класса с главной страницей
        MainPage mainPage = new MainPage();
        // добираемся до страницы с делами
        MeetingPage meetingPage = mainPage.openPage(hrefMeeting, MeetingPage.class);
        // создаём дело
        meetingPage.openCreateMeeting().createNewMeeting();

        assertAll("Созданное дело отображается в таблице",
                () -> assertEquals(meetingPage.getIdMeeting(), String.valueOf(supportREST.getIdMeeting(token))),
                () -> assertEquals(meetingPage.getNameMeeting(), String.valueOf(supportREST.getNameMeeting(token)))
        );
    }

    @AfterEach
    public void tearDown() {
        // получение id созданного дела
        int id = supportREST.getIdMeeting(token);
        // удаление дела
        supportREST.deleteMeeting(token, id);
        Selenide.closeWebDriver();
    }
}
