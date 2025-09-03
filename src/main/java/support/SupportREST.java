package support;

import api.UserJSON;
import api.meetingRequest.FilterRequest;
import api.meetingRequest.MeetingRequest;
import api.meetingRequest.SortOption;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Collections;
import static io.restassured.RestAssured.given;

public class SupportREST {

    // Endpoint авторизации
    public static final String SIGN_IN_API = "/api/v1/auth/signin";
    // Endpoint получения списка дел
    public static final String MEETING_LIST_API = "/api/v1/meeting/list";
    // Endpoint удаления дела
    public static final String DELETE_MEETING_API = "/api/v1/meeting";

    @Step("Получение токена авторизации")
    public String getToken(UserJSON creds) {

        Response response = given()
                .header("Content-type", "application/json")
                .log().all()
                .body(creds)
                .when()
                .post(SIGN_IN_API)
                .then()
                .statusCode(200)  // проверка успешного статус
                .extract()
                .response();

        // Явно используем jsonPath() для извлечения значения
        return response.jsonPath().getString("token");
    }

    // получить айди созданного дела
    public int getIdMeeting(String token) {
        Response response = getMeetingList(token);
        return response.path("data[0].id");
    }

    // получить название дела
    public String getNameMeeting(String token) {
        Response response = getMeetingList(token);
        return response.path("data[0].name");
    }

    @Step("Получение списка дел")
    public Response getMeetingList(String token) {

        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequest.setPage(1);
        meetingRequest.setSize(10);

        FilterRequest filter = new FilterRequest();
        filter.setMistaken(false);
        meetingRequest.setFilter(filter);

        SortOption sort = new SortOption();
        sort.setProperty("id");
        sort.setDirection("DESC");
        meetingRequest.setSortBy(Collections.singletonList(sort));

        Response response = given()
                .header("Content-type", "application/json")
                .header("authorization", "Bearer "+ token)
                .log().all()
                .body(meetingRequest)
                .when()
                .post(MEETING_LIST_API);
        response.then().statusCode(200);

        return response;
    }

    @Step("Удаление созданного дела")
    public void deleteMeeting(String token, int idMeeting) {
        Response response = given()
                .header("authorization", "Bearer "+ token)
                .log().all()
                .pathParams("id", idMeeting)
                .when()
                .delete(DELETE_MEETING_API + "/{id}");

        response.then()
                .log().all()
                .statusCode(200); // проверка статус код
    }
}
