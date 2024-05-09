package site.nomoreparties.stellarburgers.tests.orders.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.tests.BaseTest;


public class TestCreateWrongHash extends BaseTest{
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step("Передача данных с неверным хэшем")
    public Response makeOrder(){
        String [] ingreds = {""};
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = createTestOrder(ingreds, token);
        return response;
    }
    @Step("Проверка кода ответа")
    public void checkCode(Response response){
        response.then().statusCode(500);
    }
    @Test
    @DisplayName("Проверка заказа с неверным хэшем")
    public void testCreateNoIgred(){
        Response response = makeOrder();
        checkCode(response);
    }
    @After
    public void clearUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}
