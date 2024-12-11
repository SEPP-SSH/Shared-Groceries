# Containerisation and Orchestration
---
Within the larger prototype, the SSH Cloud prototype consists of a server that listens and waits for requests from client instances. To persist the data required to service these requests, as well as the data that these requests generate, the SSH Cloud server utilises a separate MySQL database server.

To seamlessly integrate communication between each of these servers and the client instance, and to ensure compatability across platforms, containerisation has been used to isolate the SSH Cloud server and its database server in seaprate Docker containers.

The architecture of this containerisation structure is as follows:

![SSH Cloud containerisation diagram](./Attachments/SSH%20Cloud%20Docker%20Structure.png)

To _orchestrate_ these containers, **docker-compose** has been used to start containers like so:
- The MySQL database server container is started first, binding the container port 3306 to port 3306 on the default docker-compose network on the host machine. This port is used to send and receive database related data to/from the MySQL server.
- The SSH Cloud server container is then started after the database server container, where the container exposes port 3306 such that data can be sent to it from the database server.

This containerisation and orchestration gives rise to a continuous integration workflow.

# Continuous Integration Workflow
The continuous integration workflow takes place in the form of a GitHub Action, which for the SSH Cloud product:
- Checks out the code from the relevant branch onto the VM that is running the action.
- Run the docker-compose file that starts the database server in a Docker container and _then_ the SSH Cloud server in a Docker container. The docker-compose file is run with the flag to listen for the exit code from the SSH Cloud server container, as the datbaase server should listen until terminated.
  - When running the SSH Cloud server Docker container, Maven is instructed to build the project, resulting in the unit tests being ran.
  - If any errors occur in the unit tests, the build will fail, and the action will fail as a whole, due to the exit code flag mentioned above.
- If the build succeeds, a .jar file is built, extracted from the SSH Cloud server Docker container, and uploaded as a build artifact in the dashboard for the GitHub Action.

This GitHub Action is used as a part of the branch protection rule for the `main` branch of the repository, where branches being merged into main must pass status checks - which we have set to include this GitHub Action. This workflow decision helps keep the code in the `main` branch high-quality, by ensuring that the SSH Cloud source code compiles and passes its unit tests.