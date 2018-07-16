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
import co.edu.uniandes.csw.bookstore.ejb.AuthorLogic;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.mappers.WebApplicationExceptionMapper;
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
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "authors".
 * URL: /api/authors
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "authors".</i>
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
@Path("/authors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorResource {

    @Inject
    private AuthorLogic authorLogic;

    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     * 
     */
    private List<AuthorDetailDTO> listEntity2DTO(List<AuthorEntity> entityList) {
        List<AuthorDetailDTO> list = new ArrayList<>();
        for (AuthorEntity entity : entityList) {
            list.add(new AuthorDetailDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET /api/authors : Obtener todos los autores.</h1>
     *
     * <pre>Busca y devuelve todos los autores que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los autores de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link AuthorDetailDTO} - Los autores encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<AuthorDetailDTO> getAuthors() {
        return listEntity2DTO(authorLogic.getAuthors());
    }

    /**
     * <h1>GET /api/authors/{id} : Obtener autor por id.</h1>
     *
     * <pre>Busca el autor con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el autor correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un autor con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del autor que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link AuthorDetailDTO} - El autor buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el autor.
     */
    @GET
    @Path("{id: \\d+}")
    public AuthorDetailDTO getAuthor(@PathParam("id") Long id) {
        AuthorEntity entity = authorLogic.getAuthor(id);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        return new AuthorDetailDTO(entity);
    }

    /**
     * <h1>POST /api/authors : Crear un autor.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link AuthorDetailDTO}.
     * 
     * Crea un nuevo autor con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo autor .
     * </code>
     * </pre>
     * @param author {@link AuthorDetailDTO} - EL autor que se desea guardar.
     * @return JSON {@link AuthorDetailDTO}  - El autor guardado con el atributo id autogenerado.
     */
    @POST
    public AuthorDetailDTO createAuthor(AuthorDetailDTO author) {
        return new AuthorDetailDTO(authorLogic.createAuthor(author.toEntity()));
    }

    /**
     * <h1>PUT /api/authors/{id} : Actualizar autor con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link AuthorDetailDTO}.
     *
     * Actualiza el autor con el id recibido en la URL con la información que se recibe en el cuerpo de la petición.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el autor con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del autor que se desea actualizar. Este debe ser una cadena de dígitos.
     * @param author {@link AuthorDetailDTO} El autor que se desea guardar.
     * @return JSON {@link AuthorDetailDTO} - El autor guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el autor a actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public AuthorDetailDTO updateAuthor(@PathParam("id") Long id, AuthorDetailDTO author) {
        AuthorEntity entity = author.toEntity();
        entity.setId(id);
        AuthorEntity oldEntity = authorLogic.getAuthor(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        entity.setBooks(oldEntity.getBooks());
        return new AuthorDetailDTO(authorLogic.updateAuthor(entity));
    }

    /**
     * <h1>DELETE /api/authors/{id} : Borrar autor por id.</h1>
     *
     * <pre>Borra el autor con el id asociado recibido en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina el autor correspondiente al id dado.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code>
     * </pre>
     * @param id Identificador del autor que se desea borrar. Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteAuthor(@PathParam("id") Long id) {
        AuthorEntity entity = authorLogic.getAuthor(id);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        authorLogic.deleteAuthor(id);
    }

    /**
     * Conexión con el servicio de libros para un autor. {@link AuthorBooksResource}
     * 
     * Este método conecta la ruta de /authors con las rutas de /books que dependen
     * del autor, es una redirección al servicio que maneja el segmento de la 
     * URL que se encarga de los libros.
     * 
     * @param authorsId El ID del autor con respecto al cual se accede al servicio.
     * @return El servicio de Libros para ese autor en paricular.
     */
    @Path("{authorsId: \\d+}/books")
    public Class<AuthorBooksResource> getAuthorBooksResource(@PathParam("authorsId") Long authorsId) {
        AuthorEntity entity = authorLogic.getAuthor(authorsId);
        if (entity == null) {
            throw new WebApplicationException("El author no existe", 404);
        }
        return AuthorBooksResource.class;
    }

}
