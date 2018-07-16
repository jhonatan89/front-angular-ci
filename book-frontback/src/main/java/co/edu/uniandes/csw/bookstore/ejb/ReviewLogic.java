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

import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.ReviewPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Reseña.
 * @author ISIS2603
 */
@Stateless
public class ReviewLogic {

    private static final Logger LOGGER = Logger.getLogger(EditorialLogic.class.getName());

    @Inject
    private ReviewPersistence persistence;

    @Inject
    private BookLogic bookLogic;

    /**
     * Obtiene la lista de los registros de Review que pertenecen a un Book.
     *
     * @param bookid id del Book el cual es padre de los Reviews.
     * @return Colección de objetos de ReviewEntity.
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException Error cuando el libro no tiene reseñas.
     */
    public List<ReviewEntity> getReviews(Long bookid) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de consultar todos los reviews");
        BookEntity book = bookLogic.getBook(bookid);
        if (book.getReviews() == null) {
            throw new BusinessLogicException("El libro que consulta aún no tiene reviews");
        }
        if (book.getReviews().isEmpty()) {
            throw new BusinessLogicException("El libro que consulta aún no tiene reviews");
        }
        return book.getReviews();
    }

    /**
     * Obtiene los datos de una instancia de Review a partir de su ID.
     * La existencia del elemento padre Book se debe garantizar.
     *
     * @param bookid El id del Libro buscado
     * @param reviewid Identificador de la Reseña a consultar
     * @return Instancia de ReviewEntity con los datos del Review consultado.
     * 
     */
    public ReviewEntity getReview(Long bookid, Long reviewid) {
        return persistence.find(bookid, reviewid);
    }

    /**
     * Se encarga de crear un Review en la base de datos.
     *
     * @param entity Objeto de ReviewEntity con los datos nuevos
     * @param bookid id del Book el cual sera padre del nuevo Review.
     * @return Objeto de ReviewEntity con los datos nuevos y su ID.
     * 
     */
    public ReviewEntity createReview(Long bookid, ReviewEntity entity) {
        LOGGER.info("Inicia proceso de crear review");
        BookEntity book = bookLogic.getBook(bookid);
        entity.setBook(book);
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Review.
     *
     * @param entity Instancia de ReviewEntity con los nuevos datos.
     * @param bookid id del Book el cual sera padre del Review actualizado.
     * @return Instancia de ReviewEntity con los datos actualizados.
     * 
     */
    public ReviewEntity updateReview(Long bookid, ReviewEntity entity) {
        LOGGER.info("Inicia proceso de actualizar review");
        BookEntity book = bookLogic.getBook(bookid);
        entity.setBook(book);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Review de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param bookid id del Book el cual es padre del Review.
     * 
     */
    public void deleteReview(Long bookid, Long id) {
        LOGGER.info("Inicia proceso de borrar review");
        ReviewEntity old = getReview(bookid, id);
        persistence.delete(old.getId());
    }

}
