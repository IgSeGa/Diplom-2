package site.nomoreparties.stellarburgers.tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.params.api.body.CreateUserBody;
import site.nomoreparties.stellarburgers.params.api.body.LoginUserBody;
import site.nomoreparties.stellarburgers.params.api.body.CrateOrderBody;
import site.nomoreparties.stellarburgers.params.api.responses.order.create.CreateOrder;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public static final String TESTMAIL = "diplomauser@praktikum.ru";
    public static final String TESTPASS = "123456";
    public static final String TESTNAME = "Vasya";
    public static final String SECONDMAIL = "diplomauser1@praktikum.ru";
    public static final String THIRDMAIL = "diplomauser2@praktikum.ru";
    public static final String SECONDNAME = "Vasyan";
    public static final String THIRDNAME = "Vasyanishe";
    public static final String [] INGREDS = {"61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71"};

    public void baseTestURL(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    public void createTestUser(String email, String password, String name){
        CreateUserBody params = new CreateUserBody(email, password, name);
        given().header("Content-type", "application/json").and().body(params).post("api/auth/register");
    }
    public String extractTestToken(String email, String password){
        LoginUserBody login = new LoginUserBody(email, password);
        given().header("Content-type", "application/json").and().body(login).post("api/auth/login")
                .then().assertThat().statusCode(200);
        String x = given().header("Content-type", "application/json").and().body(login).post("api/auth/login").then().extract().body().path("accessToken");
        StringBuilder sb = new StringBuilder(x);
        sb.delete(0,7);
        String token = sb.toString();
        return token;
    }
    public void deleteTestUser(String email, String password){
        String token = extractTestToken(email, password);
        given().auth().oauth2(token).delete("api/auth/user").then().assertThat().statusCode(202);
    }
    public Response createTestOrder(String[] ingreds, String token){
        CrateOrderBody order = new CrateOrderBody(ingreds);
        Response response = given().header("Content-type", "application/json").auth().oauth2(token).
                and().body(order).post("api/orders");
        return response;
    }
    public CreateOrder createTestOrderPojo(String[] ingreds, String token){
        CrateOrderBody order = new CrateOrderBody(ingreds);
        CreateOrder response = given().header("Content-type", "application/json").auth().oauth2(token).
                and().body(order).post("api/orders").as(CreateOrder.class);
        return response;
    }
}
