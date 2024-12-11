# End of Milestone Reports
---
This document encompasses reviews of the work done at each milestone, and reflects on how well it implements the specification in the [selected EDR](Selected%20EDR.pdf), and what steps need to be taken in subsequent milestones.

---

## Report at end of Milestone 2
For the SSH App, the prototype implements the two main views required for the group grocery shopping feature, as outlined in the selected EDR. With respect to the item browsing and selection view, this has been correctly implemented, as per the EDR’s specification. With respect to the basket view, the breakdown tab has been correctly implemented. However, the items tab has not been implemented, and has been omitted in error. However, on reflection, adding this in at this stage would not greatly reduce the risk of approving the group grocery shopping feature for full development, because - as the EDR outlines - the items tab draws on the exact same data as the breakdown tab, and so by having already developed the items tab, we’ve demonstrated that the development of the feature is viable. Whilst this extra view should not be developed at this stage, this change should be correctly added to the relevant documentation.

The prototype implements the database schema and necessary logic for SSH Cloud correctly, as specified in the EDR.

However, one feature that could be added is a “Your total” label to the basket view, to better convey to the users that they only pay for their items in the group grocery order, rather than an even split.