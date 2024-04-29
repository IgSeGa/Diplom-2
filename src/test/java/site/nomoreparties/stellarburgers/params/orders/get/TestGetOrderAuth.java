package site.nomoreparties.stellarburgers.params.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.responses.order.create.CreateOrder;
import site.nomoreparties.stellarburgers.params.responses.order.get.GetOrder;

import java.security.PublicKey;

import static io.restassured.RestAssured.given;

public class TestGetOrderAuth extends BaseTest{

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(),getName());
    }
    @Step
    public Response makeRequest(){
        String token = extractTestToken(getEmail(), getPassword());
        Response response = given().auth().oauth2(token).get("api/orders");
        return response;
    }
    @Step
    public GetOrder makeRequestPojo(){
        String token = extractTestToken(getEmail(), getPassword());
        GetOrder response = given().auth().oauth2(token).get("api/orders").as(GetOrder.class);
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
    public void checkOrder(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step
    public void checkTotal(Response response){
        response.then().assertThat().body("total", notNullValue());
    }
    @Step
    public void checkTotalToday(Response response){
        response.then().assertThat().body("totalToday", notNullValue());
    }

    @Test
    public void checkFields(){
        Response response = makeRequest();
        checkStatus(response);
        checkSuccess(response);
        checkOrder(response);
        checkTotal(response);
        checkTotalToday(response);
    }
    @After
    public void cleanUp(){
        deleteTestUser(getEmail(), getPassword());
    }
}
