# Book Service App API Documentation.

## Purpose 
This service for managing books in a book store.

## Requirements
- Java 8 or higher
- Spring Boot 3.x
- Maven
- A relational database (MySQL)

## Schemas
### Auth
For Storing the JWT, creation date and expiry date. For tracking the tokens that was created 
- `id` :
- `token` :
- `creation` :
- `expiry`:

### Book
This is the book entity.
- `id`
- `title`
- `author`
- `genre`
- `amount`
- `description`
- `ratings`
- `publicationDate`
- `quantityInStock`

### Review
Reviews from the user on books
- `id`
- `review`
- `rating`
- `email`
- `book`

### User
User entity
- `id`
- `firstName`
- `lastName`
- `email`
- `password`
- `role`
- `phoneNumber`

### WishList
Adding books to wish list
- `id`
- `email`
- `book id`