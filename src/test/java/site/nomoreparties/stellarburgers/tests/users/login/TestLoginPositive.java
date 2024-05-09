package site.nomoreparties.stellarburgers.tests.users.login;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.tests.BaseTest;
import site.nomoreparties.stellarburgers.params.api.body.LoginUserBody;
import site.nomoreparties.stellarburgers.params.api.responses.user.login.LoginUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestLoginPositive extends BaseTest{

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }

    @Step("Отправка запроса на логин")
    public Response loginUser(){
        LoginUserBody params = new LoginUserBody(TESTMAIL, TESTPASS);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/login");
        return response;
    }
    @Step("Отправка запроса на логин с ответом в объекте")
    public LoginUser loginUserPojo(){
        LoginUserBody params = new LoginUserBody(TESTMAIL, TESTPASS);
        LoginUser response = given().header("Content-type", "application/json").and().body(params)
                .post("api/auth/login").as(LoginUser.class);
        return response;
    }
    @Step("Проверка кода ответа")
    public void checkStatus(Response response){
        response.then().statusCode(200);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step("Проверка токена доступа")
    public void checkAccess(Response response){
        response.then().assertThat().body("accessToken", notNullValue());
    }
    @Step("Проверка токена обновления")
    public void checkRefresh(Response response){
        response.then().assertThat().body("refreshToken", notNullValue());
    }
    @Step("Проверка почты")
    public void checkEmail(LoginUser response){
        Assert.assertEquals(TESTMAIL, response.getUser().getEmail());
    }
    @Step("Проверка имени")
    public void checkName(LoginUser response){
        Assert.assertEquals(TESTNAME, response.getUser().getName());
    }
    @Test
    @DisplayName("Логин позитивная проверка")
    public void checkLoginPositive(){
        Response response = loginUser();
        checkStatus(response);
        checkSuccess(response);
        checkAccess(response);
        checkRefresh(response);
        LoginUser responsePojo = loginUserPojo();
        checkEmail(responsePojo);
        checkName(responsePojo);
    }

    @After
    public void clearData(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}