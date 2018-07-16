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

import co.edu.uniandes.csw.bookstore.dtos.ReviewDTO;
import co.edu.uniandes.csw.bookstore.ejb.ReviewLogic;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.mappers.BusinessLogicExceptionMapper;

import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "reviews".
 * URL: /api/books/{idBook}/reviews
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * que el servicio {@link BookResource} define este servicio de forma relativa 
 * con la ruta "reviews" con respecto un libro.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author ISIS2603
 * @version 1.0
 */
@Path("books/{idBook: \\d+}/reviews")
@Produces("application/json")
@Consumes("application/json")
public class ReviewResource {

    @Inject
    ReviewLogic reviewLogic;

    /**
     * <h1>GET /api/books/{idBook}/reviews : Obtener todas las reseñas de un libro.</h1>
     *
     * <pre>Busca y devuelve todas las reseñas que existen en un libro.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas las reseñas del libro.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code>
     * @param idBook El ID del libro del cual se buscan las reseñas
     * @return JSONArray {@link ReviewDTO} - Las reseñas encontradas en el libro. Si no hay ninguna retorna una lista vacía.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    public List<ReviewDTO> getReviews(@PathParam("idBook") Long idBook) throws BusinessLogicException {
        return listEntity2DTO(reviewLogic.getReviews(idBook));
    }

    /**
     * <h1>GET /api/books/{idBook}/reviews/{id} : Obtener una reseña de un libro.</h1>
     *
     * <pre>Busca y devuelve la reseña con el ID recibido en la URL, relativa a un
     * libro.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la reseña del libro.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un libro con el id dado.
     * </code>
     * @param idBook El ID del libro del cual se buscan las reseñas
     * @param id El ID de la reseña que se busca
     * @return {@link ReviewDTO} - La reseña encontradas en el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro.
     */
    @GET
    @Path("{id: \\d+}")
    public ReviewDTO getReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id) throws BusinessLogicException {
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        return new ReviewDTO(entity);
    }

    /**
     * <h1>POST /api/books/{idBook}/reviews : Crear una reseña de un libro.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ReviewDTO}.
     * 
     * Crea una nueva reseña con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó la nueva reseña .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe la reseña.
     * </code>
     * </pre>
     * @param idBook El ID del libro del cual se guarda la reseña
     * @param review {@link ReviewDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO}  - La reseña guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la reseña.
     */
    @POST
    public ReviewDTO createReview(@PathParam("idBook") Long idBook, ReviewDTO review) throws BusinessLogicException {
        return new ReviewDTO(reviewLogic.createReview(idBook, review.toEntity()));
    }

    /**
     * <h1>PUT /api/books/{idBook}/reviews/{id} : Actualizar una reseña de un libro.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ReviewDTO}.
     * 
     * Actualiza una reseña con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa el objeto actualizado.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se actualizó la reseña
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: No se pudo actualizar la reseña
     * </code>
     * </pre>
     * @param idBook El ID del libro del cual se guarda la reseña
     * @param id El ID de la reseña que se va a actualizar
     * @param review {@link ReviewDTO} - La reseña que se desea guardar.
     * @return JSON {@link ReviewDTO}  - La reseña actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la reseña.
     */
    @PUT
    @Path("{id: \\d+}")
    public ReviewDTO updateReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id, ReviewDTO review) throws BusinessLogicException {
        review.setId(id);
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        return new ReviewDTO(reviewLogic.updateReview(idBook, review.toEntity()));

    }

    /**
     * <h1>DELETE /api/books/{idBook}/reviews/{id} : Borrar reseña por id.</h1>
     *
     * <pre>Borra la reseña con el id asociado recibido en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina la reseña correspondiente al id dado dentro del libro.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe una reseña con el id dado en el libro.
     * </code>
     * </pre>
     * @param idBook El ID del libro del cual se va a eliminar la reseña.
     * @param id El ID de la reseña que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se puede eliminar la reseña.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteReview(@PathParam("idBook") Long idBook, @PathParam("id") Long id) throws BusinessLogicException {
        ReviewEntity entity = reviewLogic.getReview(idBook, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /books/" + idBook + "/reviews/" + id + " no existe.", 404);
        }
        reviewLogic.deleteReview(idBook, id);
    }

    private List<ReviewDTO> listEntity2DTO(List<ReviewEntity> entityList) {
        List<ReviewDTO> list = new ArrayList<>();
        for (ReviewEntity entity : entityList) {
            list.add(new ReviewDTO(entity));
        }
        return list;
    }

}
