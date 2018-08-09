import {NgModule, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {BookListComponent} from '../book/book-list/book-list.component';
import {AuthorListComponent} from '../author/authors-list/author-list.component';
import {EditorialListComponent} from '../editorial/editorials-list/editorial-list.component';


const routes: Routes = [

    {
        path: 'books',
        component: BookListComponent,
    },
    {
        path: 'authors',
        component: AuthorListComponent
    },
    {
        path: 'editorials',
        component: EditorialListComponent,
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
