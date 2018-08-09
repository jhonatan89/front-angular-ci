import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {Author} from './author';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const API_URL = environment.apiURL;

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
     * Returns the Observable object containing the list of authors retrieved from the API
     * @returns The list of authors in real time
     */
    getAuthors(): Observable<Author[]> {
        return this.http.get<Author[]>(API_URL + '/authors/allAuthors');
    }
}
