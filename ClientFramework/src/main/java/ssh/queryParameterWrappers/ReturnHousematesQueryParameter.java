package ssh.queryParameterWrappers;

public class ReturnHousematesQueryParameter {
    // attributes
    private int houseId;

    // constructors
    public ReturnHousematesQueryParameter(){} // need a default constructor for object deserialisation
    public ReturnHousematesQueryParameter(int houseId){
        this.houseId = houseId;
    }

    // getters and setters
    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }
}
