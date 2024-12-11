# End of Milestone Reports
---
This document encompasses reviews of the work done at each milestone, and reflects on how well it implements the specification in the [selected EDR](Selected%20EDR.pdf), and what steps need to be taken in subsequent milestones.

---

## Report at end of Milestone 2
For the SSH App, the prototype implements the two main views required for the group grocery shopping feature, as outlined in the selected EDR. With respect to the item browsing and selection view, this has been correctly implemented, as per the EDR’s specification. With respect to the basket view, the breakdown tab has been correctly implemented. However, the items tab has not been implemented, and has been omitted in error. However, on reflection, adding this in at this stage would not greatly reduce the risk of approving the group grocery shopping feature for full development, because - as the EDR outlines - the items tab draws on the exact same data as the breakdown tab, and so by having already developed the items tab, we’ve demonstrated that the development of the feature is viable. The total cost of the current user's items is shown at the bottom of the basket view, as well as the total cost of the order. Whilst this extra view should not be developed at this stage, this change should be correctly added to the relevant documentation.

The prototype implements the database schema and necessary logic for SSH Cloud correctly, as specified in the EDR.

During the next milestone, we will develop the app so that all of the functionality works without the connection to the server, hardcoding 'dummy' data into the `ServerHelper` class and testing data flow through the app. This will then make adding the network integration in Milestone 4 much easier.

## Report at end of Milestone 3
SSH App and SSH Cloud networking routines correctly implement the EDR’s specification for data synchronisation whenever changes are made in the app. As for synchronising when changes are made in the other direction (I.e. from cloud to app), the networking framework allows for the relevant listeners and handlers to be added easily, where the supporting foundations for this are already in place. (The reason this direction of dataflow hasn’t been implemented is due to the choice to simulate the supermarket APIs for the purposes of this prototype). The app is now fully functional without connecting to the server and has been tested using hardcoded 'dummy' data.

With regards to the SSH App, for milestone 4, we will be adding the networking `Client` class created by the backend team to fully-integrate with the SSH Cloud.

## End of Milestone 4
The prototype has not implemented the payments processing system or the basket approval mechanism. Though these are well-specified in the EDR, the team agreed to place them out of scope at the start of the development of the prototype. 
Similarly, strong encryption for networking and database requests has been omitted from the prototype.
We should add extra clarification to our documentation for the rationale behind these decisions, and the exact nature of which features we didn’t prototype.

Within the SSH App we decided to use the same Java classes as developed by the SSH Cloud team to represent the database entities within the app. This required a large amount of refactoring has been completed successfully and the prototype is now functional.