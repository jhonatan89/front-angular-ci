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

import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;

/**
 * ReviewDTO Objeto de transferencia de datos de Reseñas. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "name: string,
 *      "source": string,
 *      "description": string
 *   }
 * </pre>
 * Por ejemplo una reseña se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 123,
 *      "name: "Mi nueva review",
 *      "source": "NY Times",
 *      "description": "Un relato terrorífico"
 *   }
 *
 * </pre>
 * @author ISIS2603
 */
public class ReviewDTO {

    private Long id;

    private String name;

    private String source;

    private String description;

    /**
     * Constructor por defecto
     */
    public ReviewDTO() {
    }

    /**
     * Constructor a partir de una entidad
     * @param entity La entidad de la cual se construye el DTO
     */
    public ReviewDTO(ReviewEntity entity) {

        this.id = entity.getId();
        this.name = entity.getName();
        this.source = entity.getSource();
        this.description = entity.getDescription();
    }

    /**
     * Método para transformar del DTO a una entidada.
     * @return La entidad de esta reseña.
     */
    public ReviewEntity toEntity() {
        ReviewEntity entity = new ReviewEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setSource(this.source);
        entity.setDescription(this.description);
        return entity;
    }

    /**
     * Devuelve el ID de la reseña.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifica el ID de la reseña.
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la reseña.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Modifica el ID de la reseña.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la fuente de la reseña.
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Modifica la fuente de la reseña.
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Devuelve la descripción de la reseña.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifica la descripción de la reseña.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
