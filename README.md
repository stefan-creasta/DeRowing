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
- The device must have Java SDK 17(or above) installed.
- The device must have Javafx-SDK-17.0.2 or a later version installed.
- The device must have Postman installed.

### Starting the backend
Through IntelliJ
- In the top right corner, add the configurations of the microservices.
- Using spring boot to start all the configurations.

### Accessing the Database
If you want to have a look at the database, please use the inside function of IntelliJ which is in the top right corner of the screen. After running spring boot, you can access the databases we are using for storing information.

### How to start
Before a user wants to let his friends join his rowing activity, he must create one by himself. Since we are using the Spring Security Service to make sure only "safe" users can access our system, the user has to register first. After registration, he can access the other 3 micro-services to actually experiencing our system.

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

- The port for this microservices is 8082.

- There are frequently used methods for competition part:

  - http://localhost:8082/competition/hello
    - This is for testing whether you are connected correctly and also whether your register process is successful.

  - http://localhost:8082/competition/join
    - This is for join a competition, you should send the information in the format of **JoinRequestModel**
  - http://localhost:8082/competition/create
    - This is for create a competition, you should send the information in the format of **CompetitionCreateModel**
  - http://localhost:8082/competition/cancel
    - This is for cancel a competition, you should send the information in the format of **ActivityCancelModel**
  - http://localhost:8082/competition/edit
    - This is for edit a competition, you should send the information in the format of **CompetitionEditModel**
  - http://localhost:8082/competition/find
    - This is for find a competition, you should send the information in the format of **Position** (Only one position information)

- There are frequently used methods for training part:

  - http://localhost:8082/training/create
    - This is for create a training, you should send the information in the format of **TrainingCreateModel**
  - http://localhost:8082/training/join
    - This is for join a training, you should send the information in the format of **JoinRequestModel**
  - http://localhost:8082/training/edit
    - This is for edit a training, you should send the information in the format of **TrainingEditModel**
  - http://localhost:8082/training/cancel
    - This is for cancel a training, you should send the information in the format of **ActivityCancelModel**

#### Boat-Microservice

- This microservice is for creating different types of boats and storing for each position for a boat the number of people that still need to join, as well as the current rowers.

- The port for this microservice is 8085.

- There are frequently used methods:

  - http://localhost:8085/boat/hello
    - This is for testing whether you are connected correctly and also whether your register process is successful.
  - http://localhost:8085/boat/create
    - This is for creating a boat, you should send the information in the format of **BoatCreateModel**
  - http://localhost:8085/boat/insert
    - This is for inserting a rower in a boat for a certain position, you should send the information in the format of **BoatRowerEditModel**
  - http://localhost:8085/boat/remove
    - This is for removing a rower from a certain boat, you should send the information in the format of **BoatRowerEditModel**
  - http://localhost:8085/boat/delete
    - This is for deleting a certain boat from the repository, you should send the information in the format of **BoatDeleteModel**
  - http://localhost:8085/boat/check
    - This is for checking which boats from a list can add one more rower for a certain position, you should send the information in the format of **BoatEmptyPositionsModel**

#### User-Microservice

- This microservice is for allowing the user to create an account and also retrieve notifications from database. 

- The port for this microservice is 8084.

- There are frequently used methods:

  - http://localhost:8084/create
    - This is for creating an user, you should send the information in the format of **UserDetailModel**.
  - http://localhost:8084/getdetails
    - This is for getting the details of a specific user.
  - http://localhost:8085/join
    - This is for saving the request into the message database about wanting to join an activity from a participant to the activity owner.
  - http://localhost:8085/notifications
    - This is for getting all notifications of a user.
  - http://localhost:8085/update
    - This is for saving into the mesage database the decision of the owner to the participant. 

## How to contribute to it
If you want to contribute, you can open an issue relating to a feature, refactor or bug, and it can be worked on. You can clone the repository and work on a branch you created, then open a merge request. We point you to the required template documents (issue/merge request) and the code of conduct located in the docs folder.

## Copyright / License (opt.)
