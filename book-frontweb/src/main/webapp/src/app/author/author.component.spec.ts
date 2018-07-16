import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppRoutingModule } from '../routing-module/app-routing.module';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { BookListComponent } from '../book/book-list.component';
import { AuthorComponent } from './author.component';
import { EditorialComponent } from '../editorial/editorial.component';

import { Author } from './author';

import { AuthorService } from './author.service';

describe('AuthorComponent', () => {
    let component: AuthorComponent;
    let fixture: ComponentFixture<AuthorComponent>;
    const authors: Author[] = require('../../assets/authors.json');

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [ AppRoutingModule, HttpClientModule ],
            declarations: [ BookListComponent, AuthorComponent, EditorialComponent ],
            providers: [{provide: APP_BASE_HREF, useValue: ''}, AuthorService ]
        })
        .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AuthorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
    
    it('should have a list of authors', () => {
        component.authors = authors;
        expect(component.authors.length).toEqual(authors.length);
    });

    it('an author should be an author (first and last)', () => {
        component.authors = authors;
        expect(component.authors[0].birthDate).toEqual(authors[0].birthDate);
        expect(component.authors[authors.length - 1].birthDate).toEqual(authors[authors.length - 1].birthDate);
    });
    
});