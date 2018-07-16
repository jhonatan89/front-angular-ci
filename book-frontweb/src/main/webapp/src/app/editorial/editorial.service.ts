import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Editorial} from './editorial';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class EditorialService {
   
    constructor(private http: HttpClient) {
       
    }
    editorials: Editorial[];

    getEditorials(): Observable<Editorial[]> {
        const booksObservable = this.http.get<Editorial[]>("../assets/editorials.json");
        booksObservable.subscribe(editorials => this.editorials = editorials);
        return booksObservable;
    }

}
