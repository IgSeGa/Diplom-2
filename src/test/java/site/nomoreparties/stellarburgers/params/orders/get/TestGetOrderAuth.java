package site.nomoreparties.stellarburgers.params.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.params.BaseTest;
import static io.restassured.RestAssured.given;

public class TestGetOrderAuth extends BaseTest{

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(),getName());
        createTestOrder(getEmail(),getPassword());
    }

}
