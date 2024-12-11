package uk.co.xeiverse.ssh.networking.queryParameterWrappers;

public class ReturnBasketIdQueryParameter {
    // attributes
    private int houseId;
    private int storeId;

    // constructors
    public ReturnBasketIdQueryParameter(){} // need a default constructor for object deserialisation
    public ReturnBasketIdQueryParameter(int houseId, int storeId){
        this.houseId = houseId;
        this.storeId = storeId;
    }

    // getters and setters
    public int getHouseId() {
        return houseId;
    }
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
