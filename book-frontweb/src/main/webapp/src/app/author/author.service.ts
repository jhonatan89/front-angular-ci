import {Injectable} from '@angular/core';
import {Author} from './author';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/concatMap';
import 'rxjs/add/observable/combineLatest';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

/**
 * The service provider for everything related to authors
 */
@Injectable()
export class AuthorService {

    /**
     * Constructor of the service
     * @param http The HttpClient - This is necessary in order to perform requests
     */
    constructor(private http: HttpClient) {}

    /**
     * The list of authors
     */
    authors: Author[];

    /**
     * Returns the Observable object which will always be looking 
     * for modifications in the list of authors
     * @returns The list of authors in real time
     */
    getAuthors(): Observable<Author[]> {
        const authorsObservable = this.http.get<Author[]>("../assets/authors.json");
        authorsObservable.subscribe(authors => this.authors = authors);
        return authorsObservable;
    }
}
