# REST API books application

## Run the app

yarn start

## Run the tests

yarn test

# REST API

The REST API to the example app is described below.

## Status Codes

| Status Code | Description             |
| :---------- | :---------------------- |
| 200         | `OK`                    |
| 201         | `CREATED`               |
| 400         | `BAD REQUEST`           |
| 404         | `NOT FOUND`             |
| 500         | `INTERNAL SERVER ERROR` |

## Login App

### Request

`POST /auth/login`

`Authorization: Basic xxxxx`

### Response

- HTTP/1.1 200 OK
- Content-Type: application/json

### Response Error

- HTTP/1.1 401
- Content-Type: application/json

`{ "message": "Error User/Password" }`

## Logout App

### Request

`POST /books`

### Response

- HTTP/1.1 204 OK
- Content-Type: application/json

`{ "code": "B93A79A6-9ED0-4286-9F46-53A8686FD009", "name": "Book name", "price": 1990, "image": "/tmp/book_store/images/ccee08d1_5e04_4da5_833b_7464ef286637.png", "author": "Author name" }`

### Response Error

- HTTP/1.1 400
- Content-Type: application/json

`{ "errors": [ { "parameter": "name", "message": "must have value" }, { "parameter": "price", "message": "must have value" } ] }`

## Get list of Books

### Request

`GET /books?page=0&size=5`

### Response

- HTTP/1.1 200 OK
- Content-Type: application/json

`{ "page": 0, "count": 0, "books": [] }`

## Create a new Book

### Request

`POST /auth/logout`

### Response

- HTTP/1.1 204 OK
- Content-Type: application/json

### Request## Get list of Books

### Request

`GET /books`

### Response

- HTTP/1.1 200 OK
- Content-Type: application/json

`{ "page": 0, "count": 0, "books": [] }`

- HTTP/1.1 201 OK
- Content-Type: application/json

### Response

`{ "code": "B93A79A6-9ED0-4286-9F46-53A8686FD009", "name": "Book name", "price": 1990, "image": "/tmp/book_store/images/ccee08d1_5e04_4da5_833b_7464ef286637.png", "author": "Author name" }`

### Response Error

- HTTP/1.1 400
- Content-Type: application/json

`{ "errors": [ { "parameter": "name", "message": "must have value" }, { "parameter": "price", "message": "must have value" } ] }`

- HTTP/1.1 404
- Content-Type: application/json

`{ "message": "Author not found" }`

## Update a Book

### Request

`PUT /books/{id}`

- HTTP/1.1 200 OK
- Content-Type: application/json

### Response

`{ "code": "1C6AACF8-60F2-4688-A8F7-379CBC324B7A", "name": "Numero1", "price": 123, "image": "/tmp/book_store/images/748af67e_d797_48e0_9824_72c6b2fa0a50.png", "author": "Thiago Henrique Nunes" }`

### Response Error

- HTTP/1.1 404
- Content-Type: application/json

`{ "message": "Book not found" }`

## Delete a Book

### Request

`DELETE /books/{id}`

- HTTP/1.1 204 OK

### Response Error

- HTTP/1.1 404
- Content-Type: application/json

`{ "message": "Book not found" }`

## Update a Book Price

### Request

`PATCH /books/{id}`

- HTTP/1.1 200 OK
- Content-Type: application/json

### Response

`{ "code": "1C6AACF8-60F2-4688-A8F7-379CBC324B7A", "name": "Numero1", "price": 123, "image": "/tmp/book_store/images/748af67e_d797_48e0_9824_72c6b2fa0a50.png", "author": "Thiago Henrique Nunes" }`

### Response Error

- HTTP/1.1 404
- Content-Type: application/json

`{ "message": "Book not found" }`

## Get list of Authors

### Request

`GET /authors?page=0&size=5`

### Response

- HTTP/1.1 200 OK
- Content-Type: application/json

`{ "page": 0, "count": 0, "authors": [] }`
