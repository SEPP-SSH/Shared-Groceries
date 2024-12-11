package ssh.queryParameterWrappers;

public class SubmitOrderQueryParameter {
    // attributes
    private int basketId;

    // constructors
    public SubmitOrderQueryParameter(){} // need a default constructor for object deserialisation
    public SubmitOrderQueryParameter(int basketId){
        this.basketId = basketId;
    }

    // getters and setters
    public int getBasketId() {
        return basketId;
    }
    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }
}
