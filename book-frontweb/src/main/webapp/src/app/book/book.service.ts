import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Book} from './book';
import {HttpClient} from '@angular/common/http';

/**
 * The service provider for everything related to books
 */
@Injectable()
export class BookService {

    /**
     * Constructor of the service
     * @param http The HttpClient - This is necessary in order to perform requests
     */
    constructor(private http: HttpClient) { }

    /**
     * The list of books
     */
    books: Book[];
   
    /**
     * Returns the Observable object which will always be looking 
     * for modifications in the list of books
     * @returns The list of books in real time
     */
    getBooks(): Observable<Book[]> {
        const booksObservable = this.http.get<Book[]>("../assets/books.json");
        booksObservable.subscribe(books => this.books = books);
        return booksObservable;
    }
}
