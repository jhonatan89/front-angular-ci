import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {Book} from './book';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const API_URL = environment.apiURL;

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
    * Returns the Observable object containing the list of books retrieved from the API
    * @returns The list of books in real time
    */    
    getBooks(): Observable<Book[]> {
        return this.http.get<Book[]>(API_URL + '/books/allBooks');
    }
}
