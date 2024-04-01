package site.nomoreparties.stellarburgers.params;
import io.restassured.RestAssured;
import site.nomoreparties.stellarburgers.params.body.CreateUserBody;
import site.nomoreparties.stellarburgers.params.body.LoginUserBody;

import static io.restassured.RestAssured.given;

public class BaseTest {

    public void baseTestURL(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    public void createTestUser(String email, String password, String name){
        CreateUserBody params = new CreateUserBody(email, password, name);
        given().header("Content-type", "application/json").and().body(params).post("api/auth/register");
    }
    public void loginTestUser(String email, String password) {
        LoginUserBody login = new LoginUserBody(email, password);
        given().header("Content-type", "application/json").and().body(login).post("api/auth/login")
                .then().assertThat().statusCode(200);
    }

    public String extractTestToken(String email, String password){
        LoginUserBody login = new LoginUserBody(email, password);
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
}
