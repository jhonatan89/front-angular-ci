import {Component, OnInit} from '@angular/core';
import {Editorial} from './editorial';
import {EditorialService} from './editorial.service';

@Component({
    selector: 'app-editorial',
    templateUrl: './editorial.component.html',
    styleUrls: ['./editorial.component.css']
})
export class EditorialComponent implements OnInit {

    // se declara el servicio de book en el constructor
    constructor(private editorialService: EditorialService) {}
    // se declara una variable de tipo lista de libros para guardar los datos obtenidos por el 
    // metodo getEditorials() del servicio
    editorials: Editorial[];
    // se crea un metodo local getEditorials para utilizarlo en la funcion de inicializacion onInit 
    getEditorials(): void {
        this.editorialService.getEditorials()
            .subscribe(editorials => this.editorials = editorials);
    }
    ngOnInit() {
        // al inicializarse el componente siempre se llamara el metodo local getEditorials()
        this.getEditorials();
    }
}


