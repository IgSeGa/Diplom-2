package site.nomoreparties.stellarburgers.params.users.login;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.LoginUserBody;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class TestLoginNegative extends BaseTest {
    private String email;
    private String password;
    private int code;
    private boolean success;
    private String message;
    public TestLoginNegative(String email, String password, int code, boolean success, String message){
        this.email = email;
        this.password = password;
        this.code = code;
        this.success = success;
        this.message = message;
    }
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"notauser@praktikum.ru", "1234", 401, false, "email or password are incorrect"},
                {"diplomauser@praktikum.ru", "notapassword", 401, false, "email or password are incorrect"},
                {"", "1234", 401, false, "email or password are incorrect"},
                {"diplomauser@praktikum.ru", "",  401, false, "email or password are incorrect"},
                {"", "",  401, false, "email or password are incorrect"}
        };
    }
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser("diplomauser@praktikum.ru", "1234", "Vasya");
    }
    @Step
    public Response loginIncorrect(){
        LoginUserBody params = new LoginUserBody(email, password);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/login");
        return response;
    }
    @Step
    public void checkCode(Response response){
        response.then().statusCode(code);
    }
    @Step
    public void checkMessage(Response response){
        response.then().assertThat().body("message", equalTo(message));
    }
    @Test
    @DisplayName("Логин негативная проверка")
    public void checkLoginNegative(){
        Response response = loginIncorrect();
        checkCode(response);
        checkMessage(response);
    }

    @After
    public void clearData(){
        deleteTestUser("diplomauser@praktikum.ru","1234");
    }
}
