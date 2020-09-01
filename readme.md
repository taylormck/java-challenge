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
- [x] Updated Spring Boot
- [x] Added smoke tests for Employee Controller
- [x] Added tests for the Employee Controller
- [x] Added tests for the Employee Service
- [x] Added search by name
  - [x] Tests
- [x] Added search by department
  - [x] Tests

#### What's next?

There's a few things I would have liked to do if I'd had a bit more time.

First, when I implemented the ability to search by name and department, I had
implemented them as new paths. Given a bit more time, I would have liked to
go back and instead implement them as queries on the `/employees`endpoint, i.e.,
`/employees?name="Jimmy"&department="R&D"`. This would allow for mixing and
matching of the filters as desired, and wouldn't requrie new endpoints to be
supported.

The next feature I would have added would have been to make a proper table for
departments, rather than let them be strings on the employee table. This would
have allowed for a bit more consistency when dealing with them, and would
make searching for users by department a simple join.

Finally, a few endpoints to do anything interesting with salary would have been
nice. Perhaps things like getting the mean/median/mode salaries for all
employees or by department, highest salaries, etc. It's information that we
have in the DB, but aren't doing anything with.
