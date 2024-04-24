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
- `book`: `Book` It contains the book class. Stores as foreign key.

### User
User entity
- `id` : `Long` The unique identifier of users.
- `firstName`: `String` The first name of a user.
- `lastName`: `String` The last name of a user.
- `email`: `String` The email of the user.
- `password`: `String` The password of the user which is encrypted.
- `role`: `ROLE` This is an Enum. It can be ADMIN or USER.
- `phoneNumber`: `String` The phone number of the user.

### WishList
Adding books to wish list
- `id` : `Long` The unique identifier of the wishlist.
- `email`: `String` The email of the user.
- `book id`: `Book` The book class