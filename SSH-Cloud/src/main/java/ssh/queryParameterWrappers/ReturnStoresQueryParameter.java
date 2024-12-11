package ssh.queryParameterWrappers;

public class ReturnStoresQueryParameter {
    // attributes
    private int houseId;
    private int housemateId;

    // constructors
    public ReturnStoresQueryParameter(){} // need a default constructor for object deserialisation
    public ReturnStoresQueryParameter(int houseId, int housemateId){
        this.houseId = houseId;
        this.housemateId = housemateId;
    }

    // getters and setters
    public int getHouseId() {
        return houseId;
    }
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
    public int getHousemateId() {
        return housemateId;
    }
    public void setHousemateId(int housemateId) {
        this.housemateId = housemateId;
    }
}
