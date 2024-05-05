package site.nomoreparties.stellarburgers.tests.orders.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.TestData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCreteNoIgrreds extends BaseTest implements TestData {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step("Создание заказа без ингредиентов")
    public Response makeOrder(){
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().header("Content-type", "application/json").auth().oauth2(token).post("api/orders");
        return response;
    }
    @Step("Проверка кода")
    public void checkCode(Response response){
        response.then().statusCode(400);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(false));
    }
    @Step("Проверка сообщения")
    public void checkMessage(Response response){
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
    @DisplayName("Проверка заказа без ингредиентов")
    public void testCreateNoIgred(){
        Response response = makeOrder();
        checkCode(response);
        checkSuccess(response);
        checkMessage(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}
