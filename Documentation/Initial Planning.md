# Initial Planning and Organisation
---
## Choosing an EDR to prototype
> *This section formally documents the following GitHub issue: https://github.com/SEPP-SSH/Shared-Groceries/issues/35*

We selected Harvey's Engineering Design Review (EDR) to prototype *(See the .pdf in this directory - i.e. [Selected EDR](/Selected%20EDR.pdf))*.

After reviewing the EDR, we decided to make the following simplifications, alterations and omissions for the prototype:
-  Populating only two categories of items in the item selection view. This would include _not_ populating the "Offers" section. The reason behind this is that implementing support for a limited selection of items overcomes the largest challenge in the implementation, and adding further layers of features on top of this doesn't add much in the way of reducing the risk of approving the project for full development.
- Not implementing the search functionality. For similar reasons as outlined above, as well as adding a significant burden to the SSH App front-end developer.
- Simulate/hard-code the items added to the group basket by other people in the shared house. i.e. The prototype being built should show how the system works from the perspective of one user - no multi-user functionality will be implemented. The rationale behind this is that it would require a lot of development time, and potentially cause confusion when demonstrating the prototype as to how the feature works, where it may be simpler to show only one part of the system moving at once, so to speak.
- Not developing the SSH Console Table implementation of the feature. This is because the EDR proposes that the SSH App and SSH Console Table use the same piece of software, only with layout alterations for the Console Table. Therefore, the viability of the system can be nearly entirely demonstrated by prototyping just one version.
- Not implementing the "order deadline" condition for placing an order. In the EDR, it's proposed that the order submission process is initiated at the order deadline. As this is a prototype, with no real access to supermarket APIs, we propose that for simplicity we use manual order submission.
- Omitting payment processing. Due to the complex and specialised nature of (responsibly) processing payments, we propose we simply simulate payment approval when an order acknowledgement is returned from the relevant server, rather than actually implementing any form of intelligible payment processing logic.
- Refactoring the milestones. We propose refactoring milestones into the following: Initial planning, Developing system foundations, Implementing network connectivity, Integrating the systems. The reason behind this change is to account for the much smaller number of people in the development team than was drawn upon in the selected EDR.
- Refactoring the teams. We propose that we initially split into two teams - one to work on SSH App, and another to work on SSH Cloud. When reaching Milestone 4: Integration, we propose the teams work in a less segmented fashion, on each others systems, to properly integrate them with each other. The reason for this change follows similar reasons as outlined above, with respect to development team size.

---
---
## Choosing a tech stack
### For our products:
#### SSH Cloud
For SSH Cloud, we propose implementing a networked server in Java, that creates, queries, and maintains a MySQL database to store data pertaining to/necessary for implementing the group grocery order feature.

The reason we chose Java and MySQL is due to the developers' familiarity with the technologies, and the excellent documentation for both platforms, which we hope will reduce the time taken to produce the prototype.

We also propose using Javalin to implement networking between the SSH Cloud and SSH App products.

Instead of writing explicit SQL queries for the database, we will use Hibernate and the Javax persistence API to utilise Object/Relational Mapping (ORM) to store the data in the database. By doing this, we reduce the development time required to achieve the parsing of database-writable data to java objects, and vice versa.

We propose using JUnit to implement unit testing on the individual components of the database actions, as well as utilising the testing platform to run integration tests that check the soundness of the client-server networking request handling, database actions, and the server-to-server network actions, in aggregate.

We propose using the Jackson framework for parsing java objects from JSON files, and vice versa, making it easy to transmit parameters and return values over the network via Javalin, as well as for loading up the initial state of the database server from a cold-boot.

We also propose using Lombok for processing annotations in Java classes that represent database entities.

Further, we propose containerising the various systems of the SSH Cloud facet using Docker containers, and orchestrating them using docker-compose, so that the code is portable, results are reproducible, and we can easily integrate a continuous integration workflow using GitHub Actions.

#### SSH App
We are proposing to use the Android Studio IDE to create the SSH App using Java as the language, as well as XML for the layouts.

We chose this as the developers are all familiar with programming in Java, as well as having used Android Studio in the past. This method also allows the app to be easily and quickly deployed to either physical devices or emulators, simplifying testing. The base project generated by the IDE will also mean that producing the prototype will be quicker and this is further aided by the thorough documentation available for developing for Android.

We will be using Gradle for our build system as this is the default and most common build system for Android Studio projects and will therefore simplify development.

---
### For our development process:
#### Version Control
For version control, we propose using git, due to its ubiquitous usage in the rest of the industry, as well as our own familiarity with it, rendering it the most effective tool in for our use case, in our current situation.

#### Project Hosting and Management
For hosting the git repository, we propose using GitHub, due to:
- git support.
- GitHub Actions, allowing us to implement a continuous integration and deployment workflow.
- issues tracking and Kanban board issue management, allowing us to easily coordinate development milestones in a well-documented and collaborative manner.

---
---
