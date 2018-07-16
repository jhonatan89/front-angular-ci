import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AuthorService} from './author.service';
import {AuthorComponent} from './author.component';
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
        AuthorComponent
    ],
    providers: [AuthorService],
    bootstrap: [AuthorComponent]
})
export class AuthorModule {}
