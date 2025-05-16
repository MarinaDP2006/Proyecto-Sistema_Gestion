package dto;

/**
 * Clase que representa el reparto de una película.
 */
public class Reparto {
    private int IDpelicula;
    private int IDactor;
    private String personaje;

    /**
     * Constructor para crear un nuevo reparto.
     * @param IDpelicula Identificador de la película
     * @param IDactor Identificador del actor
     * @param personaje Nombre del personaje interpretado por el actor
     */
    public Reparto(int IDpelicula, int IDactor, String personaje) {
        this.IDpelicula = IDpelicula;
        this.IDactor = IDactor;
        this.personaje = personaje;
    }

    // GET Y SET
    public int getPelicula() {
        return IDpelicula;
    }

    public void setPelicula(int pelicula) {
        this.IDpelicula = pelicula;
    }

    public int getActor() {
        return IDactor;
    }

    public void setActor(int actor) {
        this.IDactor = actor;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    @Override
    public String toString() {
        return "Pelicula: " + IDpelicula + "\n" + "Actor: " + IDactor + "\n" + "Personaje: " + personaje;
    }
}
