import {Component, OnInit} from '@angular/core';
import {AuthorService} from './author.service';
import {Author} from './author';

@Component({
    selector: 'app-author',
    templateUrl: './author.component.html',
    styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

    // se declara el servicio de autor en el constructor
    constructor(private authorService: AuthorService) {

    }
    // se declara una variable de tipo lista de autor para guardar los datos obtenidos por el 
    // metodo getAuthors() del servicio
    authors: Author[];

    // se crea un metodo local getAuthors para utilizarlo en la funcion de inicializacion onInit
    getAuthors(): void {
        // llamada al servicio de autor getAuthors
        this.authorService.getAuthors()
            .subscribe(authors => this.authors = authors);
    }

    ngOnInit() {
        // al inicializarse el componente siempre se llamara el metodo local getAuthors
        this.getAuthors();
    }

}
