# SSH Cloud Database Query Design
## Contents
- [Overview](#overview)
- [Pseudocode](#pseudocode)
- [Formalised Function Prototypes](#formalised-function-prototypes)
- [Database Queries/Commands](#database-queriescommands)
---
---
## Overview 
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/6*

The following _high-level_ overview describes the identified dataflows required between SSH App and SSH Cloud:

### An initial handshake between SSH App and SSH Cloud when SSH App starts up:
- The app sends the `house_id` and `housemate_id` to SSH Cloud _(these values are hard-coded into the app for the scope of this prototype)_.
- The server sends back a list of available stores.
- The app chooses which store the order is for, and sends this over to SSH Cloud.
- SSH Cloud returns a list of categories and items for that store, a `basket_id`, and a list of any items that are in this basket _(this list may be empty)_.

### Add item to basket:
  - The app sends the `basket_id`, `store_id`, `item_id`, `housemate_id` and quantity of items to add to SSH Cloud.
  - SSH Cloud handles the addition of the item to the basket, and sends back acknowledgement of the insertion.

### Remove an item from the basket:
  - The app sends the `basket_id`, `item_id`, `housemate_id` and `quantity` of items to remove to SSH Cloud.
  - SSH Cloud handles the removal of items from the basket, and sends back acknowledgement of the removal.

### Submit basket for order:
  - The app sends the `basket_id` to SSH Cloud.
  - SSH Cloud handles order submission _(which will be simulated for the scope of this prototype)_.
  - SSH Cloud sends back confirmation of the order placement.

---
## Pseudocode
The following rough pseudocode describes the logic of the various methods that implement these dataflows:

```
func returnStores(house_id, housemate_id){
    // authentication step
    does house for supplied house_id exist?

    does housemate_id have a corresponding record with the house_id supplied? (meaning that they're in the same house)

    if above fails, return failure message, else: continue execution

    // return list of stores
    lookup all stores from database
    
    parse them to a list of java objects

    return the list of objects to the app
}
```

```
func returnCategories(store_id){
    lookup all categories in the database with the store_id supplied

    parse them into a list of java objects

    return the list of objects to the app
}
```

```
func returnBasketId(house_id, store_id){
    look for basket entry in the basket table of the database corresponding to the values provided

    if a basket exists, fetch the list of basket items, and return that list and the basket_id

    if the basket doesn't exist, make a new basket entry in the database, and return the basket_id
}
```

```
func addToBasket(basket_id, store_id, item_id, housemate_id, quantity){
    run checks on the quantity to check it's strictly greater than zero
    return error message if it's less than or equal to zero

    lookup whether item_id already has a record in the basket
    if it does{
        lookup who added the item
        if the housemate matches the housemate_id passed in{
            add quantity to the quantity already in the database
            return success message
        }
        else{
            add a new record, as this is a different housemate
            return success message
        }
    }
    else{
        add a new record
        return success message
    }
}
```

```
func removeFromBasket(basket_id, item_id, housemate_id, quantity){
    lookup whether item_id already has a record in the basket
    if it does{
        lookup who added the item
        if the housemate matches the housemate_id passed in{
            check whether the quantity supplied is greater than the number already in the database

            if it is, return error message, else: continue execution

            check whether the quantity supplied is equal to the number already in the database

            if it is, delete the record from the table and return success message, else: continue execution

            if execution has reached this point, update the database record with the reduced quantity (ie. quantity in database minus quantity supplied)

            return success message
        }
        else{
            return error message that housemate cannot remove other housemates' items
        }
    }
    else{
        return error message that item needs to be in basket to be removed
    }
}
```

```
func submitOrder(basket_id){
    return hardcoded approval acknowledgement of order
}
```

---
## Formalised Function Prototypes
The following section provides the formalised Java function prototypes for the previously outlined functions:

### `returnStores`
```java
/**
 * @param houseId
 * @param housemateId
 * @return List of Store objects on success, or null in the case of failure
 */
public static List<Store> returnStores(int houseId, int housemateId){ ... }
```
**Description**: "Authenticates" that a housemate has a valid record in the referenced house and returns a list of all supermarkets with which orders can be placed.

**Parameters**: 
- `int houseId` - for identifying the house associated with the group grocery order.
- `int housemateId` - for identifying the housemate signed in to the client accessing the SSH Cloud service.

**Return Value**: `List<Store>` - providing the list of `Store` objects that contains all necessary supermarket data for the client process. May be `null` in the event of a computation failure.

### `returnCategories`
```java
/**
 * @param storeId
 * @return List of Category objects on success, or null in the case of failure
 */
public static List<Category> returnCategories(int storeId){ ... }
```
**Description**: For a given supermarket, it returns all shopping item categories for that store.

**Parameters**:
- `int storeId` - for identifying the supermarket for which the categories should be drawn from.

**Return Value**: `List<Category>` - provides the list of `Category` objects that contains all necessary supermarket data for the client process. May be `null` in the event of a computation failure.

### `returnBasketId`
```java
/**
 * @param houseId
 * @param storeId
 * @return A ReturnedBasket object with an Integer for the basket_id and a List of BasketItem objects for the basket items. (This list may be empty).
 */
public static ReturnedBasket returnBasketId(int houseId, int storeId){ ... }
```
**Description**: For a given house and store, looks up whether a basket entry exists in the database, returning a basket_id and list of corresponding items in the basket if so, or creates a new basket if not.

**Parameters**:
- `int houseId` - for identifying the house that the order belongs to.
- `int storeId` - for identifying which supermarket the order is being placed with.

**Return Value**: `ReturnedBasket` - an object that encapsulates both the returned `basketId` and the list of returned `BasketItem`'s, where this list may be empty if it's a new basket.


### `addToBasket`
```java
/**
 * @param basketId
 * @param storeId
 * @param itemId
 * @param housemateId
 * @param quantity
 * @return boolean, where true reports success, and false reports failure of some form
 */
public static boolean addToBasket(int basketId, int storeId, int itemId, int housemateId, int quantity){ ... }
```
**Description**: Given necessary information, adds a quantity of an item from a supermarket to the relevant basket, so that it can be included in the order. The function requires that the quantity supplied be strictly greater than zero. The function will create a new record in the database in the case that a record already exists for the item, but was added by another housemate.

**Parameters**:
- `int basketId` - for identifying the basket being curated for order.
- `int storeId` - for identifying which supermarket the order is being placed with.
- `int itemId` - for identifying the underlying `Item` object that is to be added to the basket.
- `int housemateId` - for identifying which housemate added the item, so that the addition is listed separately to any (identical) items added by other housemates, as well as for tracking who is responsible for paying for the item at order processing.
- `int quantity` - for specifying how many of the item are being added in this operation. Note that this value, when supplied as an argument, must be strictly greater than zero.

**Return Value**: `boolean` - returns `true` on successful addition to basket, and `false` otherwise.

### `removeFromBasket`
```java
/**
 * @param basketId
 * @param itemId
 * @param housemateId
 * @param quantity
 * @return boolean, where true reports success, and false reports failure of some form
 */
public static boolean removeFromBasket(int basketId, int itemId, int housemateId, int quantity){ ... }
```
**Description**: Given the necessary information, removes a quantity of an item from a basket, so that some or all of them are no longer included in the order. Any quantity supplied that's greater than the number in the database will not generate an error - it will simply remove as many as it can. This function will not remove items added by other housemates.

**Parameters**:
- `int basketId` - for identifying the basket being curated for order.
- `int itemId` - for identifying the underlying `Item` object that is to be removed from the basket.
- `int housemateId` - for identifying which housemate is removing the item, so that units of the same item added by other housemates aren't removed from the basket.
- `int quantity` - for specifying how many of the item are being removed in this operation. Note that any quantity supplied that is greater than the quantity of the item already in the basket under the corresponding `housemate_id` will not generate an error, and will rather just remove as many of the item as it can (ie. there will be zero in the order) in this case.

**Return Value**: `boolean` - returns `true` on successful addition to basket, and `false` otherwise.


### `submitOrder`
```java
/**
 * @param basket_id
 * @return boolean, where true reports that the order has been placed successfully, and false reports that the order has not been placed successfully
 */
public static boolean submitOrder(int basket_id){ .. }
```
**Description**: Simulates the order submission process with the supermarket API, by returning hard-coded approval and acknowledgement of the order submission.

**Parameters**:
- `int basketId` - for identifying the basket being submitted for order.

**Return Value**: `boolean` - returns `true` on successful order placement, and `false` otherwise. _(Of course, in this prototype, order placement is always successful)._

---

## Database Queries/Commands
Based on the previous sections, the following queries and commands have been identified as being required to compose the larger, more compounded actions described above:
> Note that these are not formed in SQL or in a particularly well-formed syntax, due to the anticipation that Object/Relational Mapping will be used to implement the database, and by extension, the queries and commands that operate on it.

### Pertaining to the `Basket` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(Basket bakset)` | Required to create a basket record in the database from a `Basket` object. Notably used in populating the datbaase to an initial state from JSON files. | Yes |
| `void createByAppInfo(int houseId, int storeId)` | Required to create a basket record in the database from unpackaged values, notably when a new basket is created for a proposed order with a store. | Yes |
| `List<Basket> getAll()` | Requried to get a list of all baskets that exist in the database, notably for exporting the database to JSON files. | Yes |
| `Bakset getById(int id)` | Required for testing purposes, to see that the creation and deletion of baskets works correctly. | Yes |
| `List<Basket> getByAppInfo(int houseId, int storeId)` | Required for getting the group basket when a client application is initially loaded. | Yes |
| `void deleteById(int id)` | Required to remove an open basket when an order is successfully placed, as well as for unit testing the database actions. | Yes |

### Pertaining to `BasketItem` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(BasketItem basketItem)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `void createByInfo(int basketId, int storeId, int itemId, int housemateId, int itemQuantity)` | Required for servicing a client request to add an item to the group basket. | Yes |
| `List<BasketItem> getAll()`| Required for testing purposes, to see that items are added to and removed from group baskets correctly. | Yes |
| `BasketItem getById(int id)` | Required for testing purposes, to see that items are removed from group baskets correctly. | Yes |
| `List<BasketItem> getByBasketId(int basketId)` | Required for fetching all items in a group basket when requested to by client instances. | Yes |
| `void increaseItemQuantity(int basketItemId, int quantityToAdd)` | Required to add new items or increase the quantity of items already in group baskets. | Yes |
| `void decreaseItemQuantity(int basketItemId, int quantityToRemove)` | Required to remove items or decrease the quantity of items already in group baskets. | Yes |

### Pertaining to `Category` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(Category category)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `List<Category> getAll()` | Required for testing purposes, to verify that categories are correctly added as a part of database actions. | Yes |
| `List<Category> getByStoreId(int storeId)` | Required for fetching all categories of itmes made available by partner supermarkets when a client instance is spun-up. | Yes |

### Pertaining to `House` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(House house)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `House getById(int id)` | Required for fetching the address that an order corresponds to as a part of a group order. | Yes |

### Pertaining to `Housemate` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(Housemate housemate)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `List<Housemate> getByHouse(int houseId)` | Required for getting a list of all housemates when a client instance is spun up. The list of housemates in the corresponding house is required to show which users added which items to the group basket. | Yes |

### Pertaining to `Item` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(Item item)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `List<Item> getAll()` | Required for testing purposes, to verify that items are correctly added by other database actions. | Yes |
| `List<Item> getByStoreId(int storeId)` | Required for getting the list of all items to display for browsing/searching once a supermarket has been selected in a client instance. | Yes |

### Pertaining to `Store` entity:
| Query/Command Function Prototype | Explanation | Has a Unit Test? |
| -------------------------------- | ----------- | ------------ |
| `void create(Store store)` | Required for the initial population of database tables, when loading the test information from JSON files/strings. | Yes |
| `List<Store> getAll()` | Required for getting the list of all partner supermarkets from which the user can select from to work on a group order in a client instance. | Yes |