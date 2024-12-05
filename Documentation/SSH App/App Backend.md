# SSH App Backend
---
## Introduction
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/40*

The SSH app has been split into frontend and backend development allowing developers to work on each part individually. All the network connectivity for the app is in the `ServerHelper` class and a separate IntelliJ project has been created to develop this separately to the app. The app will then utlise the methods in this class later when the systems are integrated.


## `ServerHelper` Class

The `ServerHelper` class will initially be developed so that the app can be tested without needing to connect to the SSH cloud. This means we will program in 'dummy' data initially to make sure the app works. This will then make integrating the network connectivity easier later.
