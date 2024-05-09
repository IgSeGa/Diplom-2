package site.nomoreparties.stellarburgers.tests.orders.get;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.tests.BaseTest;
import site.nomoreparties.stellarburgers.params.api.responses.order.create.CreateOrder;
import site.nomoreparties.stellarburgers.params.api.responses.order.get.GetOrder;
import static io.restassured.RestAssured.given;

public class TestGetOrderAuth extends BaseTest{

    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(TESTMAIL, TESTPASS, TESTNAME);
    }
    @Step("Запрос на получение заказа")
    public Response makeRequest(){
        String token = extractTestToken(TESTMAIL, TESTPASS);
        Response response = given().auth().oauth2(token).get("api/orders");
        return response;
    }
    @Step("Запрос на получение заказа с ответов в объекте")
    public GetOrder makeRequestPojo(){
        GetOrder order = given().auth().oauth2(extractTestToken(TESTMAIL, TESTPASS)).get("api/orders").as(GetOrder.class);
        return order;
    }
    @Step("Проверка статуса")
    public void checkStatus(Response response){
        response.then().statusCode(200);
    }
    @Step("Проверка успеха")
    public void checkSuccess(Response response){
        response.then().assertThat().body("success", equalTo(true));
    }
    @Step("Проверка всех заказов")
    public void checkTotal(Response response){
        response.then().assertThat().body("total", notNullValue());
    }
    @Step("Проверка всех заказов сегодня")
    public void checkTotalToday(Response response){
        response.then().assertThat().body("totalToday", notNullValue());
    }
    @Step("Проверка номера заказа")
    public void checkOrderNumber(GetOrder order, CreateOrder etalon){
        Assert.assertEquals(order.getOrders().get(0).getNumber(), etalon.getOrder().getNumber());
    }
    @Step("Проверка id заказа")
    public void checkOrderId(GetOrder order, CreateOrder etalon){
        Assert.assertEquals(order.getOrders().get(0).getId(), etalon.getOrder().getId());
    }
    @Step("Проверка названия заказа")
    public void checkOrderName(GetOrder order, CreateOrder etalon){
        Assert.assertEquals(order.getOrders().get(0).getName(), etalon.getOrder().getName());
    }


    @Test
    @DisplayName("Проверка создания заказа с авторизацией")
    public void checkFields(){
        Response response = makeRequest();
        checkStatus(response);
        checkSuccess(response);
        checkTotal(response);
        checkTotalToday(response);
        CreateOrder etalon = createTestOrderPojo(INGREDS, extractTestToken(TESTMAIL, TESTPASS));
        GetOrder order = makeRequestPojo();
        checkOrderNumber(order,etalon);
        checkOrderId(order,etalon);
        checkOrderName(order,etalon);
    }

    @After
    public void cleanUp(){
        deleteTestUser(TESTMAIL, TESTPASS);
    }
}
