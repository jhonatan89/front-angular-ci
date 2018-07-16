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

import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link AuthorDTO} para manejar las relaciones entre
 * los AuthorDTO y otros DTOs. Para conocer el
 * contenido de un Autor vaya a la documentacion de {@link AuthorDTO}
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name: string,
 *      "birthDate": date,
 *      "image": string,
 *      "books": [{@link BookDTO}]
 *   }
 * </pre>
 * Por ejemplo un autor se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 1,
 *      "name: "Gabriel García Márquez",
 *      "birthDate": "23091935",
 *      "image": "mifoto.com",
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
public class AuthorDetailDTO extends AuthorDTO {

    private List<BookDTO> books;

    public AuthorDetailDTO() {
        super();
    }

    /**
     * Crea un objeto AuthorDetailDTO a partir de un objeto AuthorEntity
     * incluyendo los atributos de AuthorDTO.
     *
     * @param entity Entidad AuthorEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public AuthorDetailDTO(AuthorEntity entity) {
        super(entity);
        if (entity != null) {
            books = new ArrayList<>();
            for (BookEntity entityBooks : entity.getBooks()) {
                books.add(new BookDTO(entityBooks));
            }

        }

    }

    /**
     * Convierte un objeto AuthorDetailDTO a AuthorEntity incluyendo los
     * atributos de AuthorDTO.
     *
     * @return Nueva objeto AuthorEntity.
     *
     */
    @Override
    public AuthorEntity toEntity() {
        AuthorEntity entity = super.toEntity();
        if (books != null) {
            List<BookEntity> booksEntity = new ArrayList<>();
            for (BookDTO dtoBook : books) {
                booksEntity.add(dtoBook.toEntity());
            }
            entity.setBooks(booksEntity);
        }

        return entity;
    }

    /**
     * Obtiene la lista de libros del autor
     * @return the books
     */
    public List<BookDTO> getBooks() {
        return books;
    }

    /**
     * Modifica la lista de libros para el autor
     * @param books the books to set
     */
    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

}
