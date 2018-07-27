import {Component, OnInit, Output} from '@angular/core';
import {Book} from '../book';
import {BookService} from '../book.service';

/**
 * The component for the list of books in the BookStore
 */
@Component({
    selector: 'app-book',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

    /**
     * Constructor of the component
     * @param bookService The book's services provider
     */
    constructor(private bookService: BookService) { }

    /**
     * The list of books in the BookStore
     */
    books: Book[];

    /**
     * Asks the service to update the list of books
     */
    getBooks(): void {
        this.bookService.getBooks()
            .subscribe(books => this.books = books);
    }

    /**
     * This will initialize the component by retrieving the list of books from the service
     * This method will be called when the component is created
     */
    ngOnInit() {
        this.getBooks();
    }
}




