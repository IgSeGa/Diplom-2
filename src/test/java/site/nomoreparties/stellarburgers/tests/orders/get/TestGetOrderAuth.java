package site.nomoreparties.stellarburgers.tests.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.Constants;
import site.nomoreparties.stellarburgers.params.api.responses.order.create.CreateOrder;
import site.nomoreparties.stellarburgers.params.api.responses.order.get.GetOrder;
import static io.restassured.RestAssured.given;

public class TestGetOrderAuth extends BaseTest implements Constants {

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step
    public Response makeRequest(){
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().auth().oauth2(token).get("api/orders");
        return response;
    }
    @Step
    public GetOrder makeRequestPojo(){
        GetOrder order = given().auth().oauth2(extractTestToken(TESTMAIL, TESTPASS)).get("api/orders").as(GetOrder.class);
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
        CreateOrder etalon = createTestOrderPojo(INGREDS, extractTestToken(TESTMAIL, TESTPASS));
        GetOrder order = makeRequestPojo();
        Assert.assertEquals(order.getOrders().get(0).getNumber(), etalon.getOrder().getNumber());
        Assert.assertEquals(order.getOrders().get(0).get_id(), etalon.getOrder().get_id());
        Assert.assertEquals(order.getOrders().get(0).getName(), etalon.getOrder().getName());
    }
    @After
    public void cleanUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}
