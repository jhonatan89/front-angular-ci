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

import co.edu.uniandes.csw.bookstore.dtos.BookDetailDTO;
import co.edu.uniandes.csw.bookstore.ejb.AuthorLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * <pre>Clase que implementa el recurso "authors/books".
 * URL: /api/authors/{authorsId}/books
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * que el servicio {@link AuthorResource} define este servicio de forma relativa 
 * con la ruta "books" con respecto un autor.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author ISIS2603
 * @version 1.0
 */
@Path("authors/{authorsId: \\d+}/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorBooksResource {

    @Inject private AuthorLogic authorLogic;
    

    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDetailDTO convertida.
     * 
     */
    private List<BookDetailDTO> booksListEntity2DTO(List<BookEntity> entityList){
        List<BookDetailDTO> list = new ArrayList<>();
        for (BookEntity entity : entityList) {
            list.add(new BookDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BookDetailDTO a una lista de BookEntity.
     *
     * @param dtos Lista de BookDetailDTO a convertir.
     * @return Lista de BookEntity convertida.
     * 
     */
    private List<BookEntity> booksListDTO2Entity(List<BookDetailDTO> dtos){
        List<BookEntity> list = new ArrayList<>();
        for (BookDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * <h1>GET /api/authors/{authorsId}/books : Obtener todos los libros de un autor.</h1>
     *
     * <pre>Busca y devuelve todos los libros que existen en un autor.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los libros del autor.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code>
     * @param authorsId El ID del autor del cual se buscan los libros
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en el autor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BookDetailDTO> listBooks(@PathParam("authorsId") Long authorsId) {
        return booksListEntity2DTO(authorLogic.listBooks(authorsId));
    }

    /**
     * <h1>GET /api/authors/{authorsId}/books/{booksId} : Obtener un libro de un autor.</h1>
     *
     * <pre>Busca y devuelve el libro con el ID recibido en la URL, relativo a un
     * autor.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el libro del autor.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code>
     * @param authorsId El ID del autor del cual se busca el libro
     * @param booksId El ID del libro que se busca
     * @return {@link BookDetailDTO} - El libro encontrado en el autor.
     */
    @GET
    @Path("{booksId: \\d+}")
    public BookDetailDTO getBooks(@PathParam("authorsId") Long authorsId, @PathParam("booksId") Long booksId) {
        return new BookDetailDTO(authorLogic.getBook(authorsId, booksId));
    }

    /**
     * <h1>POST /api/authors/{authorsId}/books/{booksId} : Aociar un libro a un autor.</h1>
     *
     * <pre> Asocia un libro existente con un autor existente
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Asoció el libro .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found: No existe el libro o el autor
     * </code>
     * </pre>
     * @param authorsId El ID del autor al cual se le va a asociar el libro
     * @param booksId El ID del libro que se asocia
     * @return JSON {@link BookDetailDTO}  - El libro asociado.
     */
    @POST
    @Path("{booksId: \\d+}")
    public BookDetailDTO addBooks(@PathParam("authorsId") Long authorsId, @PathParam("booksId") Long booksId) {
        return new BookDetailDTO(authorLogic.addBook(authorsId, booksId));
    }

    /**
     * <h1>PUT /api/authors/{authorsId}/books/ : Actualizar los libros de un autor..</h1>
     *
     * <pre>Cuerpo de petición: JSONArray {@link BookDetailDTO}.
     * 
     * Actualiza la lista de libros de un autor con la lista que se recibe en el
     * cuerpo
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se actualizó la lista de libros
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: No se pudo actualizar la lista
     * </code>
     * </pre>
     * @param authorsId El ID del autor al cual se le va a asociar el libro
     * @param books JSONArray {@link BookDetailDTO} - La lista de libros que se desea guardar.
     * @return JSONArray {@link BookDetailDTO}  - La lista actualizada.
     */
    @PUT
    public List<BookDetailDTO> replaceBooks(@PathParam("authorsId") Long authorsId, List<BookDetailDTO> books) {
        return booksListEntity2DTO(authorLogic.replaceBooks(authorsId, booksListDTO2Entity(books)));
    }

    /**
     * <h1>DELETE /api/authors/{authorsId}/books/{id} : Desasociar libro por id.</h1>
     *
     * <pre>Elimina la conexión entre el libro y e autor recibidos en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina la referencia al libro.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado en el autor.
     * </code>
     * </pre>
     * @param authorsId El ID del autor al cual se le va a desasociar el libro
     * @param booksId El ID del libro que se desasocia
     */
    @DELETE
    @Path("{booksId: \\d+}")
    public void removeBooks(@PathParam("authorsId") Long authorsId, @PathParam("booksId") Long booksId) {
        authorLogic.removeBook(authorsId, booksId);
    }
}
