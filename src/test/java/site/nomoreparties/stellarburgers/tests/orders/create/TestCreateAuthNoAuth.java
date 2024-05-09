package site.nomoreparties.stellarburgers.tests.orders.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import site.nomoreparties.stellarburgers.tests.BaseTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.api.body.CrateOrderBody;

public class TestCreateAuthNoAuth extends BaseTest {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step("Создание заказа c авторизацией")
    public Response makeOrder(){
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = createTestOrder(INGREDS, token);
        return response;
    }
    @Step("Создание заказа без авторизации")
    public Response makeOrderNoAuth(){
        CrateOrderBody order = new CrateOrderBody(INGREDS);
        Response response = given().header("Content-type", "application/json").
                and().body(order).post("api/orders");
        return response;
    }

    @Step("Проверка кода")
    public void checkCode(Response response){
        response.then().statusCode(200);
    }
    @Step("Проверка успешности")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step("Проверка, что заказ не пустой")
    public void checkOrder(Response response){
        response.then().assertThat().body("order", notNullValue());
    }
    @Test
    @DisplayName("Проверка создания заказа с авторизацией")
    public void testOrderAuth(){
        Response response = makeOrder();
        checkCode(response);
        checkSuccess(response);
        checkOrder(response);

    }
    @Test
    @DisplayName("Проверка создания заказа без авторизации")
    public void testOrderNoAuth(){
        Response response = makeOrderNoAuth();
        checkCode(response);
        checkSuccess(response);
        checkOrder(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL,TESTPASS);
    }
}
