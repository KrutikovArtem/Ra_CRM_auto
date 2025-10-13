
import browser.ConfigurationBrowser;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import constants.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.BasePage;
import page.addAgreements.AddAgreementsPage;
import page.agreements.AgreementsPage;
import page.calendar.CalendarPage;
import page.catalog.CatalogPage;
import page.implemenationTerms.ImplementTermsPage;
import page.importation.ImportPage;
import page.infometrics.InfometricsPage;
import page.interests.InterestsPage;
import page.notifications.NotificationsPage;
import page.payments.PaymentsPage;
import page.sbercity.SberCityPage;
import page.scoring.ScoringPage;
import page.settlements.SettlementsPage;
import page.trade_in.TradeInPage;
import web.AutorizationPage;
import web.MainPage;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static web.MainPage.*;

public class TestOpenEntities {

    // создание экземпляра класса с главной страницей
    private static final MainPage mainPage = new MainPage();

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
//                arguments(DashboardPage.class, DASHBOARDS, "", "Создать дашборд", false, false),
//                arguments(MeetingPage.class, MEETING, "Дела", "Создать Дело", true, false),
//                arguments(ContactsPage.class, CONTACTS, "Контакты", "Создать Контакт", true, false),
//                arguments(InterestsPage.class, INTERESTS, "Лиды", "Создать Лид", true, false),
//                arguments(DealsPage.class, DEALS, "Сделки", "", true, false),
                arguments(AddAgreementsPage.class, ADD_AGREEMENTS, "Дополнительные соглашения", "", true, false),
                arguments(ImplementTermsPage.class, IMPL_TERMS, "Условия реализации", "Добавить шаблон", true, true),
//                arguments(MortgagePage.class, MORTGAGE, "Ипотека", "", true, false),
//                arguments(SettlementsPage.class, SETTLEMENTS, "Заселение", "", true, true),
//                arguments(TradeInPage.class, TRADE_IN, "Трейд-ин", "", true, false),
//                arguments(SberCityPage.class, SBER_CITY, "СберСити+", "", true, false),
//                arguments(ScoringPage.class, SCORING, "Скоринг", "Создать Скоринг", true, false),
//                arguments(InfometricsPage.class, INFOMETRICS, "CSI Ипотека", "", true, true),
//                arguments(CatalogPage.class, CATALOG, "Каталог", "Все объекты", false, false),
//                arguments(ImportPage.class, IMPORT, "Импорт ОН", "", false, false),
//                arguments(AgreementsPage.class, AGREEMENTS, "Согласования", "", true, false),
//                arguments(CalendarPage.class, CALENDAR, "Календарь", "Создать Дело", false, true),
                arguments(PaymentsPage.class, PAYMENTS, "Платежи", "", true, false),
                arguments(NotificationsPage.class, NOTIFICATION, "Уведомления", "Настроить мои уведомления", true, true)
        );
    }

    @ParameterizedTest
    @MethodSource("entitiesData")
    public void testOpenEntities(Class<? extends BasePage> pageClass,  // класс сущности
                                 SelenideElement nameEntity,           // локатор сущности
                                 String entityTitle,                   // заголовок сущности
                                 String actionButton,                  // кнопка действия в сущности
                                 boolean tableVisible,                 // есть ли таблица в сущности
                                 boolean blocks) {                     // есть ли доп. блоки в сущности

        // выбор сущности
        BasePage openEntity = mainPage.openPage(nameEntity, pageClass);

        assertAll("Сущности открываются на просмотр и соответствуют ТЗ",
                () -> assertEquals(entityTitle, openEntity.getTitle(entityTitle),
                        "Заголовок выбранной сущности = '"+ entityTitle +"'"),
                () -> assertEquals(actionButton, openEntity.getCreateButtonText(actionButton),
                        "Должна отображаться кнопка действия: '"+ actionButton +"'"),
                () -> assertEquals(tableVisible, openEntity.tableIsVisible(),
                        "Должно быть отображение таблицы на странице = '"+ tableVisible +"'"),
                () -> assertEquals(Constants.TEXT_COLOR_ENTITY, openEntity.getColorTextEntity(nameEntity),
                        "Цвет кнопки выбранной сущности должен быть = '"+ nameEntity +"'"),
                () -> assertEquals(blocks, openEntity.tabsIsVisible(entityTitle),
                        "Открытие блоков в сущности: '"+ blocks +"'")
        );
    }

    @AfterAll
    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
