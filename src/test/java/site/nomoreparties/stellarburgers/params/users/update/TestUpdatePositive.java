package site.nomoreparties.stellarburgers.params.users.update;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.UpdateUserBody;
import site.nomoreparties.stellarburgers.params.responses.user.update.UpdateUser;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestUpdatePositive extends BaseTest {

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(), getName());
    }
    @Step
    public Response makeRequest(){
        UpdateUserBody params = new UpdateUserBody(getNewmail(), getNewname());
        String token = extractTestToken(getEmail(), getPassword());
        Response response = given().header("Content-type", "application/json").body(params).auth().oauth2(token).patch("api/auth/user");
        return response;
    }
    @Step
    public UpdateUser makeRequestPojo(){
        UpdateUserBody params = new UpdateUserBody(getNewmail(), getNewname());
        String token = extractTestToken(getEmail(), getPassword());
        UpdateUser response = given().header("Content-type", "application/json").body(params).auth().oauth2(token).patch("api/auth/user").as(UpdateUser.class);
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(200);
    }
    @Step
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step
    public void checkEmail(UpdateUser response){
        Assert.assertEquals(getNewmail(), response.getUser().getEmail());
    }
    @Step
    public void checkName(UpdateUser response){
        Assert.assertEquals(getNewname(), response.getUser().getName());
    }
    @Test
    @DisplayName("Проверка кода и успеха")
    public void checkUpdateCodeSuccess(){
        Response response = makeRequest();
        checkCode(response);
        checkSuccess(response);
    }
    @Test
    @DisplayName("Проверка почты и имени")
    public void checkUpdateEmailName(){
        UpdateUser responsePojo = makeRequestPojo();
        checkEmail(responsePojo);
        checkName(responsePojo);
    }
    @After
    public void clearUp(){
        deleteTestUser(getNewmail(), getPassword());
    }
}
