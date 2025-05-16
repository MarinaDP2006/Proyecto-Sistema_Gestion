package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dto.Pelicula;
import dto.Genero;
import dto.Actor;

/**
 * Clase DAO para las operaciones de usuario en el sistema
 * Con métodos de consulta para películas, actores y géneros
 */
public class UsuarioDAO {
    private List<Pelicula> peliculas;
    private List<Actor> actores;

    /**
     * Constructor que inicializa las listas de datos.
     * @param peliculas Lista de películas
     * @param actores Lista de actores
     */
    public UsuarioDAO(List<Pelicula> peliculas, List<Actor> actores) {
        this.peliculas = new ArrayList<>(peliculas);
        this.actores = new ArrayList<>(actores);
    }

    // MÉTODOS PARA CONSULTA DE PELÍCULAS
    /**
     * Busca una película por su título.
     * @param titulo Título o parte del título a buscar
     * @return Lista de películas que coinciden
     */
    public List<Pelicula> buscarPeliculaPorTitulo(String titulo) {
        return peliculas.stream()
                .filter(pelicula -> pelicula.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Filtra películas por género.
     * @param genero Género para filtrar
     * @return Lista de películas del género especificado (lista vacía si no hay coincidencias)
     */
    public List<Pelicula> filtrarPeliculasPorGenero(Genero genero) {
        return peliculas.stream()
                .filter(pelicula -> pelicula.getGenero().equals(genero))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve las películas estrenadas en un año exacto.
     * @param año Año de estreno a buscar
     * @return Lista de películas estrenadas ese año (lista vacía si no hay coincidencias)
     */
    public List<Pelicula> peliculasEstrenadasEnAno(int año) {
        return peliculas.stream()
                .filter(pelicula -> pelicula.getAño() == año)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve todas las películas disponibles ordenadas por título.
     * @return Lista completa de películas ordenadas alfabéticamente
     */
    public List<Pelicula> obtenerPeliculasOrdenadasPorTitulo() {
        return peliculas.stream()
                .sorted((p1, p2) -> p1.getTitulo().compareToIgnoreCase(p2.getTitulo()))
                .collect(Collectors.toList());
    }

    // MÉTODOS PARA CONSULTA DE ACTORES
    /**
     * Busca un actor por nombre o apellido (búsqueda insensible a mayúsculas).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de actores que coinciden con el criterio (lista vacía si no hay coincidencias)
     */
    public List<Actor> buscarActorPorNombre(String nombre) {
        return actores.stream()
                .filter(actor -> actor.getNombre().toLowerCase().contains(nombre.toLowerCase()) || 
                                 actor.getApellido().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Filtra actores por nacionalidad.
     * @param nacionalidad Nacionalidad a buscar
     * @return Lista de actores de la nacionalidad especificada (lista vacía si no hay coincidencias)
     */
    public List<Actor> filtrarActoresPorNacionalidad(String nacionalidad) {
        return actores.stream()
                .filter(actor -> actor.getNacionalidad().equalsIgnoreCase(nacionalidad))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve la filmografía completa de un actor, verificando si existe y filtrando las películas donde aparece ese actor.
     * @param idActor ID del actor
     * @return Lista de películas en las que ha participado el actor
     */
    public List<Pelicula> obtenerFilmografiaDeActor(int idActor) {
        return peliculas.stream()
                .filter(pelicula -> pelicula.getActores().stream().anyMatch(actor -> actor.getId() == idActor))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve todos los actores registrados ordenados alfabéticamente.
     * @return Lista completa de actores ordenada por apellido
     */
    public List<Actor> obtenerActoresOrdenadosPorApellido() {
        return actores.stream()
                .sorted((a1, a2) -> a1.getApellido().compareToIgnoreCase(a2.getApellido()))
                .collect(Collectors.toList());
    }

    // MÉTODOS PARA GÉNEROS
    /**
     * Devuelve todos los géneros cinematográficos disponibles.
     * @return Lista de todos los géneros
     */
    public List<Genero> obtenerTodosGeneros() {
        return peliculas.stream()
                .map(Pelicula::getGenero)
                .distinct() // Eliminar géneros duplicados
                .collect(Collectors.toList());
    }

    /**
     * Muestra las estadísticas de películas por género.
     * @return Mapa que cuenta las películas por cada género
     */
    public Map<Genero, Long> estadisticasPeliculasPorGenero() {
        return peliculas.stream()
                .collect(Collectors.groupingBy(Pelicula::getGenero, Collectors.counting()));
    }
}
