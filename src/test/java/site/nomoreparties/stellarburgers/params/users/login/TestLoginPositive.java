package site.nomoreparties.stellarburgers.params.users.login;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.LoginUserBody;
import site.nomoreparties.stellarburgers.params.responses.user.login.LoginUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestLoginPositive extends BaseTest {
    private String email = "diplomauser@praktikum.ru";
    private String password = "1234";
    private String name = "Vasya";

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(email,password,name);
    }

    @Step
    public Response loginUser(){
        LoginUserBody params = new LoginUserBody(email,password);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/login");
        return response;
    }
    @Step
    public LoginUser loginUserPojo(){
        LoginUserBody params = new LoginUserBody(email,password);
        LoginUser response = given().header("Content-type", "application/json").and().body(params).post("api/auth/login").as(LoginUser.class);
        return response;
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
    public void checkAccess(Response response){
        response.then().assertThat().body("accessToken", notNullValue());
    }
    @Step
    public void checkRefresh(Response response){
        response.then().assertThat().body("refreshToken", notNullValue());
    }
    @Step
    public void checkEmail(LoginUser response){
        Assert.assertEquals(email, response.getUser().getEmail());
    }
    @Step
    public void checkName(LoginUser response){
        Assert.assertEquals(name, response.getUser().getName());
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
        deleteTestUser(email,password);
    }
}
