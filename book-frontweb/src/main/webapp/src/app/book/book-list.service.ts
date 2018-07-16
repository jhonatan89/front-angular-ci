import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Book} from './book';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class BookService {

    constructor(private http: HttpClient) {
      
    }
    books: Book[];
   
    getBooks(): Observable<Book[]> {
        const booksObservable = this.http.get<Book[]>("../assets/books.json");
        booksObservable.subscribe(books => this.books = books);
        return booksObservable;
    }
}
