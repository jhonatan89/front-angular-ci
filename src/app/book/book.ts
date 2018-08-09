/**
* This class represents a book of the BookStore. 
* It contains all the information relevant to the book.
*/
export class Book {
    /**
    * The book's id
    */
    id: number;

    /**
    * The book's name
    */
    name: string;

    /**
    * The book's ISBN
    */
    isbn: string;

    /**
    * A brief summary of the book
    */
    description: string;

    /**
    * The location of the book's image
    */
    image: string;
}
