package dto;

public class Pelicula {
    private int id;
    private String titulo;
    private int año;
    private  int duracion;
    private String resumen;

public Pelicula(int id, String titulo, int año, int duracion, String resumen) {
        this.id = id;
        this.titulo = titulo;
        this.año = año;
        this.duracion = duracion;
        this.resumen = resumen;
    }

    // GET Y SET
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + "\nTitulo: " + titulo
                + "\nAño: " + año
                + "\nDuracion: " + duracion
                + "\nResumen: " + resumen;
    }
}
