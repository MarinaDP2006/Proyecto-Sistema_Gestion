package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película en el sistema.
 */
public class Pelicula {
    private int idPelicula;
    private String titulo;
    private int año;
    private int duracion;
    private String resumen;
    private Genero genero;
    private List<Actor> actores; // Lista de actores que participan en la película

    /**
     * Constructor para crear una nueva película.
     * @param idPelicula Identificador de la película
     * @param titulo Título de la película
     * @param año Año de estreno de la película
     * @param duracion Duración de la película en minutos
     * @param resumen Resumen de la película
     * @param genero Género de la película
     */
    public Pelicula(int idPelicula, String titulo, int año, int duracion, String resumen, Genero genero) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.año = año;
        this.duracion = duracion;
        this.resumen = resumen;
        this.genero = genero;
        this.actores = new ArrayList<>(); // Inicializa la lista de actores
    }

    // GET Y SET
    public int getIdPelicula() {
        return idPelicula;
    }

    public void setId(int idPelicula) {
        this.idPelicula = idPelicula;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Agrega un actor a la película.
     * @param actor Actor a agregar
     */
    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }

    /**
     * Devuelve la lista de actores que participan en la película.
     * @return Lista de actores
     */
    public List<Actor> getActores() {
        return new ArrayList<>(actores); // Devuelve una copia de la lista de actores
    }

    @Override
    public String toString() {
        return "ID: " + idPelicula
                + "\nTitulo: " + titulo
                + "\nAño: " + año
                + "\nDuracion: " + duracion
                + "\nResumen: " + resumen;
    }
}
