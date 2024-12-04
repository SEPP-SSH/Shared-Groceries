package ssh;

import org.hibernate.SessionFactory;
import ssh.entities.*;
import ssh.handlers.*;
import ssh.queryParameterWrappers.*;
import ssh.utilities.HibernateUtility;

import java.util.*;


public class QueryServicer {

    /**
     * "Authenticates" that a housemate has a valid record in the referenced house and returns a list of all supermarkets with which orders can be placed.
     * @param houseId
     * @param housemateId
     * @return List of Store objects on success, or null in the case of failure
     */
    public static List<Store> returnStores(int houseId, int housemateId){
        // returns a list of Store objects if successful, or null on failure

        // does house for supplied houseId exist?
        House matchedHouse;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedHouse = new HouseHandler(sessionFactory).getById(houseId);
        }
        catch (Exception e){
            // error
            return null;
        }

        // does housemateId have a record in the corresponding house?
        List<Housemate> matchedHousemates;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedHousemates = new HousemateHandler(sessionFactory).getByHouse(matchedHouse.getHouseId());
        }
        catch (Exception e){
            // error
            return null;
        }

        boolean housemateFound = false;
        for (Housemate housemate : matchedHousemates){
            if (housemate.getHousemateId() == housemateId){
                housemateFound = true;
                break; // don't need to keep going after found housemate
            }
        }
        if (!housemateFound){
            // housemate not found
            return null;
        }

        // lookup all stores from database
        List<Store> allStores;
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            allStores = new StoreHandler(sessionFactory).getAll();
            return allStores;
        }
        catch (Exception e){
            // error of some form
            return null;
        }
    }
    public static List<Store> returnStores(ReturnStoresQueryParameter param){
        return returnStores(param.getHouseId(), param.getHousemateId());
    }

    /**
     * For a given supermarket, it returns all shopping item categories for that store.
     * @param storeId
     * @return List of Category objects on success, or null in the case of failure
     */
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
    public static List<Category> returnCategories(ReturnCategoriesQueryParameter param){
        return returnCategories(param.getStoreId());
    }

    /**
     * Queries and returns all housemates associated with a given house id
     * @param houseId
     * @return List of Housemate objects (this may be empty) in the case of success, or null in the case of computation failure
     */
    public static List<Housemate> returnHousemates(int houseId){
        // lookup all housemates in the database belonging to the indicated house
        List<Housemate> matchedHousemates = new ArrayList<>(); // init for return, in the case there are no housemates in the house
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedHousemates = new HousemateHandler(sessionFactory).getByHouse(houseId);
            return matchedHousemates;
        }
        catch (Exception e){
            // error
            return null;
        }
    }
    public static List<Housemate> returnHousemates(ReturnHousematesQueryParameter param){
        return returnHousemates(param.getHouseId());
    }

    /**
     * For a given house and store, looks up whether a basket entry exists in the database, returning a basket_id and list of corresponding items in the basket if so, or creates a new basket if not.
     * @param houseId
     * @param storeId
     * @return A Map object with an Integer for the basket_id and a List of BasketItem objects for the basket items. (This list may be empty).
     */
    public static Map<Integer, List<BasketItem>> returnBasketId(int houseId, int storeId){
        // look for basket entry in the basket table of the database corresponding to the values provided
        List<Basket> matchedBaskets =  new ArrayList<>(); // need to init for length check
        try{
            // get SessionFactory object
            SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
            matchedBaskets = new BasketHandler(sessionFactory).getByAppInfo(houseId, storeId);
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
                // get SessionFactory object
                SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
                basketItems = new BasketItemHandler(sessionFactory).getByBasketId(matchedBaskets.getFirst().getBasketId());

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
    public static Map<Integer, List<BasketItem>> returnBasketId(ReturnBasketIdQueryParameter param){
        return returnBasketId(param.getHouseId(), param.getStoreId());
    }
                                                                /**
     * Given necessary information, adds a quantity of an item from a supermarket to the relevant basket, so that it can be included in the order.
     * The function requires that the quantity supplied be strictly greater than zero.
     * The function will create a new record in the database in the case that a record already exists for the item, but was added by another housemate.
     * @param basketId
     * @param storeId
     * @param itemId
     * @param housemateId
     * @param quantity
     * @return boolean, where true reports success, and false reports failure of some form
     */
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
            matchedItems = new BasketItemHandler(sessionFactory).getByBasketId(basketId);

            // does a record already exist with this itemId that matches the housemateId?
            for (BasketItem matchedItem : matchedItems){
                if (matchedItem.getHousemate().getHousemateId() == housemateId){
                    // we've found a record that can be updated, so update it
                    new BasketItemHandler(sessionFactory).increaseItemQuantity(basketId, quantity);
                    return true;
                }
            }
            // if at this point of execution, we didn't find a record we could update, so need to add one
            new BasketItemHandler(sessionFactory).createByInfo(basketId, storeId, itemId, housemateId, quantity);
            return true;
        }
        catch (Exception e){
            // error
            return false;
        }
    }
    public static boolean addToBasket(AddToBasketQueryParameter param){
        return addToBasket(param.getBasketId(), param.getStoreId(), param.getItemId(), param.getHousemateId(), param.getQuantity());
    }

    /**
     * Given the necessary information, removes a quantity of an item from a basket, so that some or all of them are no longer included in the order.
     * Any quantity supplied that's greater than the number in the database will not generate an error - it will simply remove as many as it can.
     * This function will not remove items added by other housemates.
     * @param basketId
     * @param itemId
     * @param housemateId
     * @param quantity
     * @return boolean, where true reports success, and false reports failure of some form
     */
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
    public static boolean removeFromBasket(RemoveFromBasketQueryParameter param){
        return removeFromBasket(param.getBasketId(), param.getItemId(), param.getHousemateId(), param.getQuantity());
    }

    /**
     * Simulates the order submission process with the supermarket API, by returning hard-coded approval and acknowledgement of the order submission.
     * @param basket_id
     * @return boolean, where true reports that the order has been placed successfully, and false reports that the order has not been placed successfully
     */
    public static boolean submitOrder(int basket_id){

        // SIMULATES API CALL - WOULD NEED TO IMPLEMENT FOR FULL DEVELOPMENT

        return true; // ie. order success
    }
    public static boolean submitOrder(SubmitOrderQueryParameter param){
        return submitOrder(param.getBasketId());
    }


    // nested classes

}