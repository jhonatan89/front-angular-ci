import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditorialListComponent} from './editorials-list/editorial-list.component';
import {EditorialService} from './editorial.service';
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
    declarations: [EditorialListComponent],
    providers: [EditorialService]
})
export class EditorialModule {}
