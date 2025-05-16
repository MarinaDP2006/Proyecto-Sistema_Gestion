package dao;

import dto.Pelicula;
import dto.Genero;
import java.util.ArrayList;
import java.util.List;

/** Clase DAO para gestionar las operaciones CRUD de películas.
 * Tiene métodos para filtrar películas por género y otras consultas.
 */
public class PeliculaDAO {
    private static List<Pelicula> peliculas = new ArrayList<>();
    /** Registra una nueva película en el sistema.
     * @param pelicula
     * @throws IllegalArgumentException Si la película es nula o el género no es válido.
     */
    
    public void crearPelicula(Pelicula pelicula) {
        if (pelicula == null || pelicula.getGenero() == null) {
            throw new IllegalArgumentException("Película o género no válidos");
        }
        peliculas.add(pelicula);
    }

    /** Filtra películas por género.
     * @param genero Género para filtrar (ej: Genero.ACCION).
     * @return Lista de películas que coinciden con el género (lista vacía si no hay coincidencias).
     */
    public List<Pelicula> filtrarPorGenero(Genero genero) {
        List<Pelicula> resultado = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getGenero() == genero) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /*** Busca una película por su título.
     * @param titulo Título exacto de la película (no sensible a mayúsculas).
     * @return La película encontrada o null si no existe.
     */
    public Pelicula buscarPorTitulo(String titulo) {
        return peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElse(null);
    }

    /** Lista de todas las películas registradas.
     * @return Lista completa de películas (copia para evitar modificaciones externas).
     */
    public List<Pelicula> listarPeliculas() {
        return new ArrayList<>(peliculas);
    }

     /** Busca una película por su ID.
     * @param idPelicula Identificador único de la película.
     * @return La película encontrada o null si no existe.
     */
    public Pelicula buscarPelicula(int idPelicula) {
        return peliculas.stream()
            .filter(p -> p.getIdPelicula() == idPelicula)
            .findFirst()
            .orElse(null);
    }
}
