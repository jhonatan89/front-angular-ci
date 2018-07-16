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
import co.edu.uniandes.csw.bookstore.ejb.EditorialLogic;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;
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
 * <pre>Clase que implementa el recurso "editorial/{id}/books".
 * URL: /api/editorials/{editorialsId}/books
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "editorials/{editorialsId}/books".</i>
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
@Path("editorials/{editorialsId: \\d+}/books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EditorialBooksResource {

    @Inject
    private EditorialLogic editorialLogic;

    /**
     * Convierte una lista de BookEntity a una lista de BookDetailDTO.
     *
     * @param entityList Lista de BookEntity a convertir.
     * @return Lista de BookDetailDTO convertida.
     * 
     */
    private List<BookDetailDTO> booksListEntity2DTO(List<BookEntity> entityList) {
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
    private List<BookEntity> booksListDTO2Entity(List<BookDetailDTO> dtos) {
        List<BookEntity> list = new ArrayList<>();
        for (BookDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * <h1>GET /api/editorials/{editorialsId}/books : Obtener todos los libros de una editorial.</h1>
     *
     * <pre>Busca y devuelve todos los libros que existen en la editorial.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los libros de la aplicacion.</code> 
     * </pre>
     * @param editorialsId Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSONArray {@link BookDetailDTO} - Los libros encontrados en la editorial. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BookDetailDTO> listBooks(@PathParam("editorialsId") Long editorialsId) {
        return booksListEntity2DTO(editorialLogic.listBooks(editorialsId));
    }

    /**
     * <h1>GET /api/editorials/{editorialsId}/books/{booksId} : Obtener libro por id de la editorial por id.</h1>
     *
     * <pre>Busca el libro con el id asociado dentro de la editorial con id asociado.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el libro correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un libro con el id dado dentro de la editorial.
     * </code> 
     * </pre>
     * @param editorialsId Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @param booksId Identificador del libro que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro buscado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial o el libro.
     */
    @GET
    @Path("{booksId: \\d+}")
    public BookDetailDTO getBooks(@PathParam("editorialsId") Long editorialsId, @PathParam("booksId") Long booksId) throws BusinessLogicException {
        return new BookDetailDTO(editorialLogic.getBook(editorialsId, booksId));
    }

    /**
     * <h1>POST /api/editorials/{editorialsId}/books/{booksId} : Guarda un libro dentro de la editorial.</h1>
     *
     * <pre> Guarda un libro dentro de una editorial con la informacion que 
     * recibe el la URL. Se devuelve el libro que se guarda en la editorial.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo libro .
     * </code>
     * </pre>
     * @param editorialsId Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @param booksId Identificador del libro que se desea guardar. Este debe ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El libro guardado en la editorial.
     */
    @POST
    @Path("{booksId: \\d+}")
    public BookDetailDTO addBooks(@PathParam("editorialsId") Long editorialsId, @PathParam("booksId") Long booksId) {
        return new BookDetailDTO(editorialLogic.addBook(booksId,editorialsId));
    }

    /**
     * <h1>PUT /api/editorials/{editorialsId}/books/{booksId} : Edita los libros de una editorial..</h1>
     *
     * <pre> Remplaza las instancias de Book asociadas a una instancia de Editorial
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los libros de la editorial.
     * </code>
     * </pre>
     * @param editorialsId Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @param books JSONArray {@link BookDetailDTO} El arreglo de libros nuevo para la editorial.
     * @return JSON {@link BookDetailDTO} - El arreglo de libros guardado en la editorial.
     */
    @PUT
    public List<BookDetailDTO> replaceBooks(@PathParam("editorialsId") Long editorialsId, List<BookDetailDTO> books) {
        return booksListEntity2DTO(editorialLogic.replaceBooks(editorialsId, booksListDTO2Entity(books)));
    }

    /**
     * <h1>DELETE /api/editorials/{editorialsId}/books/{booksId} : Elimina un libro dentro de la editorial.</h1>
     *
     * <pre> Elimina la referencia del libro asociado al ID dentro de la editorial
     * con la informacion que recibe el la URL. 
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del libro.
     * </code>
     * </pre>
     * @param editorialsId Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @param booksId Identificador del libro que se desea guardar. Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{booksId: \\d+}")
    public void removeBooks(@PathParam("editorialsId") Long editorialsId, @PathParam("booksId") Long booksId) {
        editorialLogic.removeBook(booksId,editorialsId);
    }
}
