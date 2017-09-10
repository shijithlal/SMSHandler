# SMSHandler
## How to setup
### Requirements
Java<br> 
Maven(Only for build)<br> 
redis<br>
mysql<br>
web and application container
### Steps
1. Install redis and mysql 
2. Import sqldump.sql file to mysql server
3. Update application.properties file with mysql host, database, user name and password
4. Build and Deploy the application in application server. 
### Build
mvn clean build
To Skip tests use, mvn clean install -Dmaven.test.skip=true  
## Sample request
### Inbound
curl -H "Content-Type: application/json" -H "Authorization: UserName=plivo1,Password=20S0KPNOIM" -X POST -d '{"from":"441224980093","to":"4924195509198","text":"hello"}' http://localhost:8080/inbound/sms/
### Outbound
curl -H "Content-Type: application/json" -H "Authorization: UserName=plivo1,Password=20S0KPNOIM" -X POST -d '{"from":"4924195509198","to":"441224980093","text":"Hello"}' http://localhost:8080/outbound/sms/
## Test
### Unit Test
All the unit tests are there in com.shijith.sms package. 
There is no dependency for unit tests.

### Integration tests
Integration tests are in integraion package. 
You have to install redis and mysql on local machine to run integration tests. 
The sqldump should be imported before running integration tests. 
#### What is missing
There is no integration test to check that the cache entries will expire as expected. We have to manually test the same.
 
