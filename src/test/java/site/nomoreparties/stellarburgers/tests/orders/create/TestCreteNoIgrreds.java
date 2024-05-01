package site.nomoreparties.stellarburgers.tests.orders.create;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.Constants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCreteNoIgrreds extends BaseTest implements Constants {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step
    public Response makeOrder(){
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().header("Content-type", "application/json").auth().oauth2(token).post("api/orders");
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(400);
    }
    @Step
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(false));
    }
    @Step
    public void checkMessage(Response response){
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }
    @Test
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
