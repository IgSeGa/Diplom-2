package site.nomoreparties.stellarburgers.params.users.create;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.params.BaseTest;
import site.nomoreparties.stellarburgers.params.body.CreateUserBody;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestCreateNegative extends BaseTest {
    private String email;
    private String password;
    private String name;
    private int code;
    private boolean success;
    private String message;

    public TestCreateNegative(String email, String password, String name, int code, boolean success, String message){
        this.email = email;
        this.password = password;
        this.name = name;
        this.code = code;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"diplomauser@praktikum.ru", "1234", "Vasya", 403, false, "User already exists"},
                {"", "1234", "Vasya", 403, false, "Email, password and name are required fields"},
                {"diplomauser@praktikum.ru", "", "Vasya", 403, false, "Email, password and name are required fields"},
                {"diplomauser@praktikum.ru", "1234", "", 403, false, "Email, password and name are required fields"}
        };
    }

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(getEmail(), getPassword(), getName());
    }

    @Step("Отправка запроса")
    public Response createUser(){
        CreateUserBody params = new CreateUserBody(email, password, name);
        Response response = given().header("Content-type", "application/json").and().body(params).post("api/auth/register");
        return response;
    }

    @Step("Проверка кода")
    public void checkCode(Response response){
        response.then().statusCode(code);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(success));
    }

    @Step("Проверка сообщения")
    public void checkMessage(Response response){
        response.then().assertThat().body("message", equalTo(message));
    }

    @Test
    @DisplayName("Создание пользователя негатив")
    public void createUserNegative(){
        Response response = createUser();
        checkMessage(response);
        checkCode(response);
        checkSuccess(response);
    }

    @After
    public void clearData(){
        deleteTestUser(getEmail(), getPassword());
    }
}
