package dto;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Usuario {
    private String id;
    private String nombre;
    private String email;

    /** Constructor que crea un usuario
     * @param nombre 
     * @param email
     */
    public Usuario (String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    
 // GET Y SET
	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
