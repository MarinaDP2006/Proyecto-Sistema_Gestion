package dto;

public class Reparto {
	private int IDpelicula;
	private int IDactor;
	private String personaje;
	
    /** Constructor para crear un nuevo reparto.
    * @param personaje 
    * @param actor
    * @param personaje
    */
	public Reparto(int IDpelicula, int IDactor, String personaje) {
		super();
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
	
	 /** Aparecen las peliculas actores y personajes que intervienen en ella.
     * @return Lista con toda la informacion */
	
	public String toString() {
		return "Pelicula: " + IDpelicula + "\n" + "Actor: " + IDactor + "\n" + "Personaje: " + personaje;
	}
}
