# SSH Cloud Database Schema
---
## The Schema
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/5*

From the selected Engineering Design Review (EDR), a database was detailed for storing data for the group grocery shopping feature.

The schema for this database has been constructed, as shown below:

![SSH Cloud Database Schema](./Attachments/SSH%20Cloud%20Database%20Design.png)

## Changes made from the EDR
As of Milestone 1:
- An error has been corrected in the `BasketApproval` table, where in the EDR, both attributes of the composite primary key erroneously ommitted foreign key declarations.
