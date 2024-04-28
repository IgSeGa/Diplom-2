package site.nomoreparties.stellarburgers.params.users.update;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.UpdateUserBody;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class TestUpdateNoAuth extends BaseTest {
    @Before
    public void setUp(){
        baseTestURL();
    }
    @Step
    public Response makeRequest(){
        UpdateUserBody params = new UpdateUserBody(getNewmail(), getNewname());
        Response response = given().header("Content-type", "application/json").body(params).patch("api/auth/user");
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(401);
    }
    @Step
    public void checkSuccess(Response response){
        response.then().body("success", equalTo(false));
    }
    @Step
    public void checkMessage(Response response){
        response.then().body("message", equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Проверка обновления без авторизации")
    public void testUpdateNoAuth(){
        Response response = makeRequest();
        checkCode(response);
        checkSuccess(response);
        checkMessage(response);
    }
}
