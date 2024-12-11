package ssh.queryParameterWrappers;

public class AddToBasketQueryParameter {
    // attributes
    private int basketId;
    private int storeId;
    private int itemId;
    private int housemateId;
    private int quantity;

    // constructors
    public AddToBasketQueryParameter(){} // need a default constructor for object deserialisation
    public AddToBasketQueryParameter(int basketId, int storeId, int itemId, int housemateId, int quantity){
        this.basketId = basketId;
        this.storeId = storeId;
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
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
