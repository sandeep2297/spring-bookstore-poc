# spring-bookstore-poc

BookStore Assignment made with Java 17 and Spring Boot 3.0

In order to run the application go into the project path and run
mvn: spring-boot:run

In order to signup on the application use:

POST: http://localhost:8080/api/auth/signup
with the following JSON body:

{
"fullName":"fullName",
"userName":"emailId",
"password": "password"
}

In order to login on the application use:

POST: http://localhost:8080/api/auth/login
with the following JSON body:

{
"userName":"emailId",
"password": "password"
}

In order to create book details:

1) Need to create author details

POST: http://localhost:8080/api/store/author-details
with the following JSON body along
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)
Sample Request Body:
{
    "name":"XYZ",
    "birthDate": "1997-01-22"
}

2) Create Book Details
POST: http://localhost:8080/api/store/book-details
with the following JSON body along
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)
Sample Request Body:
{
    "title":"Best Book",
    "publishYear": 2024, 
    "price": 1000,
    "genre":"fiction",
    "authorIdList": [1]
}


In order to get book with author details by book title:

GET: http://localhost:8080/api/store/book-details?bookTitle={bookTitle}
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)

In order to get author with book details by author name:

GET: http://localhost:8080/api/store/author-details?authorName={authorName}
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)

In order to update book details:

PUT: http://localhost:8080/api/store/book-details
with the following JSON body along
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)
Sample Request Body :
{
    "title":"Best Book",
    "bookIsbn": "fde5beac-4fb1-4327-9e9c-2505a1f6d5ee",
    "publishYear": 2024, 
    "price": 1300,
    "genre":"fiction",
    "authorIdList": [1]
}

In order to delete particular book details:

DELETE: http://localhost:8080/api/store/book-details/{bookId}
with Authorization Header from Postman:
Authorization: Bearer JWT_TOKEN(generated from the first two calls i.e /signup and /login)

In order to access the in memory database h2
please hit on the browser
http://localhost:8080/api/h2-console
dbName: bookStoreDB
username: sa
password: (blank) (no-need to fill)
and run the following queries

select * from TBL_USER;

select * from TBL_AUTHOR;

select * from TBL_BOOK;

select * from TBL_STORE_MAPPING;