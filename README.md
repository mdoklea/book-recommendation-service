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

# Build the service

1. Package application to an executable archive (jar or war file) by building it using the build command:
   `./gradlew build`
 
# Run the application

1. After you have build the application then run it by command:
   `./gradlew bootRun`
   
2. Or just run it from an IDE.

 After the application has run successfully, you should be able to have a look again in db admin page `http://localhost:8090`, with schema updated.
 ![Migraftion Files](/images/img1.png/)
 
So, you should also see sql tables that are already created.

# Test Application & Endpoints

Use API development environment like [Postman](https://www.getpostman.com/) for testing the application.

__STEP_1__. `"/api/books"`

The very first step is to upload the books from the csv files, using endpoint:
```
POST METHOD:
http://localhost:8080/api/books/upload
```
User  `form-data`, and choose file from your folder as in the image below.
 ![Upload Csv File](/images/upload.png/)
 
In folder `https://github.com/mdoklea/book-recommendation-service/tree/master/files` there are .csv files, both for uploading data and testing cases.

By uploading/parsing the book files, whenever there is a new genre, I save it in db. 
Find saved books with endpoint: 
```
GET METHOD:   
http://localhost:8080/api/books
```

 ![Get Books](/images/books.png/)
 
Json view example of a book is like this: 
 
 ```
 {
        "id": "3b8b3688-dbb4-45c3-85a1-e53876dfcd63",
        "isbn": "1400321077",
        "fileName": "1400321077.jpg",
        "imageUrlPath": "http://ecx.images-amazon.com/images/I/51rokQ30-lL.jpg",
        "author": "Catherine Claire Larson",
        "title": "Waiting in Wonder: Growing in Faith While You're Expecting",
        "genre": {
            "id": "5e1e8333-ec91-4ffe-85d2-388a6fefc43b",
            "category": "Parenting & Relationships"
        }
    }
 ```
  __STEP_2__: `"/api/genres"` 
  
  
 Find genres saved in db.
 
 ```
 GET METHOD:   
 http://localhost:8080/api/genres
 ```
 
 __STEP_3__: `"/api/users"`
 
 Save New User
 
  ```
POST METHOD:   
http://localhost:8080/api/users/register
  ```
  
Body content example like the one below (keep in mind genre's id should match those in db):

  ```
  {
          "username": "john_snow",
          "firstName": "John",
          "lastName": "Snow",
          "genrePreferences": [
              {
                  "genre":   {
                      "id": "73224e1c-cccc-40a5-84e6-ba725ce519de",
                      "category": "Science Fiction & Fantasy"
                  }
              },
              {
                  "genre": {
                      "id": "5f8a77b3-cb4c-46a8-9a52-de2ddc6d9247",
                      "category": "Business & Money"
                  }
              },
              {
              	 "genre": {
                      "id": "82ce6c35-f7a7-429d-8d43-7e6ded4f44e8",
                      "category": "Law"
                  }
              },
              
               {
              	"genre": {
                      "id": "e89d51e4-c1af-410d-b267-5dd10845cab5",
                      "category": "Politics & Social Sciences"
                  }
              } ,
              {
              	"genre": {
                      "id": "5e1e8333-ec91-4ffe-85d2-388a6fefc43b",
                      "category": "Parenting & Relationships"
                  }
              }
              
          ]
      }
  ```
  
  Acceptance criteria to register new user:
  
  * `username` not existent.
  * `genrePreferences` should contain at least four genres. Otherwise bad request message is shown.

You can update existent user genre preferences with the same way as save, with endpoint.
  ```
POST METHOD:   
http://localhost:8080/api/users/update
  ```

 __STEP_4__: `"/api/recommendations"`
 
  Find books for recommendation that are relevant to user genre preferences. It should return 20 books.
 ```
 GET METHOD:   
http://localhost:8080/api/recommendations/proposed-books/john_snow
 ```
 
 Find books for recommendation that are rated from other users. It should return 5 books.
```
 GET METHOD:   
 http://localhost:8080/api/recommendations/proposed-books/LIKE/exclude/john_snow
```
 
 
 __STEP_5__: `"/api/feedback"`
 
   ```
 POST METHOD:   
http://localhost:8080/api/feedback
   ```
   
 And body content of post method can be as the below example. NOTE: user & book should exist in db.
  ```
{
	"user": {
            "id": "9bde0147-4d32-479b-bc11-e5e065f6326f",
            "username": "john_snow",
            "firstName": "John",
            "lastName": "Snow"
        },
   "book": {
            "id": "830da977-c29a-43b3-a1c9-d1ce4f7b6d20",
        	"isbn": "881504645",
        	"fileName": "0881504645.jpg",
        	"imageUrlPath": "http://ecx.images-amazon.com/images/I/515kgTKZCuL.jpg",
        	"author": "Bruce Bolnick",
        	"title": "Waterfalls of the White Mountains: 30 Hikes to 100 Waterfalls",
        	"genre": {
            	"id": "9cd0119e-4e4e-4212-a36a-e43054c7a5ef",
            	"category": "Science & Math"
        	}
    },
    "rate": "DISLIKE"
}

 ```
Search feedback by username:
   ```
 Get METHOD:   
http://localhost:8080/api/feedback/john_snow
   ```
   
Search feedback by username & rate:
   ```
 Get METHOD:   
http://localhost:8080/api/feedback/john_snow/LIKE
   ```
   
   
 __Automated Tests:__   

   Test classes are named <className>Test. That is for a class `BookCsvParseService` the test would be `BookCsvParseServiceTest`.
   
   Test methods are named `<methodUnderTest>_<expectedBehavior>_<stateUnderTest>`. 
   Even though refactoring might change the names its still useful to have the method name in there to know immediately what's tested.
   
   Example:
   
   `parseBooksFromCsvFile_ReturnsCorrectFields_WithValidInput`
   With a complicated integration test it make sense to rather describe a broader behavior, but we'll see on a case by case basis.
   
   Organization:
   
   I followed the Given-When-Then or Arrange-Act-Assert patterns inspired from BDD. There are three code blocks with up to 4 lines each. Example:
   * givenUser().authenticated(true).build();
   * val result = service.doAction();
   * assertThat(result.value).isEqualTo(123);
   