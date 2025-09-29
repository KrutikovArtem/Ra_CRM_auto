
import Browser.ConfigurationBrowser;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import constants.Constants;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.BasePage;
import page.contact.ContactsPage;
import page.dashboard.DashboardPage;
import page.interests.InterestsPage;
import page.meetings.MeetingPage;
import web.AutorizationPage;
import web.MainPage;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static web.MainPage.*;

public class TestOpenEntities {

    // создание экземпляра класса с главной страницей
    private static MainPage mainPage = new MainPage();

    // авторизация 1 раз для всех тестов
    @BeforeAll
    public static void auth() {
        ConfigurationBrowser config = new ConfigurationBrowser(InitialData.AUTH_PAGE_URL);
        config.configuration();
        AutorizationPage autorizationPage = new AutorizationPage(InitialData.AUTH_PAGE_URL);
        autorizationPage.authorization(InitialData.REGISTRATION_EMAIL, InitialData.REGISTRATION_PASSWORD);
    }

    static Stream<Arguments> entitiesData() {
        return Stream.of(
//                arguments(DashboardPage.class, DASHBOARDS, "", "Создать дашборд", false),
//                arguments(MeetingPage.class, MEETING, "Дела", "Создать Дело", true),
                arguments(ContactsPage.class, CONTACTS, "Контакты", "Создать Контакт", true),
                arguments(InterestsPage.class, INTERESTS, "Лиды", "Создать Лид", true)
        );
    }

    @ParameterizedTest
    @MethodSource("entitiesData")
    public void testInterestCreate(Class<? extends BasePage> pageClass,  // класс сущности
                                   SelenideElement nameEntity,           // локатор сущности
                                   String entityTitle,                   // заголовок сущности
                                   String actionButton,                  // кнопка действия в сущности
                                   boolean tableVisible) {               // есть ли таблица в сущности

        // выбор сущности
        BasePage openEntity = mainPage.openPage(nameEntity, pageClass);

        assertAll("Сущности открываются на просмотр и соответствуют ТЗ",
                () -> assertEquals(entityTitle, openEntity.getTitle(entityTitle),
                        "Заголовок выбранной сущности = '"+ entityTitle +"'"),
                () -> assertEquals(Constants.TEXT_COLOR_ENTITY, openEntity.getColorTextEntity(nameEntity),
                        "Цвет кнопки выбранной сущности должен быть '"+ nameEntity +"'"),
                () -> assertEquals(actionButton, openEntity.getCreateButtonText(actionButton),
                        "Должна отображаеться кнопка действия '"+ actionButton +"'"),
                () -> assertEquals(tableVisible, openEntity.tableIsVisible(),
                        "Должно быть отображение таблицы на странице = '"+ tableVisible +"'")
        );
    }

    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
