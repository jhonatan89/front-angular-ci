import {NgModule, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {BookListComponent} from '../book/book-list.component';
import {AuthorComponent} from '../author/author.component';
import {EditorialComponent} from '../editorial/editorial.component';


const routes: Routes = [

    {
        path: 'books',
        component: BookListComponent,
    },
    {
        path: 'authors',
        component: AuthorComponent
    },
    {
        path: 'editorials',
        component: EditorialComponent,
    }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule],
    declarations: []
})
export class AppRoutingModule {

}
