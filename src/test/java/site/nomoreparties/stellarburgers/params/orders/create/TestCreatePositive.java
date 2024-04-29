package site.nomoreparties.stellarburgers.params.orders.create;
import io.qameta.allure.Step;
import org.junit.Before;
import site.nomoreparties.stellarburgers.params.BaseTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.body.CrateOrderBody;

public class TestCreatePositive extends BaseTest {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(),getName());
    }
    @Step
    public Response makeOrder(){
        String token = extractTestToken(getEmail(), getPassword());
        Response response = createTestOrder(getIngreds(), token);
        return response;
    }
    @Step
    public Response makeOrderNoAuth(){
        CrateOrderBody order = new CrateOrderBody(getIngreds());
        Response response = given().header("Content-type", "application/json").
                and().body(order).post("api/orders");
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
    public void checkOrder(Response response){
        response.then().assertThat().body("order", notNullValue());
    }
    @Test
    public void testOrderAuth(){
        Response response = makeOrder();
        checkCode(response);
        checkSuccess(response);
        checkOrder(response);
    }
    @Test
    public void testOrderNoAuth(){
        Response response = makeOrderNoAuth();
        checkCode(response);
        checkSuccess(response);
        checkOrder(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(getEmail(),getPassword());
    }
}
