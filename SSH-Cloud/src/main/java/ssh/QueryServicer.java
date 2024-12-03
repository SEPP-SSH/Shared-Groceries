package ssh;

import org.hibernate.SessionFactory;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.utilities.HibernateUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryServicer {
    public static List<Store> returnStores(int houseId, int housemateId){
        // returns null on failure

        // does house for supplied houseId exist?
        House matchedHouse;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            System.out.println("got session factory");
            matchedHouse = new HouseHandler(sessionFactory).getById(houseId);
        }
        catch (Exception e){
            // no house for house id
            return null;
        }

        // does housemateId have a record in the corresponding house?
        List<Housemate> matchedHousemates;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            System.out.println("got second session factory");
            matchedHousemates = new HousemateHandler(sessionFactory).getByHouse(matchedHouse.getHouseId());
            System.out.println("got back from matched housemates");
        }
        catch (Exception e){
            // error
            return null;
        }

        boolean housemateFound = false;
        System.out.println("before iterating housemates");
        for (Housemate housemate : matchedHousemates){
            if (housemate.getHousemateId() == housemateId){
                housemateFound = true;
                break;
            }
        }
        if (!housemateFound){
            // housemate not found
            return null;
        }

        // lookup all stores from database
        System.out.println("got to lookup of stores");
        List<Store> allStores;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            allStores = new StoreHandler(sessionFactory).getAll();
        }
        catch (Exception e){
            // error of some form
            return null;
        }

        // return the list of objects to the app
        return allStores;
    }

    public static List<Category> returnCategories(int storeId){
        // lookup all categories in the database with the storeId
        List<Category> allCategories;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            allCategories = new CategoryHandler(sessionFactory).getByStoreId(storeId);
            return allCategories;
        }
        catch (Exception e){
            // error
            return null;
        }
    }

    // explanation of return type: the key is the basket_id, and the value is the list of BasketItem objects
    public static Map<Integer, List<BasketItem>> returnBasketId(int houseId, int storeId){
        // look for basket entry in the basket table of the database corresponding to the values provided
        List<Basket> matchedBaskets =  new ArrayList<>(); // need to init for length check
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedBaskets = new BasketHandler(sessionFactory).getByAppInfo(houseId, storeId);
            System.out.println("got matched baskets");
        }
        catch (Exception e){
            // error
            return null;
        }

        if (matchedBaskets.size() > 1){
            // shouldn't be more than one basket
            return null;
        }
        else if (matchedBaskets.size() == 1){
            // get list of basket items
            List<BasketItem> basketItems;
            try{
                System.out.println("in matched baskets size  = 1");
                // get SessionFactory object
                SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
                basketItems = new BasketItemHandler(sessionFactory).getByBasketId(matchedBaskets.getFirst().getBasketId());
                System.out.println("got basket items");

                Map<Integer, List<BasketItem>> returnVal = new HashMap<>();
                returnVal.put(matchedBaskets.getFirst().getBasketId(), basketItems);
                return returnVal;
            }
            catch (Exception e){
                // error
                return null;
            }
        }
        else {
            // need to make a basket as one doesn't exist

            // get SessionFactory object
            try{
                SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
                BasketHandler basketHandler = new BasketHandler(sessionFactory);
                basketHandler.createByAppInfo(houseId, storeId);

                Basket newlyCreatedBasket = basketHandler.getByAppInfo(houseId, storeId).getFirst();

                Map<Integer, List<BasketItem>> returnVal = new HashMap<>();
                returnVal.put(newlyCreatedBasket.getBasketId(), new ArrayList<>()); // returns empty list as there's no items in the basket, as the basket has just been made
                return returnVal;
            }
            catch (Exception e){
                return null;
            }
        }
    }

    public static boolean addToBasket(int basketId, int storeId, int itemId, int housemateId, int quantity){
        // check quantity is > 0
        if (quantity <= 0){
            return false;
        }

        // lookup whether itemId already has a record in the basket
        List<BasketItem> matchedItems = new ArrayList<>(); // init for length check
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            System.out.println("about to get matched items");
            matchedItems = new BasketItemHandler(sessionFactory).getByBasketId(basketId);
            System.out.println("got back from matched items");

            // does a record already exist with this itemId that matches the housemateId?
            for (BasketItem matchedItem : matchedItems){
                if (matchedItem.getHousemate().getHousemateId() == housemateId){
                    // we've found a record that can be updated, so update it
                    System.out.println("found an updatable record");
                    new BasketItemHandler(sessionFactory).increaseItemQuantity(basketId, quantity);
                    return true;
                }
            }
            // if at this point of execution, we didn't find a record we could update, so need to add one
            System.out.println("need to add a record");
            new BasketItemHandler(sessionFactory).createByInfo(basketId, storeId, itemId, housemateId, quantity);
            return true;
        }
        catch (Exception e){
            // error
            return false;
        }
    }

    public static boolean removeFromBasket(int basketId, int itemId, int housemateId, int quantity){
        // lookup whether itemId already has a record in the basket
        List<BasketItem> matchedItems = new ArrayList<>(); // init for length check
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedItems = new BasketItemHandler(sessionFactory).getByBasketId(basketId);

            // does a record already exist with this itemId that matches the housemateId?
            for (BasketItem matchedItem : matchedItems){
                if (matchedItem.getHousemate().getHousemateId() == housemateId && matchedItem.getItem().getItemId() == itemId){
                    // we've found a record that MAY be able to be updated, so check if we can
                    int quantityInDatabase = matchedItem.getItemQuantity();

                    if (quantity < quantityInDatabase){
                        // update record, as there will still be some left in the database after query
                        new BasketItemHandler(sessionFactory).decreaseItemQuantity(matchedItem.getBasketItemId(), quantity);
                        return true;
                    }
                    else{
                        // need to delete record
                        new BasketItemHandler(sessionFactory).deleteById(matchedItem.getBasketItemId());
                        return true;
                    }
                }
                break; // stop after first alteration, as there should only be one record in database
            }
            return false; // only reaches here if no removal could take place (ie. cannot delete non-existent items, or other housemates' items)
        }
        catch (Exception e){
            // error
            return false;
        }
    }

    public static boolean submitOrder(int basket_id){

        // SIMULATES API CALL - WOULD NEED TO IMPLEMENT FOR FULL DEVELOPMENT

        return true; // ie. order success
    }
}