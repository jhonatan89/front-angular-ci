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

import co.edu.uniandes.csw.bookstore.ejb.EditorialLogic;
import co.edu.uniandes.csw.bookstore.dtos.EditorialDetailDTO;
import co.edu.uniandes.csw.bookstore.entities.EditorialEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.bookstore.mappers.WebApplicationExceptionMapper;
import co.edu.uniandes.csw.bookstore.persistence.EditorialPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "editorials".
 * URL: /api/editorials
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "editorials".</i>
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
@Path("editorials")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EditorialResource {

    @Inject
    EditorialLogic editorialLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    private static final Logger LOGGER = Logger.getLogger(EditorialPersistence.class.getName());

    /**
     * <h1>POST /api/editorials : Crear una editorial.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link EditorialDetailDTO}.
     * 
     * Crea una nueva editorial con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó la nueva editorial .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe la editorial.
     * </code>
     * </pre>
     * @param editorial {@link EditorialDetailDTO} - La editorial que se desea guardar.
     * @return JSON {@link EditorialDetailDTO}  - La editorial guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la editorial.
     */
    @POST
    public EditorialDetailDTO createEditorial(EditorialDetailDTO editorial) throws BusinessLogicException {
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        EditorialEntity editorialEntity = editorial.toEntity();
        // Invoca la lógica para crear la editorial nueva
        EditorialEntity nuevoEditorial = editorialLogic.createEditorial(editorialEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        return new EditorialDetailDTO(nuevoEditorial);
    }

    /**
     * <h1>GET /api/editorials : Obtener todas las editoriales.</h1>
     *
     * <pre>Busca y devuelve todas las editoriales que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas las editoriales de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link EditorialDetailDTO} - Las editoriales encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<EditorialDetailDTO> getEditorials() {
        return listEntity2DetailDTO(editorialLogic.getEditorials());
    }

    /**
     * <h1>GET /api/editorials/{id} : Obtener editorial por id.</h1>
     *
     * <pre>Busca la editorial con el id asociado recibido en la URL y la devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la editorial correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una editorial con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador de la editorial que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDetailDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{id: \\d+}")
    public EditorialDetailDTO getEditorial(@PathParam("id") Long id) throws WebApplicationException {
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        return new EditorialDetailDTO(editorialLogic.getEditorial(id));
    }

    /**
     * <h1>PUT /api/editorials/{id} : Actualizar editorial con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link EditorialDetailDTO}.
     *
     * Actualiza la editorial con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza la editorial con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe una editorial con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador de la editorial que se desea actualizar. Este debe ser una cadena de dígitos.
     * @param editorial {@link EditorialDetailDTO} La editorial que se desea guardar.
     * @return JSON {@link EditorialDetailDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial a actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public EditorialDetailDTO updateEditorial(@PathParam("id") Long id, EditorialDetailDTO editorial) throws WebApplicationException {
        editorial.setId(id);
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        return new EditorialDetailDTO(editorialLogic.updateEditorial(id, editorial.toEntity()));
    }

    /**
     * <h1>DELETE /api/editorials/{id} : Borrar editorial por id.</h1>
     *
     * <pre>Borra la editorial con el id asociado recibido en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina la editorial correspondiente al id dado.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe una editorial con el id dado.
     * </code>
     * </pre>
     * @param id Identificador de la editorial que se desea borrar. Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se puede eliminar la editorial.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEditorial(@PathParam("id") Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una editorial con id {0}", id);
        EditorialEntity entity = editorialLogic.getEditorial(id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + id + " no existe.", 404);
        }
        editorialLogic.deleteEditorial(id);

    }

    /**
     * Conexión con el servicio de libros para una editorial. {@link ReviewResource}
     * 
     * Este método conecta la ruta de /editorials con las rutas de /books que dependen
     * de la editorial, es una redirección al servicio que maneja el segmento de la 
     * URL que se encarga de los libros.
     * 
     * @param editorialsId El ID de la editorial con respecto al cual se accede al servicio.
     * @return El servicio de Libros para esa editorial en paricular.
     */
    @Path("{editorialsId: \\d+}/books")
    public Class<EditorialBooksResource> getEditorialBooksResource(@PathParam("editorialsId") Long editorialsId) {
        EditorialEntity entity = editorialLogic.getEditorial(editorialsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + editorialsId + " no existe.", 404);
        }
        return EditorialBooksResource.class;
    }

    /**
     *
     * lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EditorialEntity a una lista de
     * objetos EditorialDetailDTO (json)
     *
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<EditorialDetailDTO> listEntity2DetailDTO(List<EditorialEntity> entityList) {
        List<EditorialDetailDTO> list = new ArrayList<>();
        for (EditorialEntity entity : entityList) {
            list.add(new EditorialDetailDTO(entity));
        }
        return list;
    }

}
