# Supermarket APIs
---
## Outlining a Framework
Within the `SSH Cloud` project directory is a package called `supermarketAPIs` which is intended to contain all of the necessary methods to retrieve information about the items (and the various categories that they belong to) that can be purchased in the online group grocery order.

By containing these classes and methods in this package, we've abstracted this aspect of the system from the SSH Cloud functionality, and have thus created a framework which can be adapted depending on the nature of each supermarket API.

## How it's being prototyped
For the scope of this prototype however, the API functionality has been "simualted" by hard-coded data.

The sample supermarket API - called the `MoneyBurnerMarket` API loads up hard-coded test data from JSON files.

The data is read in from these JSON files by the `JsonUtilities` class in the `utilities` package in the SSH Cloud project, and uses the Jackson library to parse the JSON file contents to the correct database entity objects.

The class then has a method that uses methods in the `handlers` package, that runs the necessary commands on the MySQL Server's database to populate the test data into the database.