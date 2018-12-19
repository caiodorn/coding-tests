####Build project: 

`mvnw clean install`

---

####Run app: 

`mvnw tomcat7:run-war`

#####Web App base path: 
http://localhost:9090

---

####Login process:

Send a POST request to **/login** as follows:

#####Request:
`curl -d '{"username" : "john doe", "password" : "1234"}' -H "Content-Type: application/json" -X POST http://localhost:9090/login -i`
                
A token will be returned upon authentication. It corresponds to the value assigned to "Authorization". **COPY IT**.

#####Response:

```
Server: Apache-Coyote/1.1
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huIGRvZSIsImV4cCI6MTU0NTE4MDI3OH0.ERepXs061Uwkc3r76O9fwDJtBvfbwAr5anjU0PZX-cLBuQ4B8tQ7FZbD-VvGT5pyALVSW9kUBDL0Bm0MDLdtAQ
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Tue, 18 Dec 2018 23:44:38 GMT
```

---

####Consuming the API:

Whenever sending requests to the endpoints below, send the previously saved token along in the header
under the "Authorization" field. Luckily enough, now can be leveraged for this. Just click [here](http://localhost:9090/swagger-ui.html) :)

From there you can perform all possible operations (except for the login, as you noticed). Have fun!

---
####Notes:

My computer runs Windows 10 and Intellij Ultimate, latest version. For some reason, curl login request returned 404 when the server was booted 
up from Git Bash using maven wrapper. That's the only case I had issues... so if you do the same and the server returns 404, I recommend running
from within an IDE (preferably Intellij, just in case). Both the shortcuts on maven tab and commands ran from Intellij's terminal worked for me. 