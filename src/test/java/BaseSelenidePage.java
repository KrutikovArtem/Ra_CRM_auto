
import Browser.ConfigurationBrowser;
import org.junit.jupiter.api.BeforeEach;
import web.AutorizationPage;

abstract public class BaseSelenidePage {

    /**
     * Инициализация selenide с настройками
     */
    public void setUp() {
        ConfigurationBrowser config = new ConfigurationBrowser(InitialData.AUTH_PAGE_URL);
        config.configuration();
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
