# Containerisation and Orchestration
---
Within the larger prototype, the SSH Cloud prototype consists of a server that listens and waits for requests from client instances. To persist the data required to service these requests, as well as the data that these requests generate, the SSH Cloud server utilises a separate MySQL database server.

To seamlessly integrate communication between each of these servers and the client instance, and to ensure compatability across platforms, containerisation has been used to isolate the SSH Cloud server and its database server in seaprate Docker containers.

The architecture of this containerisation structure is as follows:

![SSH Cloud containerisation diagram]()

To _orchestrate_ these containers, **docker-compose** has been used to start containers like so:
- asdf

This containerisation and orchestration gives rise to a continuous integration workflow.

# Continuous Integration Workflow
