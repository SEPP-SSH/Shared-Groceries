package ssh;

import ssh.entities.BasketItem;
import ssh.entities.Category;
import ssh.entities.Store;
import java.util.List;
import java.util.Map;

public class QueryServicer {
    public static List<Store> returnStores(int houseId, int housemateId){
        // todo
    }

    public static List<Category> returnCategories(int storeId){
        // todo
    }

    // explanation of return type: the key is the basket_id, and the value is the list of BasketItem objects
    public static Map<Integer, List<BasketItem>> returnBasketId(int houseId, int housemateId, int storeId){
        // todo
    }

    public static boolean addToBasket(int basketId, int itemId, int housemateId, int quantity){
        // todo
    }

    public static boolean removeFromBasket(int basketId, int itemId, int housemateId, int quantity){
        // todo
    }

    public static boolean submidOrder(int basket_id){
        // todo
    }
}
