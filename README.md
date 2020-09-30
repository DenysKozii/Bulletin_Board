Bulletin Board

Application for posting and reading bulletins in the easiest way. 
Your account will be in safe because of encrypting all passwords!   

Short description

To build the project following requirements should be met:
* Installed Oracle JDK 11

Internally I use:
* In memory h2 database for easy start 

To assemble JAR, in the project root directory run:
```bash
mvn clean package
```

Artifacts
Build generate following artifacts:
- executable jar file BulletinBoard-1.0-SNAPSHOT.jar
- Api documentation in target/apidocs

Run 
To run execute 
```bash
java -jar target/BulletinBoard-1.0-SNAPSHOT.jar
```