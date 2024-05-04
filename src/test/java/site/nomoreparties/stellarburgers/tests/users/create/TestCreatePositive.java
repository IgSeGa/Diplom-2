package site.nomoreparties.stellarburgers.tests.users.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.Constants;
import site.nomoreparties.stellarburgers.params.api.body.CreateUserBody;
import site.nomoreparties.stellarburgers.params.api.responses.user.create.CreateUser;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class TestCreatePositive extends BaseTest implements Constants {

    @Before
    public void setUp(){
        baseTestURL();
    }

    @Step
    public Response createUser(){
        CreateUserBody params = new CreateUserBody(TESTMAIL, TESTPASS, TESTNAME);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/register");
        return response;
    }
    @Step
    public CreateUser createUserPojo(){
        CreateUserBody params = new CreateUserBody(TESTMAIL, TESTPASS, TESTNAME);
        CreateUser response = given().header("Content-type", "application/json").and().body(params).post("api/auth/register").as(CreateUser.class);
        return response;
    }
    @Step
    public void checkName(CreateUser response){
        Assert.assertEquals(TESTNAME, response.getUser().getName());
    }
    @Step
    public void checkEmail(CreateUser response){
        Assert.assertEquals(TESTMAIL, response.getUser().getEmail());
    }
    @Step
    public void checkStatus(Response response){
        response.then().statusCode(200);
    }

    @Step
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }

    @Step
    public void checkUser(Response response){
        response.then().assertThat().body("user", notNullValue());
    }

    @Step
    public void checkToken(Response response){
        response.then().body("accessToken", notNullValue());
    }
    @Step
    public void checkRefreshToken(Response response){
        response.then().body("refreshToken", notNullValue());
    }


    @Test
    @DisplayName("Проверка успешного создания пользователя")
    public void createUserPositive(){
        Response response = createUser();
        checkStatus(response);
        checkSuccess(response);
        checkUser(response);
        checkToken(response);
        checkRefreshToken(response);
    }
    @Test
    @DisplayName("Проверка корректной записи имени и почты")
    public void checkDetails(){
        CreateUser responsePojo = createUserPojo();
        checkName(responsePojo);
        checkEmail(responsePojo);
    }

    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}