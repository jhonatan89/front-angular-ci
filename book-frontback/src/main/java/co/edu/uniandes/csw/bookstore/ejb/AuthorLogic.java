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
package co.edu.uniandes.csw.bookstore.ejb;

import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.persistence.AuthorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Autthor.
 * @author ISIS2603
 */
@Stateless
public class AuthorLogic {

    private static final Logger LOGGER = Logger.getLogger(AuthorLogic.class.getName());

    @Inject
    private AuthorPersistence persistence;

    @Inject
    private BookLogic bookLogic;

    /**
     * Obtiene la lista de los registros de Author.
     *
     * @return Colección de objetos de AuthorEntity.
     */
    public List<AuthorEntity> getAuthors() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Author a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de AuthorEntity con los datos del Author consultado.
     */
    public AuthorEntity getAuthor(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un autor con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Author en la base de datos.
     *
     * @param entity Objeto de AuthorEntity con los datos nuevos
     * @return Objeto de AuthorEntity con los datos nuevos y su ID.
     */
    public AuthorEntity createAuthor(AuthorEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un autor ");
        
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Author.
     *
     * @param entity Instancia de AuthorEntity con los nuevos datos.
     * @return Instancia de AuthorEntity con los datos actualizados.
     */
    public AuthorEntity updateAuthor(AuthorEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un autor ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Author de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteAuthor(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor ");
        persistence.delete(id);
    }

    /**
     * Obtiene una colección de instancias de BookEntity asociadas a una
     * instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @return Colección de instancias de BookEntity asociadas a la instancia de
     * Author
     */
    public List<BookEntity> listBooks(Long authorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros del autor con id = {0}", authorId);
        return getAuthor(authorId).getBooks();
    }

    /**
     * Obtiene una instancia de BookEntity asociada a una instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     * @return La entidadd de Libro del autor
     */
    public BookEntity getBook(Long authorId, Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un libro con id = {0}", booksId);
        List<BookEntity> list = getAuthor(authorId).getBooks();
        BookEntity booksEntity = new BookEntity();
        booksEntity.setId(booksId);
        int index = list.indexOf(booksEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Book existente a un Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     * @return Instancia de BookEntity que fue asociada a Author
     */
    public BookEntity addBook(Long authorId, Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al author con id = {0}", authorId);
        bookLogic.addAuthor(booksId, authorId);
        return bookLogic.getBook(booksId);
    }

    /**
     * Remplaza las instancias de Book asociadas a una instancia de Author
     *
     * @param authorId Identificador de la instancia de Author
     * @param list Colección de instancias de BookEntity a asociar a instancia
     * de Author
     * @return Nueva colección de BookEntity asociada a la instancia de Author
     */
    public List<BookEntity> replaceBooks(Long authorId, List<BookEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los libros asocidos al author con id = {0}", authorId);
        AuthorEntity authorEntity = getAuthor(authorId);
        List<BookEntity> bookList = bookLogic.getBooks();
        for (BookEntity book : bookList) {
            if (list.contains(book)) {
                if (!book.getAuthors().contains(authorEntity)) {
                    bookLogic.addAuthor(book.getId(), authorId);
                }
            } else {
                bookLogic.removeAuthor(book.getId(), authorId);
            }
        }
        authorEntity.setBooks(list);
        return authorEntity.getBooks();
    }

    /**
     * Desasocia un Book existente de un Author existente
     *
     * @param authorId Identificador de la instancia de Author
     * @param booksId Identificador de la instancia de Book
     */
    public void removeBook(Long authorId, Long booksId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un libro del author con id = {0}", authorId);
        bookLogic.removeAuthor(booksId, authorId);
    }
}
