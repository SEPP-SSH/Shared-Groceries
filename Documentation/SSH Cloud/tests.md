# Tests Documentation

## Handlers Tests
- **BasketHandler**: Tests CRUD operations and queries for baskets.
- **CategoryHandler**: Validates CRUD operations for categories.
- **HouseHandler**: Tests operations for house management.
- **HousemateHandler**: Ensures housemates are correctly handled.
- **ItemHandler**: Tests item-related database operations.
- **StoreHandler**: Verifies store creation and retrieval.
- **BasketItemHandler**: Covers item addition, quantity changes, and deletion in baskets.

## Query Servicer Tests
- **returnStores**: Ensures stores are fetched for valid housemates.
- **returnCategories**: Tests retrieval of categories for a given store.
- **returnBasketId**: Verifies basket retrieval or creation logic.
- **addToBasket**: Tests adding items to a basket.
- **removeFromBasket**: Ensures items can be removed from a basket.
- **submitOrder**: Simulates order submission.

## Integration Tests
- Simulates end-to-end interactions:
  - Adding items to a basket and retrieving them.
  - Removing items and verifying changes.
  - Submitting orders successfully.
  - Fetching stores and categories.

## Future Plans
- Automate tests using GitHub Actions for continuous integration.
