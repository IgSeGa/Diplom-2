package site.nomoreparties.stellarburgers.params.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.responses.order.create.CreateOrder;
import site.nomoreparties.stellarburgers.params.responses.order.get.GetOrder;
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
        GetOrder order = given().auth().oauth2(extractTestToken(getEmail(), getPassword())).get("api/orders").as(GetOrder.class);
        return order;
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
        checkTotal(response);
        checkTotalToday(response);
    }
    @Test
    public void checkDetails(){
        CreateOrder etalon = createTestOrderPojo(getIngreds(), extractTestToken(getEmail(), getPassword()));
        GetOrder order = makeRequestPojo();
        Assert.assertEquals(order.getOrders().get(0).getNumber(), etalon.getOrder().getNumber());
        Assert.assertEquals(order.getOrders().get(0).get_id(), etalon.getOrder().get_id());
        Assert.assertEquals(order.getOrders().get(0).getName(), etalon.getOrder().getName());
    }
    @After
    public void cleanUp(){
        deleteTestUser(getEmail(), getPassword());
    }
}
