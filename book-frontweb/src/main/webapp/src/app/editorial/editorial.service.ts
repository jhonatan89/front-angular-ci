import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Editorial} from './editorial';
import {Observable} from 'rxjs/Observable';

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
     * Returns the Observable object which will always be looking 
     * for modifications in the list of editorials
     * @returns The list of books in real time
     */
    getEditorials(): Observable<Editorial[]> {
        const booksObservable = this.http.get<Editorial[]>("../assets/editorials.json");
        booksObservable.subscribe(editorials => this.editorials = editorials);
        return booksObservable;
    }

}
