import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './routing-module/app-routing.module';
import {AuthorModule} from './author/authors-list/author-list.module';
import {BookModule} from './book/book-list/book-list.module';
import {EditorialModule} from './editorial/editorials-list/editorial-list.module';
import {FormsModule} from '@angular/forms';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        AuthorModule,
        BookModule,
        EditorialModule,
        FormsModule
    ],

    bootstrap: [AppComponent]
})
export class AppModule {}
