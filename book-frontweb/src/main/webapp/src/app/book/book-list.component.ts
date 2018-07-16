import {Component, OnInit, Output} from '@angular/core';
import {Book} from './book';
import {BookService} from './book-list.service';


@Component({
    selector: 'app-book',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {


    // se declara el servicio de book en el constructor
    constructor(private bookService: BookService) {
    }

    // se declara una variable de tipo lista de libros para guardar los datos obtenidos por el 
    // metodo getBooks() del servicio
    books: Book[];

    // se crea un metodo local getBooks para utilizarlo en la funcion de inicializacion onInit 
    getBooks(): void {
        this.bookService.getBooks()
            .subscribe(books => this.books = books);
    }
    ngOnInit() {
        // al inicializarse el componente siempre se llamara el metodo local getBooks()
        this.getBooks();
    }
}




