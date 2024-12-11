# Containerisation and Orchestration
---
Within the larger prototype, the SSH Cloud prototype consists of a server that listens and waits for requests from client instances. To persist the data required to service these requests, as well as the data that these requests generate, the SSH Cloud server utilises a separate MySQL database server.

To seamlessly integrate communication between each of these servers and the client instance, and to ensure compatability across platforms, containerisation has been used to isolate the SSH Cloud server and its database server in seaprate Docker containers.

There is a different containerisation structure for each of the following three contexts:

- The SSH Cloud unit testing containerisation structure:
![SSH Cloud containerisation diagram - Unit testing context](./Attachments/SSH%20Cloud%20Unit%20Testing%20Docker%20Structure.png)
- The SSH Cloud integration testing containerisation structure:
![SSH Cloud containerisation diagram - Integration testing context](./Attachments/SSH%20Cloud%20Integration%20Testing%20Docker%20Structure.png)
- The SSH Cloud "production" containerisation structure:
![SSH Cloud containerisation diagram - Production context](./Attachments/SSH%20Cloud%20Production%20Docker%20Structure.png)

In the unit testing context:
- docker-compose is used to orchestrate these containers such that the MySQL database server is started first, binding the container port 3306 to port 3306 on the default docker-compose network on the host machine. This port is used to send and receive database related data to/from the MySQL server.

In the integration testing context:
- docker-compose is used to orchestrate these containers such that the MySQL database server first _(in the same way described above)_, and then the SSH Cloud server Docker container second, where this will listen for client requests on port 8080 on the default docker-compose network. Once these two servers have _both_ spun up, the client instance container is started, where it exposes port 8080 to receive data from the SSH Cloud server, and is set to address the SSH Cloud server on the docker-compose network on port 8080.

In the "production" context:
> Note that by "production" context, we refer to when the server is expected to listen indefinitely for requests whilst the Android app implementation of SSH App is running and interacting with the server.
- In this context, docker-compose is used to orchestrate the MySQL server and SSH Cloud server containers, in the same order as described in the unit testing context, and the client instance operates on the host machine's network, whilst the two server Docker containers run on the default docker-compose network. Hence, to ensure that client and server can communicate, the client instance communicates over port 8080 on the host machine, which the SSH Cloud server container has it's port 8080 bound to. 

These containerisation and orchestration structures for the unit and integration testing contexts give rise to continuous integration workflows.

# Continuous Integration Workflows
The continuous integration workflows for SSH Cloud take place in the form of GitHub Action, which follow the process (for the unit testing and integratino testing contexts) of:
- Checking out the code from the relevant branch onto the VM that is running the action.
- Running the docker-compose file that starts the database server in a Docker container and _then_ the SSH Cloud server in a Docker container. The docker-compose file is run with the flag to listen for the exit code from the SSH Cloud server container, as the datbaase server should listen until terminated.
  - When running the SSH Cloud server Docker container, Maven is instructed to build the project, resulting in the unit tests being ran.
  - If any errors occur in the unit or integration tests (depending on context), the build will fail, and the action will fail as a whole, due to the exit code flag mentioned above.
- In the unit testing workflow, if the build succeeds, a .jar file is built, extracted from the SSH Cloud server Docker container, and uploaded as a build artifact in the dashboard for the GitHub Action. _(Note that a build artifact isn't uploaded when running the integration tests, as there's no need for duplicated artifacts)._

These GitHub Actions are used as a part of the branch protection rule for the `main` branch of the repository, where branches being merged into main must pass status checks - which we have set to include these GitHub Actions. This workflow decision helps keep the code in the `main` branch high-quality, by ensuring that the SSH Cloud source code compiles and passes its unit and integration tests.