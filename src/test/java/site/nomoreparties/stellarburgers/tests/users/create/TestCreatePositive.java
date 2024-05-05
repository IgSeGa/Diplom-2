package site.nomoreparties.stellarburgers.tests.users.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.TestData;
import site.nomoreparties.stellarburgers.params.api.body.CreateUserBody;
import site.nomoreparties.stellarburgers.params.api.responses.user.create.CreateUser;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class TestCreatePositive extends BaseTest implements TestData {

    @Before
    public void setUp(){
        baseTestURL();
    }

    @Step("Создание пользователя")
    public Response createUser(){
        CreateUserBody params = new CreateUserBody(TESTMAIL, TESTPASS, TESTNAME);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/register");
        return response;
    }
    @Step("Создание пользователя с ответом в объекте")
    public CreateUser createUserPojo(){
        CreateUserBody params = new CreateUserBody(TESTMAIL, TESTPASS, TESTNAME);
        CreateUser response = given().header("Content-type", "application/json").and().body(params).post("api/auth/register").as(CreateUser.class);
        return response;
    }
    @Step("Проверка имени")
    public void checkName(CreateUser response){
        Assert.assertEquals(TESTNAME, response.getUser().getName());
    }
    @Step("Проверка почты")
    public void checkEmail(CreateUser response){
        Assert.assertEquals(TESTMAIL, response.getUser().getEmail());
    }
    @Step("Проверка кода состояния")
    public void checkStatus(Response response){
        response.then().statusCode(200);
    }

    @Step("Проверка поля success")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }

    @Step("Проверка поля user")
    public void checkUser(Response response){
        response.then().assertThat().body("user", notNullValue());
    }

    @Step("Проверка поля токена на доступ")
    public void checkToken(Response response){
        response.then().body("accessToken", notNullValue());
    }
    @Step("Проверка токена для обновления")
    public void checkRefreshToken(Response response){
        response.then().body("refreshToken", notNullValue());
    }


    @Test
    @DisplayName("Создание пользователя позитивная проверка")
    public void createUserPositive(){
        Response response = createUser();
        checkStatus(response);
        checkSuccess(response);
        checkUser(response);
        checkToken(response);
        checkRefreshToken(response);
        deleteTestUser(TESTMAIL, TESTPASS);
        CreateUser responsePojo = createUserPojo();
        checkName(responsePojo);
        checkEmail(responsePojo);
    }

    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}