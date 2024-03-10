## Golf Tournament REST API

The idea behind this API is to allow consumers to send POST requests containing "golf tournament" resources, then persisting them in a DB for future retrieval. The caveat is that the resource is "polymorphic": each consumer will be sending it in a different format, requiring the provider to differentiate them and process requests accordingly. 

---------------------

### Usage:

In order to build and run the application, execute the following commands from the project's root folder:

```
mvnw install
mvnw spring-boot:run
```
By default, the webserver will be listening at 
``
localhost:8080
``

Exposed endpoint: **/v1/golf-tournament**

Supported method: **POST**

As mentioned in the "intro" to this document, the resource is not uniform, being possible to send request payloads in two distinct formats. It's been decided that the way this should be handled by the provider is via a custom header named ``sourceId``.

Currently, two values are allowed for ``sourceId`` header: ``A`` and ``B``. The absence of this header or any other value different than these will result in a 404 response.


Sample request for ``sourceId: A``:
```
curl --location --request POST 'localhost:8080/v1/golf-tournament' \
--header 'sourceId: A' \
--header 'Content-Type: application/json' \
--data-raw '{
    "tournamentId": "174638",
    "tournamentName": "Women'\''s Open Championship",
    "forecast": "fair",
    "courseName": "Sunnydale Golf Course",
    "countryCode": "GB",
    "startDate": "09/01/21",
    "endDate": "13/01/21",
    "roundCount": "4"
}'
```

Sample request for ``sourceId: B``:
```
curl --location --request POST 'localhost:8080/v1/golf-tournament' \
--header 'sourceId: B' \
--header 'Content-Type: application/json' \
--data-raw '{
    "tournamentUUID": "southWestInvitational",
    "golfCourse": "Happy Days Golf Club",
    "competitionName": "South West Invitational",
    "hostCountry": "United States Of America",
    "epochStart": "1638349200",
    "epochFinish": "1638468000",
    "rounds": "2",
    "playerCount": "35"
}'
```

#### Bonus business rule: no two "golf tournaments" can be created with same ``externalId`` and ``sourceId``. ``externalId`` is how either ``tournamentUUID`` or ``tournamentId`` (or whatever other field if new sources are added) are known by the DB. 

---------------------

### Development:

``dev`` Spring profile is activated by default. 

This app makes use of an embedded, in-memory Database. For accessing its UI, navigate to
http://localhost:8080/h2-console and fill out these (if needed):

```
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Pasword: <leave this empty>
```

For running unit tests: ``mvw test``

For running integration tests: ``mvw verify -DskipSurefire`` (if flag is not set, executes both unit and integration tests)

The project uses a plugin for generating test coverage reports, which can be found at: **\target\site\jacoco\index.html**

* **Adding a new resource format**

    This application has been designed to allow the introduction of a new resource format (identified by ``sourceId``, as described in the "usage" section) without having to make changes to the entire codebase. 
    
    Steps:
    
    - Create a new resource under ``resource`` package. It will have to implement [GolfTournamentResource](src/main/java/com/caiodorn/imgarena/golftournamentapi/rest/resource/GolfTournamentResource.java) interface.
    - Update [SourceId](src/main/java/com/caiodorn/imgarena/golftournamentapi/rest/resource/SourceId.java) enum with the new ID. 
    - Add a new request mapping to the [controller](src/main/java/com/caiodorn/imgarena/golftournamentapi/rest/GolfTournamentController.java).
    - [GolfTournamentEntityMapper](src/main/java/com/caiodorn/imgarena/golftournamentapi/service/mapper/GolfTournamentEntityMapper.java) will need to be updated with a mapping/conversion declaration for the new 
      resource to be converted to an entity object. Luckily for you, the logic is inferred by a library (Mapstruct), so you don't have to worry about it. That is, in special cases, manual implementations may be needed 
      -> see [LocalDateTimeMapper](src/main/java/com/caiodorn/imgarena/golftournamentapi/service/mapper/LocalDateTimeMapper.java), which is one of those special cases. 
    - Put another entry into the map in [GolfTournamentEntityMapperStrategy](src/main/java/com/caiodorn/imgarena/golftournamentapi/service/mapper/GolfTournamentEntityMapperStrategy.java), so that the service will find your mapping function.
    - It should be it, no need to touch any business logic or write a lot of code.

---------------------

### Plugins and libraries included in this project (the ones worth mentioning):

*   [Zalando's Problem API](https://github.com/zalando/problem-spring-web)

For returning nicely formatted error responses and handling exceptions in the web layer. This is an implementation of [RFC 7807](https://datatracker.ietf.org/doc/html/rfc7807).

*   [Mapstruct](http://mapstruct.org/documentation/stable/reference/html/)

The best object conversion/mapping library I've had the opportunity to work with.

*   [Jacoco](https://www.jacoco.org/jacoco/trunk/doc/)

For generating test coverage metrics/reports.

-------------------
### Observations:

1. I really wanted to have Swagger-UI available in this project, and I tried. Tried quite hard, actually. I tried with two different plugin libraries, and both failed at understanding that it is possible to have two 
   endpoints with the same path mapped to the same HTTP method (i.e. POST /v1/golf-tournament). Well... let's be fair: even I didn't know it was possible, until seeing this exercise. I'm glad Spring is smarter than those 
   plugins! One solution was to break it down into two separate paths, meaning two controllers (ok, not necessarily... but think how messy it could get). So I did it. But then that meant duplicated code -- imagine having 
   ten different consumers, each with their own resource format, it would mean ten controllers that are virtually the same, except for a few naming differences. This made me realize I was going to give up on having a nice 
   Swagger-UI documenting the API in favor of better design and coding practices.
   

2. Having different sources made naming classes a challenge, and I think it shows (sad).
   

3. Had I had more time for this, I would have tried Spring WebFlux. For the same reason I kept security disabled.


4. One trade-off that I had to make in order to provide the flexibility I wanted was to use a marker interface for the resources. I'm not happy with that, but it was the price I had to pay, let's say. In a real work environment
   I believe another pair of eyes would have helped me find a different --_better_--  solution.


5. As I mentioned, this is the first time I get this requirement of sharing one endpoint with different object formats. So maybe the decisions I took are far from being optimal... and if they are, please let me know as I'm now quite triggered by this problem. 


6. I believe in incremental development, and I do my best to avoid overengineering. 


7. Not sure if the rule I came up with for validating duplicates in the DB is valid, but it makes sense if the externalIds are to be unique per event. Example: Roland Garros can't have the same externalId for two different 
   years. Of course, this is just a rule I "invented" for avoiding repeated tournaments in the DB. In the real world, those records would probably be updatable via PUT/PATCH requests. 


8. For sure there is more I want to talk about, but this is what comes to my mind most immediately :)


