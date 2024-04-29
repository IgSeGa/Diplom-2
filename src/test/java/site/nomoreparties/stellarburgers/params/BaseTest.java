package site.nomoreparties.stellarburgers.params;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.params.body.CreateUserBody;
import site.nomoreparties.stellarburgers.params.body.LoginUserBody;
import site.nomoreparties.stellarburgers.params.body.CrateOrderBody;
import site.nomoreparties.stellarburgers.params.responses.order.create.CreateOrder;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private String email = "diplomauser@praktikum.ru";
    private String password = "1234";
    private String name = "Vasya";
    private String newmail = "diplomauser1@praktikum.ru";
    private String newname = "Vasyan";
    private String [] ingreds = {"61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71"};

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public String[] getIngreds() {
        return ingreds;
    }

    public void setIngreds(String[] ingreds) {
        this.ingreds = ingreds;
    }

    public String getNewmail() {
        return newmail;
    }

    public void setNewmail(String newmail) {
        this.newmail = newmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    public Response createTestOrder(String[] ingreds, String token){
        CrateOrderBody order = new CrateOrderBody(ingreds);
        Response response = given().header("Content-type", "application/json").auth().oauth2(token).
                and().body(order).post("api/orders");
        return response;
    }
}
