/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.dtos;

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link EditorialDTO} para manejar las relaciones entre
 * los Editorial JSON y otros DTOs. Para conocer el
 * contenido de la una Editorial vaya a la documentacion de {@link EditorialDTO}
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name: string,
 *      "books": [{@link BookDTO}]
 *   }
 * </pre>
 * Por ejemplo una editorial se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id" : 1, 
 *      "name" : "Plaza y Janes",
 *      "books" : [
 *          {
 *              "id" : 1,
 *              "name" : "Cien años de soledad",
 *              "description": "El libro se compone de 20 capítulos no titulados, en los cuales se narra una historia con una estructura cíclica temporal, puesto que los acontecimientos del pueblo y de la familia Buendía, así como los nombres de los personajes, se repiten una y otra vez, fusionando la fantasía con la realidad.",
 *              "isbn" : "0307474720",
 *              "image" : "http://goo.gl/IWNdCX",
 *              "publishDate" : "01071967"
 *          },
 *          {
 *              "id" : 2,
 *              "name" : "El coronel no tiene quien le escriba",
 *              "description" : "...",
 *              "isbn" : "0307474721",
 *              "image" : "http://goo.gl/IWNdCX",
 *              "publishDate" : "01071967"
 *          }
 *      ]
 *   }
 *
 * </pre>
 * @author ISIS2603
 */
public class EditorialDetailDTO extends EditorialDTO {

    /*
    * Esta lista de tipo BookDTO contiene los books que estan asociados a una editorial
     */
    private List<BookDTO> books;

    /**
     * Constructor por defecto
     */
    public EditorialDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity La entidad de la editorial para transformar a DTO.
     */
    public EditorialDetailDTO(EditorialEntity entity) {
        super(entity);
        if (entity != null) {
            if (entity.getBooks() != null) {
                books = new ArrayList<>();
                for (BookEntity entityBook : entity.getBooks()) {
                    books.add(new BookDTO(entityBook));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO de la editorial para transformar a Entity
     */
    @Override
    public EditorialEntity toEntity() {
        EditorialEntity editorialE = super.toEntity();
        if (books != null) {
            List<BookEntity> booksEntity = new ArrayList<>();
            for (BookDTO dtoBook : books) {
                booksEntity.add(dtoBook.toEntity());
            }
            editorialE.setBooks(booksEntity);
        }
        return editorialE;
    }

    /**
     * Devuelve la lista de libros de la editorial.
     * @return the books
     */
    public List<BookDTO> getBooks() {
        return books;
    }

    /**
     * Modifica la lista de libros de la editorial.
     * @param books the books to set
     */
    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

}
