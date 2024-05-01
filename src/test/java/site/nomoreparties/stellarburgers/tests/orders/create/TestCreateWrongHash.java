package site.nomoreparties.stellarburgers.tests.orders.create;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.model.BaseTest;
import site.nomoreparties.stellarburgers.model.Constants;


public class TestCreateWrongHash extends BaseTest implements Constants {
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step
    public Response makeOrder(){
        String [] ingreds = {""};
        String token = extractTestToken(TESTMAIL, TESTPASS);
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
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}
