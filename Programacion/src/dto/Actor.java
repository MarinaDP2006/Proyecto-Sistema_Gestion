package dto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.conexionBaseDatos;

public class Actor {
    private int id;
    private String nombre;
    private String apellido;
    private int añoNacimiento;
    private String nacionalidad;

    /**
     * Constructor para crear un nuevo actor.
     * @param idActor 
     * @param nombre 
     * @param apellido 
     * @param añoNacimiento 
     * @param nacionalidad 
     */
    
    public Actor(int id, String nombre, String apellido, int añoNacimiento, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.añoNacimiento = añoNacimiento;
        this.nacionalidad = nacionalidad;
    }
    
 // GET Y SET   
    public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public int getAñoNacimiento() {
		return añoNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setAñoNacimiento(int añoNacimiento) {
		this.añoNacimiento = añoNacimiento;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

// Guardar en la base de datos
	public void guardar() {
        try (Connection conn = conexionBaseDatos.getConnection()) {
            String sql = "INSERT INTO actores (id, nombre) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.id);
            stmt.setString(2, this.nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	@Override
    public String toString() {
        return nombre + " " + apellido + " (" + nacionalidad + ", nacido en " + añoNacimiento + ")";
    }
}
