# FakeStore API Integration.

This project demonstrates how to integrate and interact with the FakeStore API to retrieve and manage product data in spring boot.

# Overview

The FakeStore API is a RESTful API that provides endpoints for fetching product details, categories, user and more. This project showcases how to consume these APIs to retrieve products,cart,user information and perform basic CRUD operations.This project guides you to understand how to consume external API in Spring Boot.

# To Know more about Fake store API read the documentation.[fakestore](https://fakestoreapi.com/docs)

# Features
## API Listing.
### Product
- Fetch all product.
   #### HTTP
        GET /products/all HTTP/1.1
        Host: localhost:8080
  
- Fetch single product.
  #### HTTP
      GET /products/4 HTTP/1.1
      Host: localhost:8080
- Fetch limited product.
  #### HTTP
      GET /products?limit=6  HTTP/1.1
      Host: localhost:8080
- Fetch product in specific category.
  #### HTTP
      GET /products/category/electronics HTTP/1.1
      Host: localhost:8080
- Fetch all categories.
  #### HTTP
      GET /products/categories HTTP/1.1
      Host: localhost:8080
- Add new product.
  #### HTTP
      POST /products/add HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 375
      
      {
      "title": "test product",
      "price": 13.5,
      "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam hendrerit nisi sed sollicitudin pellentesque. Nunc posuere purus rhoncus pulvinar aliquam. Ut aliquet tristique nisl vitae volutpat. Nulla aliquet porttitor venenatis.",
      "image": "https://i.pravatar.cc",
      "category": "electronic"
      }
- Update a product.
  #### HTTP
      PUT /products/7 HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 163

      {
      "title": "Lorem ipsum",
      "price": "13.5",
      "description": "lorem ipsum set",
      "image": "https://i.pravatar.cc",
      "category": "electronic"
      }
- Delete a product.
  #### HTTP
      DELETE /products/9 HTTP/1.1
      Host: localhost:8080
### Cart
- Fetch a cart.
  #### HTTP
      GET /carts/-5 HTTP/1.1
      Host: localhost:8080
- Fetch all carts.
  #### HTTP
      GET /carts/all HTTP/1.1
      Host: localhost:8080
- Fetch limited carts.
  #### HTTP
      GET /carts/limit?limit=5 HTTP/1.1
      Host: localhost:8080
- Fetch cart in sorted order.
  #### HTTP
      GET /carts/sort?sort= HTTP/1.1
      Host: localhost:8080
- Fetch carts of a user.
  #### HTTP
      GET /carts/user/2 HTTP/1.1
      Host: localhost:8080
- Add a new cart.
  #### HTTP
      POST /carts/add HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 232

      {
        "userId": 5,
        "date": "2020-02-03",
        "products": [
          {
            "productId": 5,
            "quantity": 1
        },
        {
            "productId": 1,
            "quantity": 5
        }
        ]
      }
- Update a cart.
  #### HTTP
      PUT /carts/update/7 HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 153

      {
        "userId": 3,
        "date": "2019-12-10",
      "products": [
        {
            "productId": 1,
            "quantity": 3
        }
      ]
      }
- Delete a cart.
  #### HTTP
      DELETE /carts/delete/6 HTTP/1.1
      Host: localhost:8080
- Fetch carts in date range.
  #### HTTP
      GET /carts/range/date?startDate=2019-12-10&endDate=2020-10-10 HTTP/1.1
      Host: localhost:8080
### User
- Fetch all user.
  #### HTTP
      GET /users/all HTTP/1.1
      Host: localhost:8080
- Fetch a single user.
  #### HTTP
      GET /users/3 HTTP/1.1
      Host: localhost:8080
- Fetch limited user.
  #### HTTP
      GET /users/limit/5 HTTP/1.1
      Host: localhost:8080
- Fetch users in sorted order.
  #### HTTP
      GET /users/sort?sort=desc HTTP/1.1
      Host: localhost:8080
- Add a user.
  #### HTTP
      POST /users/add HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 448

      {
        "email": "John@gmail.com",
        "username": "johnd",
        "password": "m38rmF$",
        "name": {
            "firstname": "John",
            "lastname": "Doe"
        },
        "address": {
            "city": "kilcoole",
            "street": "7835 new road",
            "number": 3,
            "zipcode": "12926-3874",
            "geolocation": {
              "lat": "-37.3159",
              "long": "81.1496"
            }
        },
      "phone": "1-570-236-7033"
      }
- Update a user.
  #### HTTP
      PUT /users/update/7 HTTP/1.1
      Host: localhost:8080
      Content-Type: application/json
      Content-Length: 448
      {
          "email": "John@gmail.com",
          "username": "johnd",
          "password": "m38rmF$",
          "name": {
            "firstname": "John",
            "lastname": "Doe"
          },
          "address": {
            "city": "kilcoole",
            "street": "7835 new road",
            "number": 3,
            "zipcode": "12926-3874",
            "geolocation": {
              "lat": "-37.3159",
              "long": "81.1496"
            }
        },
        "phone": "1-570-236-7033"
      }
- Delete a user.
  #### HTTP
      DELETE /users/delete/6 HTTP/1.1
      Host: localhost:8080
## Requirements

To run this project locally, you'll need the following:

- Java programming language used.
- maven buiding tool used.
- spring boot framework used.
- IDE IntelliJ IDEA to download visit --> [IntelliJ IDEA](https://www.jetbrains.com/idea/)


## Setup and Installation

1. Clone this repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd apisymphony`
4. Install dependencies: `mvn clean install OR mvn clean install -U`
5. buid and run the project
6. After tomcat started successfully. go to postman and hit above apis.

## Usage
 ### This project demonstrates how to call or consume an external API in Spring Boot.
