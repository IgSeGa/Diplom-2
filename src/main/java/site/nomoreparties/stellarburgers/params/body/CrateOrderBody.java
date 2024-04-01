package site.nomoreparties.stellarburgers.params.body;

public class CrateOrderBody {
    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    private String [] ingredients;
    public CrateOrderBody(String[] ingredients){
        this.ingredients = ingredients;
    }
    public CrateOrderBody(){}
}
