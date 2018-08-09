/**
 * This class represents an author of the BookStore. 
 * It contains all the information relevant to the author.
 */
export class Author {
    /**
    * The author's id
    */
    id: number;

    /**
     * The author's name
     */ 
    name: string;

    /**
     * The location of the author's profile picture
     */
    image: string;

    /**
     * The author's birthdate MM/DD/YYYY
     */
    birthDate: string;

    /**
     * A brief description of the author's life
     */
    description: string;
}
