package ssh;

import ssh.entities.BasketItem;

import java.util.List;

public class ReturnedBasket{
    // attributes
    private Integer basketid;
    private List<BasketItem> basketItems;

    // constructor
    public ReturnedBasket() {} // needs default constructor for object serialisation
    public ReturnedBasket(Integer basketid, List<BasketItem> basketItems){
        this.basketid = basketid;
        this.basketItems = basketItems;
    }

    // methods
    public Integer getBasketid() {
        return basketid;
    }
    public void setBasketid(Integer basketid) {
        this.basketid = basketid;
    }
    public List<BasketItem> getBasketItems() {
        return basketItems;
    }
    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
}