import org.junit.jupiter.api.Test;
import page.BaseSelenidePage;
import web.AutorizationPage;
import web.MainPage;

public class TestCreateMeeting extends BaseSelenidePage {

    private final String LOGIN = "admin@admin.ru";
    private final String PASSWORD = "GpDW5Z?mlJDY";
    private final String BASE_URL_LOGIN = "https://172.20.206.107/login";

    @Test
    public void meetingCreate() {
//        AutorizationPage autorizationPage = new AutorizationPage("base.url");
//        autorizationPage.autorization("login", "password");
        AutorizationPage autorizationPage = new AutorizationPage(BASE_URL_LOGIN);
        autorizationPage.autorization(LOGIN, PASSWORD);

        MainPage mainPage = new MainPage();
        String nameTextMeeting = mainPage.open().openCreateMeeting().createNewMeeting().getNameMeeting();

        System.out.println(nameTextMeeting);
    }
}
