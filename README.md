# Description
A Notes Organising Application using AI



# Technology
* Currently, only a backend server api is implemented for Creating, Editing, View and Deleting a Note model object.
* Using PS Slingshot, the above api is built on the tech stack of a Maven module built on Spring Boot Java framework.
* To run the app locally, execute: 
  - ```mvn clean package docker:build```
  - followed by ```docker run -p 8080:8080 your-docker-image-name```
  - The api will be available on ```http://localhost:8080/notes```
* For testing,, ```JUnit, Mockito``` are used for unit testing the backend api logic; ```spring-test``` module is using for http request hitting the API layer.

# To Do
* Implement a simple UI with a simple form for creating and viewing Notes. This could be done using PS Slingshot and the ```spring-boot-starter-thymeleaf``` module of Spring Boot
* Deploy the application to ```vercel```. Last attempt failed as [maven is not recognised as a build tool](src/main/resources/vercel.png).