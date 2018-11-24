
### Instructions:

Server is configured to run on port 8080. API can be tested straight from Swagger-ui at 
**http://localhost:8080/v1/user-registration-api/swagger-ui.html**

Exposed resource: **/v1/user-registration-api/users**

Supported method: **POST**

Jacoco reports: **\target\site\jacoco\index.html**

API docs (Spring REST Docs): **\target\generated-docs\api.html**

---------------------

### What I've included/used in this project (not counting Maven, Spring - the usual):

*   Swagger (API-first approach, courtesy of [SpringFox](https://springfox.github.io/springfox/docs/current/))

Although this is a very nice tool, if used as a documentation tool, app code gets easily bloated by its own, thus making 
it not very pleasant to maintain and go through. Another downside of it is that there is no guarantees that the API stays
up-to-date with the implementation, nor that the documentation is effectively respected (more on this soon). So it is there, 
with minimal documentation, mainly because Swagger-ui is a neat tool for API testing/development. 

*   [Zalando's Problem API](https://github.com/zalando/problem-spring-web)

In their words, "Problem is a library that implements application/problem+json". First time using it, wanted to find a
standardized way of formatting JSON error responses instead of coming up with one of my own, and found it. The API
implements [RFC7807](https://tools.ietf.org/html/rfc7807).

*   [Mapstruct](http://mapstruct.org/documentation/stable/reference/html/)

A great API for mapping objects (say goodbye to writing converters).

*   [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/)

The fact that I wasn't happy with the plethora of annotations that come along with SpringFox (Swagger) made me do some
research... this is the result. It generates an HTML doc, which is navigable, however it lacks that amazing UI provided 
by Swagger. ON THE OTHER HAND, the doc is bound to unit tests and works as a contract, meaning that changes to the API
will most certainly break unit tests and FORCE developer to put it on back on track. It can get way better than what I've
done (first time using it), but I think it is a nice addition.

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
otherwise.

Thanks!