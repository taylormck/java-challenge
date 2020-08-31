### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- Fork this project
- Enhance the code in any ways you can see, you are free!. Some possibilities:
  - Add tests
  - Change syntax
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your fork

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### What I've Done
[x] Updated Spring Boot
[x] Added smoke tests for Employee Controller
[x] Added tests for the Employee Controller
[ ] Added tests for the Employee Service
[ ] Added search by name
  [ ] Tests
[ ] Added ability to query Departments
  [ ] Smoke tests
  [ ] List all Departments
    [ ] Tests
  [ ] Post Department
    [ ] Tests
  [ ] Get Department by ID
    [ ] Tests
  [ ] Get Department by Name
    [ ] Tests
  [ ] Get Department by Employee
    [ ] Tests
  [ ] Get Employee by Department
    [ ] Tests