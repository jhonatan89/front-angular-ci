import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BookService} from './book.service';
import {BookListComponent} from './book-list/book-list.component';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '../routing-module/app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';


@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        CommonModule,
        FormsModule
    ],
    declarations: [
        BookListComponent
    ],
    providers: [BookService],
    bootstrap: [BookListComponent]
})
export class BookModule {}
