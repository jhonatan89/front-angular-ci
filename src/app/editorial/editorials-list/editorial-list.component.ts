import {Component, OnInit} from '@angular/core';
import {Editorial} from '../editorial';
import {EditorialService} from '../editorial.service';

/**
 * The component for the list of editorials in the BookStore
 */
@Component({
    selector: 'app-editorial',
    templateUrl: './editorial-list.component.html',
    styleUrls: ['./editorial-list.component.css']
})
export class EditorialListComponent implements OnInit {

    /**
     * Constructor for the component
     * @param editorialService The author's services provider
     */
    constructor(private editorialService: EditorialService) { }
    
    /**
     * The list of editorials which belong to the BookStore
     */
    editorials: Editorial[];

    /**
     * Asks the service to update the list of editorials
     */
    getEditorials(): void {
        this.editorialService.getEditorials()
            .subscribe(editorials => this.editorials = editorials);
    }

    /**
     * This will initialize the component by retrieving the list of editorials from the service
     * This method will be called when the component is created
     */
    ngOnInit() {
        this.getEditorials();
    }
}


