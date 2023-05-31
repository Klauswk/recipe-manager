# Recipe Manager
## _How to build_

The project can be built simply by running the maven command `mvn clean package`, this is sufficient to build it.

To run, if you are aiming to run local, run `mvn spring-boot:run -Dspring-boot.run.profiles=local`, the local env comes with a few dishes, but the others don't.

The other environments present are: 
- dev `mvn spring-boot:run -Dspring-boot.run.profiles=dev`
- uat `mvn spring-boot:run -Dspring-boot.run.profiles=uat`
- prd `mvn spring-boot:run -Dspring-boot.run.profiles=prd`

This project was designed around hexagonal architecture, with a well-defined domain with no dependencies aside from Lombok.

The communication is made through the implementation of an interface, the RestApi being the driver, and the database the driven ports.

For simplicity, the project was developed using an in-memory database, but it could be easily replaced by any RDBS such as Postgres, MySQL, or NoSQL without having to change the other parts of the system.

## List of technologies

- RestAPI: Spring-Web alongside OpenAPI3 for the documentation
- Database: H2 with Hibernate using Specification to perform the filters in the database.
- Support: Lombok and orgmapper to reduce the boilerplate code.


## Database

The database can be accessed through the following endpoint http://localhost:8080/h2-console/, the connection, user, and password can be found below

Local:

    url: jdbc:h2:mem:testdb
    username: sa
    password: test

Dev / UAT / PRD

    url: jdbc:h2:mem:notMemDb
    username: sa
    password: test

## OpenApi 3 / Swagger

The project contains an OpenAPI page to easily access and send requests available at http://localhost:8080/swagger-ui/index.html

It provides examples to run the necessary requests.

## Filtering

The filters were implemented using query params, as such, for the necessary tasks, it contains the following parameters:

- id - filter by the id
- name - filter by the name, ignore case
- includes - filter by included ingredient through name, ignore case
- excludes - filter by excluding ingredient by name, ignore case
- vegetarian - filter whether a dish is vegetarian or not
- numberOfServings - filter by the number of servings
- instructions - filter by the instruction string, ignore case

### Examples
1 - All vegetarian recipes - http://localhost:8080/v1/recipe?vegetarian=true
2 - Recipes that can serve 4 persons and have “potatoes” as an ingredient - http://localhost:8080/v1/recipe?includes=potatoes&numberOfServings=4
3 - Recipes without “salmon” as an ingredient that has “oven” in the instructions. - http://localhost:8080/v1/recipe?excludes=salmon&=oven

