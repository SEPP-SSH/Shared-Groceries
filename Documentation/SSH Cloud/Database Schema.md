# SSH Cloud Database Schema
---
## The Schema
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/5*

From the selected Engineering Design Review (EDR), a database was detailed for storing data for the group grocery shopping feature.

The schema for this database has been constructed, as shown below:

![SSH Cloud Database Schema](./Attachments/SSH%20Cloud%20Database%20Design.png)

## Changes made from the EDR
As of Milestone 2:
- The database schema has been significantly altered in its structure to remove composite primary keys, instead replacing them with a single-attribute (i.e. non-composite) primary key, due to the difficulties the composite keys were causing during the implementation process. Though this is _structurally_ a significant change to the database design, it _doesn't_ greatly affect the data that the database stores, or how it behaves, where this change mainly provides simplifications in the implementation process, without affecting the functionality of the SSH Cloud product.
- An attribute has been added to the `Store` table - namely `store_logo`, which allows client UI applications to show a logo for stores, rather than *just* their name.

---
---
## Entity Descriptions
See below for the datatype specification of each attribute in each entity, and (where unclear) a description explaining the role of that attribute in the database.

### `Basket` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `basket_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `store_id` | Integer | _Foreign Key_ | - |
| `house_id` | Integer | _Foreign Key_ | - |

### `BasketItem` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `basket_item_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `basket_id` | Integer | _Foreign Key_ | - |
| `store_id` | Integer | _Foreign Key_ | - |
| `item_id` | Integer | _Foreign Key_ | - |
| `housemate_id` | Integer | _Foreign Key_ | - |
| `item_quantity` | Integer | - | - |

### `Category` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `category_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `store_id` | Integer | _Foreign Key_ | - |
| `category_name` | String | - | - |

### `House` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `house_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `house_address` | String | - | A physical property address for where the group order should be delivered to. |

### `Housemate` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `housemate_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `housemate_forename` | String | - | The housemate's first name - i.e. "Nathan" of "Nathan Drake". |
| `housemate_surname` | String | - | The housemate's last name - i.e. "Drake" of "Nathan Drake". |
| `housemate_img` | String | - | A URL for the profile image of the housemate. |
| `house_id` | Integer | _Foreign Key_ | - |

### `Item` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `item_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `store_id` | Integer | _Foreign Key_ | - |
| `item_name` | String | - | - |
| `item_img` | String | - | A URL for the supermarket's image of the grocery item. |
| `item_base_price` | Double | - | This is the full normal retail price of the grocery item. |
| `item_on_offer_price` | Double | - | This is the "on-offer" price of the grocery item. If the item is _not_ on offer, then this value will be the same as the `item_base_price`. |
| `item_is_in_stock` | Boolean | - | This is `True` if there is stock of the item available for purchase, and `False` otherwise. | 
| `category_id` | Integer | _Foreign Key_ | - |

### `Store` Entity
| Attribute | Type | Key Annotations | Description |
| --------- | ---- | --------------- | ----------- |
| `store_id` | Integer (auto-increment, non-repeating) | **Primary Key** | - |
| `store_name` | String | - | - |
| `store_logo` | String | - | A URL for the image of the logo of the partner supermarket. |