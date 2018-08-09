import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {Editorial} from './editorial';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

const API_URL = environment.apiURL;

/**
* The service provider for everything related to editorials
*/
@Injectable()
export class EditorialService {
    
    /**
    * Constructor of the service
    * @param http The HttpClient - This is necessary in order to perform requests
    */
    constructor(private http: HttpClient) { }
    
    /**
    * The list of editorials
    */
    editorials: Editorial[];
    
    /**
    * Returns the Observable object containing the list of editorials retrieved from the API
    * @returns The list of books in real time
    */
    getEditorials(): Observable<Editorial[]> {
        return this.http.get<Editorial[]>(API_URL + '/editorials/allEditorials');
    }
    
}
