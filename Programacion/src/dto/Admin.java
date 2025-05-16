package dto;
import java.util.ArrayList;
import java.util.List;

public class Administrador {
	private String nombre;
    private String email;
    private String contraseña;
    private List<Actor> actores;
    private List<Pelicula> peliculas;
    private List<Genero> generos;

    /** Constructor
     * @param nombre 
     * @param email 
     * @param contraseña
     */
    
    public Administrador (String nombre, String email, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.actores = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.generos = new ArrayList<>();
    }
    
    // GET Y SET
    public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}
}
