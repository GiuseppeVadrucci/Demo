# Demo
Demo api rest
This poc project exposes three apis and saves the results in a postgresql db.
Just install a local postgresql db and run the application.

The structure of the project is:
-configuration: configuration class -controller: controller that exposes the apis -repository: to persist data in the db
-dto: data transfer objects -entity: objects stored in the db -exceptions: custom exceptions class -logic: the business
logic of the project -service: interfaces implemented in the logic -test: unit tests classes for code coverage
