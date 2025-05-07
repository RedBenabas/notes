# Description
A Notes Organising Application using AI



# Technology
* Currently, only a backend server api is implemented for Creating, Editing, View and Deleting a Note model object.
* Using PS Slingshot, the above api is built on the tech stack of a Maven module built on Spring Boot Java framework.
* To run the app locally, execute: 
  - ```mvn clean package docker:build```
  - followed by ```docker run -p 8080:8080 your-docker-image-name```.
  - The api will be available on ```http://localhost:8080/notes```
* Describe the testing approach and how to execute the test cases.
