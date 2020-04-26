
### Instructions:

This application is bundled with Maven Wrapper, so it does not require a local Maven installation. Maven install it, then run it from the command line: 
```
mvnw install
mvnw spring-boot:run
```
Once the app is up and running, Swagger-UI can be leveraged for making testing easier. It can be accessed 
[here](http://localhost:8080/v1/user-registration-api/swagger-ui.html).

Exposed resource: **/v1/user-registration-api/users**

Supported method: **POST**

Resource format:
```
{
  "userName": "JohnDoe",
  "password": "vAl1d",
  "dob": "1990-12-31",
  "ssn": "111-22-3333"
}
```
Resource class path: **com\caiodorn\codingtests\gamesys\user\rest\User.java**

Business-level validations are applied as follows: 
```
public void validateNewUser(User incomingUser) {
    validateConflict(incomingUser.getUserName()); //userName is unique, so expect to get a validation error if attempted to register the same twice
    validateBanned(incomingUser.getDob(), incomingUser.getSsn()); //this is the validation required for this coding test, will result in a call to the ExclusionService.
}
``` 

This app features an in-memory database linked to a "local" profile, which is activated by default. Db is initialized 
with two users: one valid and one blacklisted. Check **com\caiodorn\codingtests\gamesys\user\LocalDBLoader.java** for
 details on how those two users are created. H2 database console can be acessed [here](http://localhost:8080/v1/user-registration-api/h2-console).
For logging into H2 console:
```
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Pasword: <leave this empty>
```

Jacoco reports: **\target\site\jacoco\index.html**

API docs (Spring REST Docs): **\target\generated-docs\api.html**

---------------------

### "Extras" included/used in this project:

*   Swagger (API-first approach, courtesy of [SpringFox](https://springfox.github.io/springfox/docs/current/))

Mainly because Swagger-ui is a neat tool for API testing/development, eliminating the need of using tools like Postman. 
Opted for using API-first approach, which I like better.

*   [Zalando's Problem API](https://github.com/zalando/problem-spring-web)

In their words, "Problem is a library that implements application/problem+json". First time using it, wanted to find a
standardized way of formatting JSON error responses instead of coming up with one of my own, and found it. The API
implements [RFC7807](https://tools.ietf.org/html/rfc7807).

*   [Mapstruct](http://mapstruct.org/documentation/stable/reference/html/)

A great API for mapping objects, helps keeping the code clean and focusing on the actual business needs.

*   [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/)

SpringFox (Swagger) is nice and useful, but if you try using it as a documentation tool, code can get quite messy. Since I
still wanted to use it as a testing tool, I did some research and this is the result. Spring REST Docs generates an HTML doc, 
which is navigable, however it lacks that pleasant looking UI provided by Swagger. ON THE OTHER HAND, the doc is bound to unit tests 
and works as a contract, meaning that changes to the API will break unit tests and FORCE developer to update them. It can 
get way better than what I've done (first time using it), but I think it is a nice addition.

*   [Jacoco](https://www.jacoco.org/jacoco/trunk/doc/)

For generating test coverage metrics/reports.

-------------------
### Observations:

1 - since this is a REST ([level 2](https://restfulapi.net/richardson-maturity-model/#level-two), no hypermedia), I took
the freedom to make what is described in the coding-test as a "register" named method a POST against a ***user resource***.
Behind the scenes it is indeed a method called "register".

2 - one of the checks is to verify if the provided user is already registered. However, since ExclusionService looks for a
DOB+SSN combo I assumed that a PERSON can own multiple users - that is, in this implementation who gets blacklisted is the 
owner (person), not just a username. Expect to be able to register users with the same DOB+SSN pair as long as their 
usernames differ (and the guy is not blacklisted hehe).

3 - for integration tests, the only class being directly tested is the controller. From there, it goes all the way to the 
DB and back, and that's why I decided to not overdo it. This is a good debate, though.

4 - unit tests are covering what is important. I've added Jacoco plugin to generate coverage reports and configured it to 
ignore POJOs and interfaces. Hope you don't mind... but I'm not a fan of writing tests for "dumb" classes. Also it helps 
keeping things clean.

5 - you'll notice there aren't barely any comments/javadoc. Again, I don't know what you're expecting regarding this
matter... so I tried to keep it clean. In "real life" this is how I proceed unless the company I'm working for says
I should do otherwise.

Thanks!