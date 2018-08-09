import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppRoutingModule } from '../../routing-module/app-routing.module';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { BookListComponent } from '../../book/book-list/book-list.component';
import { AuthorListComponent } from '../../author/authors-list/author-list.component';
import { EditorialListComponent } from './editorial-list.component';

import { Editorial } from '../editorial';

import { EditorialService } from '../editorial.service';

describe('EditorialComponent', () => {
    let component: EditorialListComponent;
    let fixture: ComponentFixture<EditorialListComponent>;
    const editorials: Editorial[] = require('../../../assets/editorials.json');

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [ AppRoutingModule, HttpClientModule ],
            declarations: [ BookListComponent, AuthorListComponent, EditorialListComponent ],
            providers: [{provide: APP_BASE_HREF, useValue: ''}, EditorialService ]
        })
        .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(EditorialListComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
    
    it('should have a list of authors', () => {
        component.editorials = editorials;
        expect(component.editorials.length).toEqual(editorials.length);
    });

    it('an author should be an author (first and last)', () => {
        component.editorials = editorials;
        expect(component.editorials[0].description).toEqual(editorials[0].description);
        expect(component.editorials[editorials.length - 1].description).toEqual(editorials[editorials.length - 1].description);
    });
    
});