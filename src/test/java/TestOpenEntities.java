
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import page.dashboard.DashboardPage;
import web.MainPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static web.MainPage.DASHBOARDS;

public class TestOpenEntities extends BaseSelenidePage {

    static Stream<Arguments> isAdultTestData() {
        return Stream.of(
                arguments(-1, false),
                arguments(0, false),
                arguments(17, false),
                arguments(18, true),
                arguments(19, true),
                arguments(25, true)
        );
    }

    @Test
    public void testInterestCreate() {
        // создание экземпляра класса с главной страницей
        MainPage mainPage = new MainPage();
        // открытие таблицы с Лидами
        DashboardPage dashboardPage = mainPage.openPage(DASHBOARDS, DashboardPage.class);

        System.out.println(dashboardPage.getColorTextEntity());

        assertAll("Созданный Лид отображается в таблице",
                () -> assertEquals(dashboardPage.getColorTextEntity(), InitialData.TEXT_COLOR_ENTITY),
                () -> assertEquals(dashboardPage.getCreateDashboardText(), "Создать дашборд")
        );
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
