Bulletin Board

Application for posting and reading bulletins in the easiest way. 
Your account will be in safe because of encrypting all passwords!
System almost under tests and without bugs)  

To build the project following requirements should be met:
* Installed Oracle JDK 11

Internally I use:
* In memory h2 database for easy start 

To assemble JAR, in the project root directory run:
```bash
mvn clean package
```

Build generate following artifacts:
- executable jar file BulletinBoard-1.0-SNAPSHOT.jar
- Api documentation in target/apidocs


To run execute 
```bash
java -jar target/BulletinBoard-1.0-SNAPSHOT.jar
```