import api.UserJSON;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import page.interests.InterestsPage;
import rest.SupportREST;
import web.MainPage;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rest.SupportREST.*;
import static web.MainPage.hrefInterests;

@ExtendWith(AllureJunit5.class)
public class TestCreateInterest extends BaseSelenidePage {

    // инициализация REST запросов
    SupportREST supportREST = new SupportREST();
    // токен для авторизации по REST запросу
    private String token;
    // id дела
    private int id;

    @Test
    public void testInterestCreate() {

        // получение токена
        token = supportREST.getToken(new UserJSON(InitialData.REGISTRATION_EMAIL,
                InitialData.REGISTRATION_PASSWORD,
                InitialData.REMEMBER_ME));

        // создание экземпляра класса с главной страницей
        MainPage mainPage = new MainPage();
        // открытие таблицы с Лидами
        InterestsPage interestsPage = mainPage.openPage(hrefInterests, InterestsPage.class);
        // переход к созданию Лида (заполнение полей и выбор кнопки "Создать Лид")
        interestsPage.openCreateInterests().createNewInterest();
        // получение id созданного дела
        id = supportREST.getId(token, MEETING_LIST_API);

        assertAll("Созданный Лид отображается в таблице",
                () -> assertEquals(interestsPage.getIdInterest(), String.valueOf(id)),
                () -> assertEquals(interestsPage.getNameInterest(), String.valueOf(supportREST.getName(token, INTEREST_LIST_API)))
        );
    }

    @Override
    public void tearDown() {
        // удаление дела
        supportREST.deleteEntity(token, id, DELETE_MEETING_API);
        super.tearDown();
    }
}
