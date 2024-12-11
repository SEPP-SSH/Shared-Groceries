package uk.co.xeiverse.ssh.networking.queryParameterWrappers;

public class RemoveFromBasketQueryParameter {
    // attributes
    private int basketId;
    private int itemId;
    private int housemateId;
    private int quantity;

    // constructors
    public RemoveFromBasketQueryParameter(){} // need a default constructor for object deserialisation
    public RemoveFromBasketQueryParameter(int basketId, int itemId, int housemateId, int quantity){
        this.basketId = basketId;
        this.itemId = itemId;
        this.housemateId = housemateId;
        this.quantity = quantity;
    }

    // getters and setters
    public int getBasketId() {
        return basketId;
    }
    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getHousemateId() {
        return housemateId;
    }
    public void setHousemateId(int housemateId) {
        this.housemateId = housemateId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
