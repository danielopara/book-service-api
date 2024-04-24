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
- `id` : `Long` The unique identify of every token.
- `token` : `String` The token is produced after the user logs in.
- `creation` : `Date` The creation date is extracted from the token.
- `expiry`: `Date` The expiration date is extracted from the token.

### Book
This is the book entity.
- `id` : `Long` The unique identify of every book.
- `title`: `String` The title of the book. 
- `author`: `String` The author of the book.
- `genre`: `String` The genre of the book.
- `amount`: `BigDecimal` The amount of the book
- `description`: `String` The description of the book.
- `ratings`: `Double` The ratings of the book.
- `publicationDate`: `Date` The data the book was published.
- `quantityInStock`: `Integer` The quantity of the book in stock.

### Review
Reviews from the user on books
- `id`: `Long`
- `review`: `String` The review text.
- `rating`: `Double` The rating for the book.
- `email`: `String` The email of the user that made the review.
- `book`

### User
User entity
- `id` : Long
- `firstName`
- `lastName`
- `email`
- `password`
- `role`
- `phoneNumber`

### WishList
Adding books to wish list
- `id` : Long
- `email`
- `book id`