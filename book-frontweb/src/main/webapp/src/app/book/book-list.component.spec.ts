import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AppRoutingModule } from '../routing-module/app-routing.module';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { BookListComponent } from './book-list.component';
import { AuthorComponent } from '../author/author.component';
import { EditorialComponent } from '../editorial/editorial.component';

import {BookService} from './book-list.service';

import { Book } from './book';

describe('BookListComponent', () => {
    let component: BookListComponent;
    let fixture: ComponentFixture<BookListComponent>;
    const books: Book[] = require('../../assets/books.json');
    
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [ AppRoutingModule, HttpClientModule ],
            declarations: [ BookListComponent, AuthorComponent, EditorialComponent ],
            providers: [{provide: APP_BASE_HREF, useValue: ''}, BookService ]
        })
        .compileComponents();
    }));
    
    beforeEach(() => {
        fixture = TestBed.createComponent(BookListComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });
    
    
    it('should create', () => {
        expect(component).toBeTruthy();
    });
    
    
    it('should have a list of books', () => {
        component.books = books;
        expect(component.books.length).toEqual(books.length);
    });

    it('a book should be a book (first and last)', () => {
        component.books = books;
        expect(component.books[0].name).toEqual(books[0].name);
        expect(component.books[books.length - 1].name).toEqual(books[books.length - 1].name);
    });
});
