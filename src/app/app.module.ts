import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './routing-module/app-routing.module';
import {AuthorModule} from './author/author.module';
import {BookModule} from './book/book.module';
import {EditorialModule} from './editorial/editorial.module';
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
