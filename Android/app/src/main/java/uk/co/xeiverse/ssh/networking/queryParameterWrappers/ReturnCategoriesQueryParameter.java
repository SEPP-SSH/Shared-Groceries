package uk.co.xeiverse.ssh.networking.queryParameterWrappers;

public class ReturnCategoriesQueryParameter {
    // attributes
    private int storeId;

    // constructors
    public ReturnCategoriesQueryParameter(){} // need a default constructor for object deserialisation
    public ReturnCategoriesQueryParameter(int storeId){
        this.storeId = storeId;
    }

    // getters and setters
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
