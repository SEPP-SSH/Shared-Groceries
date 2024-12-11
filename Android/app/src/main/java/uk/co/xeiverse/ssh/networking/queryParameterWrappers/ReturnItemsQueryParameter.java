package uk.co.xeiverse.ssh.networking.queryParameterWrappers;

public class ReturnItemsQueryParameter {
    // attributes
    private int storeId;

    // constructors
    public ReturnItemsQueryParameter(){} // need a default constructor for object deserialisation
    public ReturnItemsQueryParameter(int storeId){
        this.storeId = storeId;
    }

    // getters and setters
    public int getstoreId() {
        return storeId;
    }

    public void setstoreId(int storeId) {
        this.storeId = storeId;
    }
}
