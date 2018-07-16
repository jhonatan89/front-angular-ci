import {Injectable} from '@angular/core';
import {Author} from './author';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/concatMap';
import 'rxjs/add/observable/combineLatest';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable()
export class AuthorService {

    constructor(private http: HttpClient) {}

    authors: Author[];

    getAuthors(): Observable<Author[]> {

        const authorsObservable = this.http.get<Author[]>("../assets/authors.json");
        authorsObservable.subscribe(authors => this.authors = authors);
        return authorsObservable;
    }
}
