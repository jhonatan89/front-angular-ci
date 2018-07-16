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

import co.edu.uniandes.csw.bookstore.dtos.AuthorDetailDTO;
import co.edu.uniandes.csw.bookstore.ejb.BookLogic;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
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
 * <pre>Clase que implementa el recurso "books/authors".
 * URL: /api/books/{booksId}/authors
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * que el servicio {@link BookResource} define este servicio de forma relativa 
 * con la ruta "authors" con respecto un libro.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author ISIS2603
 * @version 1.0
 */
@Path("books/{booksId: \\d+}/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookAuthorsResource {

    @Inject
    private BookLogic bookLogic;

    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     * 
     */
    private List<AuthorDetailDTO> authorsListEntity2DTO(List<AuthorEntity> entityList) {
        List<AuthorDetailDTO> list = new ArrayList<>();
        for (AuthorEntity entity : entityList) {
            list.add(new AuthorDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de AuthorDetailDTO a una lista de AuthorEntity.
     *
     * @param dtos Lista de AuthorDetailDTO a convertir.
     * @return Lista de AuthorEntity convertida.
     * 
     */
    private List<AuthorEntity> authorsListDTO2Entity(List<AuthorDetailDTO> dtos) {
        List<AuthorEntity> list = new ArrayList<>();
        for (AuthorDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * <h1>GET /api/books/{booksId}/authors : Obtener todos los autores de un libro.</h1>
     *
     * <pre>Busca y devuelve todos los autores que existen en un libro.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los autores del libro.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code>
     * @param booksId El ID del libro del cual se buscan los autores
     * @return JSONArray {@link AuthorDetailDTO} - Los autores encontrados en el libro. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<AuthorDetailDTO> listAuthors(@PathParam("booksId") Long booksId) {
        return authorsListEntity2DTO(bookLogic.listAuthors(booksId));
    }

    /**
     * <h1>GET /api/books/{booksId}/authors/{authorsId} : Obtener un autor de un libro.</h1>
     *
     * <pre>Busca y devuelve el autor con el ID recibido en la URL, relativo a un
     * libro.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el autor del libro.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code>
     * @param authorsId El ID del autor que se busca
     * @param booksId El ID del libro del cual se busca el autor
     * @return {@link AuthorDetailDTO} - El autor encontrado en el libro.
     */
    @GET
    @Path("{authorsId: \\d+}")
    public AuthorDetailDTO getAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        return new AuthorDetailDTO(bookLogic.getAuthor(booksId, authorsId));
    }

    /**
     * <h1>POST /api/books/{booksId}/authors/{authorsId} : Aociar un autor a un libro.</h1>
     *
     * <pre> Asocia un autor existente con un libro existente
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Asoció el autor .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found: No existe el libro o el autor
     * </code>
     * </pre>
     * @param authorsId El ID del autor que se va a asociar
     * @param booksId El ID del libro al cual se le va a asociar el autor
     * @return JSON {@link AuthorDetailDTO}  - El autor asociado.
     */
    @POST
    @Path("{authorsId: \\d+}")
    public AuthorDetailDTO addAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        return new AuthorDetailDTO(bookLogic.addAuthor(booksId, authorsId));
    }

    /**
     * <h1>PUT /api/books/{booksId}/authors/ : Actualizar los autores de un libro..</h1>
     *
     * <pre>Cuerpo de petición: JSONArray {@link AuthorDetailDTO}.
     * 
     * Actualiza la lista de autores de un libro con la lista que se recibe en el
     * cuerpo
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se actualizó la lista de autores
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: No se pudo actualizar la lista
     * </code>
     * </pre>
     * @param booksId El ID del libro al cual se le va a asociar la lista de autores
     * @param authors JSONArray {@link AuthorDetailDTO} - La lista de autores que se desea guardar.
     * @return JSONArray {@link AuthorDetailDTO}  - La lista actualizada.
     */
    @PUT
    public List<AuthorDetailDTO> replaceAuthors(@PathParam("booksId") Long booksId, List<AuthorDetailDTO> authors) {
        return authorsListEntity2DTO(bookLogic.replaceAuthors(booksId, authorsListDTO2Entity(authors)));
    }

    /**
     * <h1>DELETE /api/books/{booksId}/authors/{authorsId} : Desasociar autor por id.</h1>
     *
     * <pre>Elimina la conexión entre el autor y el libro recibidos en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina la referencia al autor.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado en el libro.
     * </code>
     * </pre>
     * @param booksId El ID del libro al cual se le va a desasociar el autor
     * @param authorsId El ID del autor que se desasocia
     */
    @DELETE
    @Path("{authorsId: \\d+}")
    public void removeAuthors(@PathParam("booksId") Long booksId, @PathParam("authorsId") Long authorsId) {
        bookLogic.removeAuthor(booksId, authorsId);
    }
}
