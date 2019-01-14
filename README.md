# Task

Provide a simple book recommendation service which is usable via a REST API. It needs to be possible to define a new user, who will then be provided with 20 book recommendations. For a recommendation of a user feedback can be provided. The feedback can either be "liked the book", "disliked the book" or "not interested".
Requirements:
* Users are identified by their username.
* The list of recommendations should contain exactly 20 entries if possible.
* The code should be tested as appropriated.

# Prerequisites

* Install Java JDK 8
* Spring Boot:  `2.1.1.RELEASE`
* Gradle: `https\://services.gradle.org/distributions/gradle-4.10.2-bin.zip`
* lombok

In more details the dependencies are in file `build.gradle`

# Steps to build the service

1. Package application to an executable archive (jar or war file) by building it using the build command:
   `./gradlew build`
 
# Steps to run the application

1. After you have build the application then run it by command:
   `./gradlew bootRun`
   
2. Or just run it from an IDE.

 After the application has run successfully, you should be able to have a look again in db admin page `http://localhost:8090`, with schema updated.
 ![Migraftion Files](/images/img1.png/)
 
So, you should see now that sql tables are already created:
 ![SQL Tables](/images/img2.png/)

# Test Application & Endpoints

Use API development environment like [Postman](https://www.getpostman.com/) for testing the application.

1. The very first step is to upload the books from the csv files.
In folder `https://github.com/mdoklea/book-recommendation-service/tree/master/files` there some .csv files, both for importing data and for testing cases.
