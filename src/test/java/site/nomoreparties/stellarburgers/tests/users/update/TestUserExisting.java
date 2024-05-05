package site.nomoreparties.stellarburgers.tests.users.update;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.TestData;
import site.nomoreparties.stellarburgers.params.api.body.UpdateUserBody;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class TestUserExisting extends BaseTest implements TestData {

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
        createTestUser(SECONDMAIL, TESTPASS, SECONDNAME);
    }
    @Step("Создание запроса с используемыми данными")
    public Response makeRequest(){
        UpdateUserBody params = new UpdateUserBody(SECONDMAIL, TESTPASS);
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().header("Content-type", "application/json").body(params).auth().oauth2(token).patch("api/auth/user");
        return response;
    }
    @Step("Проверка кода ответа")
    public void checkCode(Response response){
        response.then().statusCode(403);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().body("success", equalTo(false));
    }
    @Step("Проверка сообщения")
    public void checkMessage(Response response){
        response.then().body("message", equalTo("User with such email already exists"));
    }
    @Test
    @DisplayName("Проверка обновления на используемые данные")
    public void testUpdateNoAuth(){
        Response response = makeRequest();
        checkCode(response);
        checkSuccess(response);
        checkMessage(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
        deleteTestUser(SECONDMAIL, TESTPASS);
    }
}
