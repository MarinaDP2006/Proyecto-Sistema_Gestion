package dto;

public enum Genero {
	    ACCION("Acción"),
	    COMEDIA("Comedia"),
	    DRAMA("Drama"),
	    TERROR("Terror"),
	    CIENCIA_FICCION("Ciencia Ficción"), 
	    ROMANCE("Romance"),
		FANTASIA("Fantasia"), 
		AVENTURA("Aventura");
	
// ATRIBUTO
  private String nombre;

  // CONSTRUCTOR
  Genero(String nombre) {
	  this.nombre = nombre;
	 }
  
  // GET Y SET
  public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

   /** Busca un género por su nombre
     * @param nombre 
     * @return Objeto Genero si se encuentra, null si no existe
     */
	 public static Genero buscarPorNombre(String nombre) {
	    for (Genero g : values()) {
	      if (g.getNombre().equalsIgnoreCase(nombre)) {
	            return g;
	      }
	    }
	      return null;
     }
}
