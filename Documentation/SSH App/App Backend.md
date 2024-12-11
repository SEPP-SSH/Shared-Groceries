# SSH App Backend
---
## Introduction
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/40*

The SSH app has been split into frontend and backend development allowing developers to work on each part individually. All the network connectivity for the app is in the `ServerHelper` class and a separate IntelliJ project has been created to develop this separately to the app. The app will then utlise the methods in this class later when the systems are integrated.


## `ServerHelper` Class

The `ServerHelper` class will initially be developed so that the app can be tested without needing to connect to the SSH cloud. This means we will program in 'dummy' data initially to make sure the app works. This will then make integrating the network connectivity easier later.

It will store the following attributes:
- `housemateId` - the ID of the housemate logged into the app (hardcoded for now).
- `houseId` - the ID of the house the current housemate is part of (this will also be hardcoded for now).
- `storesList` - the list of stores available to shop at through the app. This will be the first thing fetched from the server.
- `currentStoreId` - the ID of the store currently being browsed. This will be decided by the app - based on the list of stores returned by the server - it will then govern what data is fetched from the server for the next few attributes.
- `categoriesList` - the list of different grocery categories available from a given store.
- `itemsList` - list of all grocery items available from a given grocery store.
- `basketId` - the ID of the basket for the current house, from the currently selected supermarket.
- `basketItemsList` - the list of all the items currently in the basket.
- `housemateList` - the list of all the housemates in current house.

All of these attributes will be available for the app to fetch using getters and also set the values using setters. They will be populated by fetching the values from the server when the `ShopFragment` is launched.

