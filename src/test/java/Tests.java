import org.junit.jupiter.api.Test;
import page.BaseSelenidePage;
import web.AutorizationPage;
import web.MainPage;

public class Tests extends BaseSelenidePage {

    private final String LOGIN = "admin@admin.ru";
    private final String PASSWORD = "GpDW5Z?mlJDY";
    private final String BASE_URL_LOGIN = "https://172.20.206.107/login";

    public void authorization() {
        new AutorizationPage(BASE_URL_LOGIN).autorization(LOGIN, PASSWORD);
    }

    @Test
    public void createNewContact() {
        AutorizationPage autorizationPage = new AutorizationPage(BASE_URL_LOGIN);
        autorizationPage.autorization(LOGIN, PASSWORD);
        MainPage mainPage = new MainPage();
        mainPage.openContactsPage().clickButtonCreateNewContact().createNewContact().check();
    }
}
