# SSH Cloud Unit Tests
## Contents
- [Handlers Tests](#handlers-tests)
  - [BasketHandler Tests](#baskethandler-tests)
  - [BasketItemHandler Tests](#basketitemhandler-tests)
  - [CategoryHandler Tests](#categoryhandler-tests)
  - [HouseHandler Tests](#househandler-tests)
  - [HousemateHandler Tests](#housematehandler-tests)
  - [ItemHandler Tests](#itemhandler-tests)
  - [StoreHandler Tests](#storehandler-tests)
- [QueryServicer Tests](#queryservicer-tests)
---
---
## Handlers Tests
### BasketHandler Tests   
#### `testCreateBasket`
```java
/**
 * @Test
 */
void testCreateBasket(){ ... }
```
**Description**: Verifies that a new basket can be created in the database. A new Basket is created with a predefined House and Store. The test ensures that the basket is saved correctly and that its associations with the House and Store match the expected values.

#### `testGetAllBaskets`
```java
/**
 * @Test
 */
void testGetAllBaskets(){ ... }
```
**Description**: Confirms that all baskets can be retrieved from the database. The test fetches all baskets and ensures that the previously created basket is included in the result. This validates that BasketHandler.getAll works correctly.

#### `testGetBasketById`
```java
/**
 * @Test
 */
void testGetBasketById(){ ... }
```
**Description**: Tests retrieval of a specific basket using its unique ID. The test validates that the returned basket matches the expected values.

#### `testGetBasketByAppInfo`
```java
/**
 * @Test
 */
void testGetBasketByAppInfo(){ ... }
```
**Description**: Ensures baskets can be retrieved based on application-specific information such as houseId and storeId. The test fetches baskets associated with the given House and Store and validates that only the expected basket is returned with correct details.

#### `testDeleteBasketById`
```java
/**
 * @Test
 */
void testDeleteBasketById(){ ... }
```
**Description**: Validates that a basket can be successfully deleted by its ID. The test first deletes the existing basket and then verifies that it is no longer retrievable. Finally, it recreates the basket to restore the test environment for subsequent tests.

#### `testCreateBasketByAppInfo`
```java
/**
 * @Test
 */
void testCreateBasketByAppInfo(){ ... }
```
**Description**: Confirms that a basket can be created using houseId and storeId. The test deletes the existing basket and then creates a new one using BasketHandler.createByAppInfo. It validates the correctness of the newly created basket's associations with the House and Store.

### BasketItemHandler Tests  
#### `testCreateBasketItem`
```java
/**
 * @Test
 */
void testCreateBasketItem(){ ... }
```
**Description**: Validates that a new BasketItem can be created in the database. The test creates a BasketItem with predefined references to Basket, Store, Item, and Housemate. It verifies that the BasketItem is saved successfully and its attributes, such as quantity and associations, match the expected values.

#### `testCreateBasketItemByInfo`
```java
/**
 * @Test
 */
void testCreateBasketItemByInfo(){ ... }
```
**Description**: Ensures that a BasketItem can be created using specific identifiers (basketId, storeId, itemId, housemateId) and an item quantity. The method constructs a BasketItem using these values and saves it to the database. The test verifies that the saved BasketItem has the correct attributes and associations, such as the proper basket, store, item, housemate, and quantity.

#### `testGetAllBasketItems`
```java
/**
 * @Test
 */
void testGetAllBasketItems(){ ... }
```
**Description**: Confirms that all BasketItem records can be retrieved from the database. The test ensures that the preloaded BasketItem is included in the results, validating the functionality of BasketItemHandler.getAll.

#### `testGetBasketItemById`
```java
/**
 * @Test
 */
void testGetBasketItemById(){ ... }
```
**Description**: Tests the retrieval of a specific BasketItem by its unique ID. The test verifies that the fetched BasketItem ID matches the expected value.

#### `testGetBasketItemByBasketId`
```java
/**
 * @Test
 */
void testGetBasketItemByBasketId(){ ... }
```
**Description**: Ensures that basket items can be retrieved based on their associated basket's ID. The test fetches basket items for the predefined basket and validates that the returned basket item matches the expected basket reference.

#### `testIncreaseItemQuantity`
```java
/**
 * @Test
 */
void testIncreaseItemQuantity(){ ... }
```
**Description**: Verifies that the quantity of an existing BasketItem can be increased. The test updates the item quantity and validates that the new quantity is correctly saved in the database

#### `testDecreaseItemQuantity`
```java
/**
 * @Test
 */
void testDecreaseItemQuantity(){ ... }
```
**Description**: Confirms that the quantity of an existing BasketItem can be decreased. The test updates the item quantity and validates that the updated value matches expectations.

#### `testDeleteBasketItemById`
```java
/**
 * @Test
 */
void testDeleteBasketItemById(){ ... }
```
**Description**: Validates that a BasketItem can be deleted from the database using its unique ID. The test deletes the BasketItem, confirms its removal, and recreates it to restore the testing environment.

### CategoryHandler Tests  
#### `testCreateCategory`
```java
/**
 * @Test
 */
void testCreateCategory(){ ... }
```
**Description**: Verifies the ability to create a new Category in the database. The test creates a category with a predefined store association and ensures that the saved Category has the correct name and store reference

#### `testGetAllCategories`
```java
/**
 * @Test
 */
void testGetAllCategories(){ ... }
```
**Description**: Confirms that all Category records can be retrieved from the database. The test ensures that the preloaded Category is included in the retrieved list, validating the functionality of CategoryHandler.getAll.

#### `testGetCategoryByStoreId`
```java
/**
 * @Test
 */
void testGetCategoryByStoreId(){ ... }
```
**Description**: Ensures that categories can be retrieved based on their associated store's ID. The test fetches categories for the predefined store and validates that the returned Category matches the expected name and store reference.

### HouseHandler Tests  
#### `testCreateHouse`
```java
/**
 * @Test
 */
void testCreateHouse(){ ... }
```
**Description**: Validates the creation of a new House record in the database. A new house with a specific address is created, and the test verifies that the saved house matches the expected address.

#### `testGetHouseById`
```java
/**
 * @Test
 */
void testGetHouseById(){ ... }
```
**Description**: Tests retrieval of a specific House by its unique ID. The test validates that the fetched House ID matches the expected value.

### HousemateHandler Tests  
#### `testCreateHousemate`
```java
/**
 * @Test
 */
void testCreateHousemate(){ ... }
```
**Description**: Validates the creation of a new Housemate in the database. A new housemate is created and linked to an existing House. The test confirms that the saved housemate matches the provided details, including their name, image, and associated house.

#### `testGetHousematesByHouse`
```java
/**
 * @Test
 */
void testGetHousematesByHouse(){ ... }
```
**Description**: Ensures that housemates can be retrieved based on their associated house's ID. The test fetches housemates for the predefined house and validates that the returned housemates matches the expected housemate and store references.

### ItemHandler Tests  
#### `testCreateItem`
```java
/**
 * @Test
 */
void testCreateItem(){ ... }
```
**Description**: Verifies the creation of a new Item in the database. A new item is associated with a predefined Store and Category. The test checks that the item is saved with correct details, including its name, offer price, and stock status.

#### `testGetAllItems`
```java
/**
 * @Test
 */
void testGetAllItems(){ ... }
```
**Description**: Ensures that all Item records can be retrieved from the database. The test confirms that the preloaded item is included in the result list, validating the functionality of ItemHandler.getAll.

#### `testGetItemByStoreId`
```java
/**
 * @Test
 */
void testGetItemByStoreId(){ ... }
```
**Description**: Ensures that items can be retrieved based on their associated store's ID. The test fetches items for the predefined store and validates that the returned items matches the expected name and store reference.

### StoreHandler Tests  
#### `testCreateStore`
```java
/**
 * @Test
 */
void testCreateStore(){ ... }
```
**Description**: Verifies the creation of a new Store in the database. A new store is created with a specific name and logo, and the test ensures that the store is saved correctly with matching details.

#### `testGetAllStores`
```java
/**
 * @Test
 */
void testGetAllStores(){ ... }
```
**Description**: Confirms that all Store records can be retrieved from the database. The test ensures that the preloaded store is included in the result list, validating the functionality of StoreHandler.getAll.

## QueryServicer Tests
#### `testReturnStores`
```java
/**
 * @Test
 */
void testReturnStores(){ ... }
```
**Description**: Verifies that QueryServicer.returnStores correctly retrieves the list of stores accessible to a housemate. The test ensures that the returned store name matches the expected value when queried with a valid house and housemate ID.


#### `testReturnCategories`
```java
/**
 * @Test
 */
void testReturnCategories(){ ... }
```
**Description**: Ensures that QueryServicer.returnCategories retrieves all categories for a given store. The test validates that the categories list is not null and contains at least one entry, ensuring proper database integration and query execution.


#### `testReturnBasketId`
```java
/**
 * @Test
 */
void testReturnBasketId(){ ... }
```
**Description**: Confirms that QueryServicer.returnBasketId retrieves the correct basket ID for a given house and store. The test validates that the returned basket matches the prepopulated basket's ID and that its item list is initially empty.


#### `testAddToBasket`
```java
/**
 * @Test
 */
void testAddToBasket(){ ... }
```
**Description**: Tests the functionality of QueryServicer.addToBasket by adding an item to a basket. The test verifies that the item is successfully added with the correct quantity and checks if the basket items list includes the added item.


#### `testRemoveFromBasket`
```java
/**
 * @Test
 */
void testRemoveFromBasket(){ ... }
```
**Description**: Validates the behavior of QueryServicer.removeFromBasket when reducing the quantity of an item in the basket. The test first adds an item to the basket, then removes part of its quantity, and confirms the updated quantity in the basket.


#### `testSubmitOrder`
```java
/**
 * @Test
 */
void testSubmitOrder(){ ... }
```
**Description**: Simulates an order submission using QueryServicer.submitOrder. The test ensures the method returns true, indicating successful submission of the order.
