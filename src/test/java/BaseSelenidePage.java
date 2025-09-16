
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import web.AutorizationPage;

abstract public class BaseSelenidePage {

    /**
     * Инициализация selenide с настройками
     */
    public void setUp() {
        RestAssured.config = RestAssured.config()
                .sslConfig(io.restassured.config.SSLConfig.sslConfig().relaxedHTTPSValidation());
        RestAssured.baseURI = InitialData.AUTH_PAGE_URL;
        Configuration.browser = "chrome";
        //Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "normal";
        signIn();
    }

    // авторизация пользователя
    private void signIn() {
        AutorizationPage autorizationPage = new AutorizationPage(InitialData.AUTH_PAGE_URL);
        autorizationPage.authorization(InitialData.REGISTRATION_EMAIL, InitialData.REGISTRATION_PASSWORD);
    }

    /**
     * Выполнение метода перед каждым запуском тестов
     */
    @BeforeEach
    public void init() {
        setUp();
    }

    /**
     * Выполнение метода после каждого закрытия тестов
     */
//    @AfterEach
//    public void tearDown() {
//        Selenide.closeWebDriver();
//    }
}
