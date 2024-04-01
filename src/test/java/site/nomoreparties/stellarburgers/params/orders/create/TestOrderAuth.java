package site.nomoreparties.stellarburgers.params.orders.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.CrateOrderBody;
import site.nomoreparties.stellarburgers.params.responses.order.create.CreateOrder;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class TestOrderAuth extends BaseTest {
    String[] ingreds = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"};
    String email = "diplomauser@praktikum.ru";
    String password = "1234";
    String burgername = "Бессмертный флюоресцентный бургер";

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(email, password, "Vasya");
    }
    @Step
    public Response makeOrderNoAuth(){
        CrateOrderBody params = new CrateOrderBody(ingreds);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/orders");
        return response;
    }
    @Step
    public CreateOrder makeOrderNoAuthPojo(){
        CrateOrderBody params = new CrateOrderBody(ingreds);
        CreateOrder response = given().header("Content-type", "application/json").and().body(params).post("api/orders").as(CreateOrder.class);
        return response;
    }
    @Step
    public Response makeOrderwithAuth(){
        CrateOrderBody params = new CrateOrderBody(ingreds);
        String token = extractTestToken(email,password);
        Response response = given().header("Content-type", "application/json").and().auth().oauth2(token).body(params).post("api/orders");
        return response;
    }
    @Step
    public CreateOrder makeOrderwithAuthPojo(){
        CrateOrderBody params = new CrateOrderBody(ingreds);
        String token = extractTestToken(email,password);
        CreateOrder response = given().header("Content-type", "application/json").and().auth().oauth2(token).body(params).post("api/orders").as(CreateOrder.class);
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
    public void checkName(Response response){
        response.then().assertThat().body("name", equalTo(burgername));
    }
    @Step
    public void checkOrder(Response response){
        response.then().assertThat().body("order", notNullValue());
    }
    @Step
    public void checkNumber(CreateOrder createOrder){
        Assert.assertNotEquals(createOrder.getOrder().getNumber(), 0);
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void orderNoAuth(){
        Response response = makeOrderNoAuth();
        checkCode(response);
        checkSuccess(response);
        checkName(response);
        checkOrder(response);
        CreateOrder createOrder = makeOrderNoAuthPojo();
        checkNumber(createOrder);
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void orderWithAuth(){
        Response response = makeOrderwithAuth();
        checkCode(response);
        checkSuccess(response);
        checkName(response);
        checkOrder(response);
        CreateOrder createOrder = makeOrderNoAuthPojo();
        checkNumber(createOrder);
    }
    @After
    public void clearData(){
        deleteTestUser(email,password);
    }
}
