package site.nomoreparties.stellarburgers.tests.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import static io.restassured.RestAssured.given;

public class TestGetOrderNoAuth extends BaseTest {
    @Before
    public void setUp(){
        baseTestURL();
    }
    @Step
    public Response makeRequestNoAuth(){
        Response response = given().get("api/orders");
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(401);
    }
    @Step
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(false));
    }
    @Step
    public void checkMessage(Response response){
        response.then().assertThat().body("message", equalTo("You should be authorised"));
    }
    @Test
    @DisplayName("Получение неавторизованного заказа")
    public void checkOrderNoAuth(){
        Response response = makeRequestNoAuth();
        checkCode(response);
        checkSuccess(response);
        checkMessage(response);
    }
}
