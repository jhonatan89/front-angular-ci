import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { AppRoutingModule } from './routing-module/app-routing.module';
import { APP_BASE_HREF } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BookListComponent } from './book/book-list/book-list.component';
import { AuthorListComponent } from './author/authors-list/author-list.component';
import { EditorialListComponent } from './editorial/editorials-list/editorial-list.component';

describe('AppComponent', () => {
    let component: AppComponent;
    let fixture: ComponentFixture<AppComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [ AppRoutingModule, HttpClientModule ],
            declarations: [ AppComponent, BookListComponent, AuthorListComponent, EditorialListComponent ],
            providers: [{provide: APP_BASE_HREF, useValue: ''} ]
        })
        .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(AppComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the app', async(() => {
        expect(component).toBeTruthy();
    }));
    
    it(`should have as title 'Bookstore'`, async(() => {
        const app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual(component.title);
    }));
    
    it('should render titles in the navbar', async(() => {
        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('#booksTag').textContent).toContain('Books');
        expect(compiled.querySelector('#authorsTag').textContent).toContain('Authors');
        expect(compiled.querySelector('#editorialTag').textContent).toContain('Editorials');
    }));
});

