package site.nomoreparties.stellarburgers.params.orders.create;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestCreateWrongHash extends BaseTest {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(),getName());
    }
    @Step
    public Response makeOrder(){
        String [] ingreds = {""};
        String token = extractTestToken(getEmail(), getPassword());
        Response response = createTestOrder(ingreds, token);
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(500);
    }
    @Test
    public void testCreateNoIgred(){
        Response response = makeOrder();
        checkCode(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(getEmail(), getPassword());
    }
}
