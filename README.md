## Description of project
For this project, the client has asked to deliver a rowing managing system with the goal of help rowers arrange their rowing activities. The client also put forward requirements that the application had to abide by.

### Requirements
- The application must use the microservice architecture.
- The application should not have a front end.


## Group members

| Profile Picture | Name | Email |
|---|---|---|
| ![](https://secure.gravatar.com/avatar/0408829ba64f29bb1f00e5934d5b6968?s=800&d=identicon&size=50) | Yongcheng Huang | Y.Huang-51@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/31830b2713ea008ceeb0262a7333e4c9?s=800&d=identicon&size=50) | Maarten van der Weide | M.V.vanderWeide@student.tudelft.nl |
| <img src="https://gitlab.ewi.tudelft.nl/uploads/-/system/user/avatar/3749/avatar.png?width=50&size=5" style="zoom:25%;" /> | Vlad Iftimescu | V.G.Iftimescu@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/d7f782ec04f7eeea0f5b3d0fc2344554?s=800&d=identicon&size=50) | Stefan Creastă | Creasta@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/15dfa52ead3382296d8a544a549ba201?s=800&d=identicon&size=50) | Viet Luong | H.V.Luong-1@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/325ee4730af88cf34054be3ab5aec337?s=192&d=identicon&size=50) | Rithik Appachi Senthilkumar | R.AppachiSenthilkumar@student.tudelft.n |

## How to run it

### Requirements
Before running the application the user must make sure that their devices have the following requirements:
- The device must have Java SDK 11(or above) installed.
- The device must have gradle installed (preferably the latest version).

### Configure the services
The ports and URL's of the services can be changed in the application.properties file of a specific service.  
**Warning**: make sure that if ports/url's of service are changed, this change is also reflected in the application.properties of the other services, otherwise the services cannot communicate with eachother.
### Building the project
The project is divided into multiple microservices, thus building can be done in the following way:  
```java
// For all the services at the same time
gradle build
```
```java
// For a specific service
gradle <service-name>:assemble
```

### Running the services
To start a microservice the following command can be used:  
```java
gradle <service-name>:bootRun
```

### Database setup
The services use an in-memory database by default.  
To change this to a persistant database the following change needs to be made in the application-dev.properties of a microservice  
```java
spring.jpa.hibernate.ddl-auto=create-drop --> spring.jpa.hibernate.ddl-auto=update
```

To inspect database content during runtime the following line needs to be added to the application-dev.properties of a microservice:
```java
spring.h2.console.enabled=true
```
If you run the service with this property, the database contents can be inspected at: 
```java
http://localhost:<port>/h2
```

### How to start
Before a user wants to let his friends join his rowing activity, he must create one by himself. Since we are using the Spring Security Service to make sure only authenticated users can access our system, the user has to register first. After registration, he can access the other 3 micro-services to actually experiencing our system. 

An example chain of request can then look like this:
```java
/register (Auth service)
/authenticate (Auth service)
/create (User service) <-- this mapping is to complete the registration and should be used for every new user.
/competition/create (Activity service)
```

#### Authentication-Microservice
- This microservice is mainly in charge of the security part.
- The port for this microservice is 8081.
- There are frequently used methods: 
  - http://localhost:8081/register
    - For registering as a new user.

  - http://localhost:8081/authenticate
    - For getting the token. (Once getting the JWT Token, please paste it to the Authorization part of the Postman, which should be the only way you access our system.)


#### Activity-Microservice

- This microservice is for creating activities of two types: Competition and Training. Competitions should have their own constraints on the attendees while training is welcoming everyone to join.

- The default port for this microservices is 8082.

- This service exposes the following REST-mappings.

  - /competition/hello
    - This is for testing whether you are connected correctly and also whether your register process is successful.

  - /competition/join
    - This is for join a competition, you should send the information in the format of **JoinRequestModel**
  - /competition/create
    - This is for create a competition, you should send the information in the format of **CompetitionCreateModel**
  - /competition/cancel
    - This is for cancel a competition, you should send the information in the format of **ActivityCancelModel**
  - /competition/edit
    - This is for edit a competition, you should send the information in the format of **CompetitionEditModel**
  - /competition/find
    - This is for find a competition, you should send the information in the format of **Position** (Only one position information)

  - /training/create
    - This is for create a training, you should send the information in the format of **TrainingCreateModel**
  - /training/join
    - This is for join a training, you should send the information in the format of **JoinRequestModel**
  - /training/edit
    - This is for edit a training, you should send the information in the format of **TrainingEditModel**
  - /training/cancel
    - This is for cancel a training, you should send the information in the format of **ActivityCancelModel**

#### Boat-Microservice

- This microservice is for creating different types of boats and storing for each position for a boat the number of people that still need to join, as well as the current rowers.

- The default port for this microservice is 8085.

- This service exposes the following REST-mappings.

  - /boat/hello
    - This is for testing whether you are connected correctly and also whether your register process is successful.
  - /boat/create
    - This is for creating a boat, you should send the information in the format of **BoatCreateModel**
  - /boat/insert
    - This is for inserting a rower in a boat for a certain position, you should send the information in the format of **BoatRowerEditModel**
  - /boat/remove
    - This is for removing a rower from a certain boat, you should send the information in the format of **BoatRowerEditModel**
  - /boat/delete
    - This is for deleting a certain boat from the repository, you should send the information in the format of **BoatDeleteModel**
  - /boat/check
    - This is for checking which boats from a list can add one more rower for a certain position, you should send the information in the format of **BoatEmptyPositionsModel**

#### User-Microservice

- This microservice is for allowing the user to create an account and also retrieve notifications from database. 

- The default port for this microservice is 8084.

- There are frequently used methods:

  - /create
    - This is for creating an user, you should send the information in the format of **UserDetailModel**.
  - /getdetails
    - This is for getting the details of a specific user.
  - /join
    - This is for saving the request into the message database about wanting to join an activity from a participant to the activity owner.
  - /notifications
    - This is for getting all notifications of a user.
  - /update
    - This is for saving into the mesage database the decision of the owner to the participant. 

## How to contribute to it
If you want to contribute, you can open an issue relating to a feature, refactor or bug, and it can be worked on. You can clone the repository and work on a branch you created, then open a merge request. We point you to the required template documents (issue/merge request) and the code of conduct located in the docs folder.  
When contributing do make sure that you adheare to the checkstyle as defined in config/checkstyle.xml

## Copyright / License (opt.)