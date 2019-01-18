# Task

Provide a simple book recommendation service which is usable via a REST API. It needs to be possible to define a new user, who will then be provided with 20 book recommendations. For a recommendation of a user, feedback can be provided. The feedback can either be "liked the book", "disliked the book" or "not interested".
Requirements:
* Users are identified by their username.
* The list of recommendations should contain exactly 20 entries if possible.
* The code should be tested as appropriated.

### Prerequisites

* Install Java JDK 8
* Spring Boot:  `2.1.1.RELEASE`
* Gradle: `https\://services.gradle.org/distributions/gradle-4.10.2-bin.zip`
* lombok

In more details the dependencies are in file `build.gradle`

### Build the service

1. Package application to an executable archive (jar or war file) by building it using the build command:
   `./gradlew build`
 
### Run the application

1. After you have build the application then run it by command:
   `./gradlew bootRun`
   
2. Or just run it from an IDE.

 After the application has run successfully, you should be able to have a look again in db admin page `http://localhost:8090`, with schema updated.
 ![Migraftion Files](/images/img1.png/)
 
So, you should also see sql tables that are already created. Easy way to set up the SQL connection is via docker file and here is the [link](https://github.com/mdoklea/docker-with-postgresql).
   
### Automated Tests 

   Test classes are named <className>Test. That is for a class `BookCsvParseService` the test would be `BookCsvParseServiceTest`.
   
   Test methods are named `<methodUnderTest>_<stateUnderTest>_<expectedBehavior>`. 
   Even though refactoring might change the names its still useful to have the method name in there to know immediately what's tested.
   
   Example:
   
   `parseBooksFromCsvFile_WithValidInput_ReturnsCorrectFields`
   With a complicated integration test it makes sense to rather describe a broader behavior, but we'll see on a case by case basis.
   
   Organization:
   
   I followed the `Given-When-Then` or `Arrange-Act-Assert` patterns inspired by BDD. There are three code blocks with up to 4 lines each. Example:
   * givenUser().authenticated(true).build();
   * val result = service.doAction();
   * assertThat(result.value).isEqualTo(123);
   
