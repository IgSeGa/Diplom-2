package site.nomoreparties.stellarburgers.params.orders.create;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import site.nomoreparties.stellarburgers.params.BaseTest;

@RunWith(Parameterized.class)
public class TestOrderIngreds extends BaseTest {
    private String[] ingreds;
    private String email = "diplomauser@praktikum.ru";
    private String password = "1234";
    private String burgername = "Бессмертный флюоресцентный бургер";
    private int code;
    private String message;

    public TestOrderIngreds(String[] ingreds, int code, String message){
        this.ingreds = ingreds;
        this.code = code;
        this.message = message;
    }
    @Before
    public void setUp(){
        baseTestURL();
        createTestUser(email, password, "Vasya");
    }
    @Parameterized.Parameters
    public static Object[][] getData() {
        String[] one = {"61c0c5a71d1f82001bdaaa6d"};
        String[] none = {""};
        String[] oneWrong = {"61c0c5a71d1f82001bdaaa6d", "88005553535"};
        String[] allWrong = {"s5sdfg4sdfg43we8f4", "sdzf4sdf6a51sdf56ae"};
        return new Object[][]{
                {one, "1234", 401, false, "email or password are incorrect"},
                {"diplomauser@praktikum.ru", "notapassword", 401, false, "email or password are incorrect"},
                {"", "1234", 401, false, "email or password are incorrect"},
                {"diplomauser@praktikum.ru", "",  401, false, "email or password are incorrect"},
                {"", "",  401, false, "email or password are incorrect"}
        };
    }
}
