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
package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.dtos.BookDetailDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.bookstore.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "books".
 * URL: /api/books
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "books".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author ISIS2603
 * @version 1.0
 */
@Path("books")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BookResource {

    @Inject
    BookLogic bookLogic;

    /**
     * <h1>GET /api/books : Obtener todos los libros.</h1>
     *
     * <pre>Busca y devuelve todos los libros que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los libros de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BookDetailDTO> getBooks() {
        return listBookEntity2DetailDTO(bookLogic.getBooks());
    }

    /**
     * <h1>GET /api/books/{id} : Obtener libro por id.</h1>
     *
     * <pre>Busca el libro con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el libro correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un libro con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del libro que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    @Path("{id: \\d+}")
    public BookDetailDTO getBook(@PathParam("id") Long id) {
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        return new BookDetailDTO(entity);
    }

    /**
     * <h1>POST /api/books : Crear un libro.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link BookDetailDTO}.
     * 
     * Crea un nuevo libro con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo libro .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el libro.
     * </code>
     * </pre>
     * @param book {@link BookDetailDTO} - EL libro que se desea guardar.
     * @return JSON {@link BookDetailDTO}  - El libro guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el libro.
     */
    @POST
    public BookDetailDTO createBook(BookDetailDTO book) throws BusinessLogicException {        
         return new BookDetailDTO(bookLogic.createBook(book.toEntity()));
    }

    /**
     * <h1>PUT /api/books/{id} : Actualizar libro con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link BookDetailDTO}.
     *
     * Actualiza el libro con el id recibido en la URL con la información que se recibe en el cuerpo de la petición.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el libro con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precondition Failes. No se puede actualizar el libro con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del libro que se desea actualizar. Este debe ser una cadena de dígitos.
     * @param book {@link BookDetailDTO} El libro que se desea guardar.
     * @return JSON {@link BookDetailDTO} - El libro guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro a actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se puede actualizar el libro.
     */
    @PUT
    @Path("{id: \\d+}")
    public BookDetailDTO updateBook(@PathParam("id") Long id, BookDetailDTO book) throws WebApplicationException, BusinessLogicException {
        book.setId(id);
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        return new BookDetailDTO(bookLogic.updateBook(id, book.toEntity()));
    }

    /**
     * <h1>DELETE /api/books/{id} : Borrar libro por id.</h1>
     *
     * <pre>Borra el libro con el id asociado recibido en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina el libro correspondiente al id dado.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code>
     * </pre>
     * @param id Identificador del libro que se desea borrar. Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se puede eliminar el libro.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteBook(@PathParam("id") Long id) throws BusinessLogicException {
        BookEntity entity = bookLogic.getBook(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + id + " no existe.", 404);
        }
        bookLogic.deleteBook(id);
    }

    /**
     * Conexión con el servicio de reseñas para un libro. {@link ReviewResource}
     * 
     * Este método conecta la ruta de /books con las rutas de /reviews que dependen
     * del libro, es una redirección al servicio que maneja el segmento de la 
     * URL que se encarga de las reseñas.
     * 
     * @param booksId El ID del libro con respecto al cual se accede al servicio.
     * @return El servicio de Reseñas para ese libro en paricular.
     */
    @Path("{idBook: \\d+}/reviews")
    public Class<ReviewResource> getReviewResource(@PathParam("idBook") Long booksId) {
        BookEntity entity = bookLogic.getBook(booksId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + "/reviews no existe.", 404);
        }
        return ReviewResource.class;
    }

    /**
     * Conexión con el servicio de autores para un libro. {@link BookAuthorsResource}
     * 
     * Este método conecta la ruta de /books con las rutas de /authors que dependen
     * del libro, es una redirección al servicio que maneja el segmento de la 
     * URL que se encarga de los Autores.
     * 
     * @param booksId El ID del libro con respecto al cual se accede al servicio.
     * @return El servicio de Autores para ese libro en paricular.
     */
    @Path("{booksId: \\d+}/authors")
    public Class<BookAuthorsResource> getBookAuthorsResource(@PathParam("booksId") Long booksId) {
        BookEntity entity = bookLogic.getBook(booksId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + booksId + "/reviews no existe.", 404);
        }
        return BookAuthorsResource.class;
    }

    private List<BookDetailDTO> listBookEntity2DetailDTO(List<BookEntity> entityList) {
        List<BookDetailDTO> list = new ArrayList<>();
        for (BookEntity entity : entityList) {
            list.add(new BookDetailDTO(entity));
        }
        return list;
    }

}
