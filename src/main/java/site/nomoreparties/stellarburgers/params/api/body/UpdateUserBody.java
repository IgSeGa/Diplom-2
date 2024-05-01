package site.nomoreparties.stellarburgers.params.api.body;

public class UpdateUserBody {
    private String email;
    private String name;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public UpdateUserBody(String email, String name){
        this.email = email;
        this.name = name;
    }
    public UpdateUserBody(){}
}
