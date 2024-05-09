package site.nomoreparties.stellarburgers.tests.users.update;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.tests.BaseTest;
import site.nomoreparties.stellarburgers.params.api.body.UpdateUserBody;
import site.nomoreparties.stellarburgers.params.api.responses.user.update.UpdateUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestUpdatePositive extends BaseTest {

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step("Создание запроса на обновление")
    public Response makeRequest(){
        UpdateUserBody params = new UpdateUserBody(SECONDMAIL, SECONDNAME);
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().header("Content-type", "application/json").body(params).auth().oauth2(token).patch("api/auth/user");
        return response;
    }
    @Step("Создание запроса на обновление с ответом в объекте")
    public UpdateUser makeRequestPojo(){
        UpdateUserBody params = new UpdateUserBody(THIRDMAIL, THIRDNAME);
        String token = extractTestToken(SECONDMAIL, TESTPASS);
        UpdateUser response = given().header("Content-type", "application/json").body(params).auth().oauth2(token).patch("api/auth/user").as(UpdateUser.class);
        return response;
    }
    @Step("Проверка кода ответа")
    public void checkCode(Response response){
        response.then().statusCode(200);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step("Проверка почты")
    public void checkEmail(UpdateUser response){
        Assert.assertEquals(THIRDMAIL, response.getUser().getEmail());
    }
    @Step("Создание имени")
    public void checkName(UpdateUser response){
        Assert.assertEquals(THIRDNAME, response.getUser().getName());
    }
    @Test
    @DisplayName("Обновление данных позитивная проверка")
    public void checkUpdateCodeSuccess(){
        Response response = makeRequest();
        checkCode(response);
        checkSuccess(response);
        UpdateUser responsePojo = makeRequestPojo();
        checkEmail(responsePojo);
        checkName(responsePojo);
    }

    @After
    public void clearUp(){
        deleteTestUser(THIRDMAIL, TESTPASS);
    }
}
