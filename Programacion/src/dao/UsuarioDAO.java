// En desarrollo
package dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dto.Pelicula;
import dto.Genero;
import dto.Actor;

/** Clase DAO para las operaciones de usuario en el sistema
 * Con métodos de consulta para películas, actores y géneros
 */
public class UsuarioDAO {
    private List<Pelicula> peliculas;
    private List<Actor> actores;

    /** Constructor que inicializa las listas de datos.
     * @param peliculas 
     * @param actores
     */
    public UsuarioDAO(List<Pelicula> peliculas, List<Actor> actores) {
        this.peliculas = new ArrayList<>(peliculas);
        this.actores = new ArrayList<>(actores);
    }

    // MÉTODOS PARA CONSULTA DE PELÍCULAS
    /** Busca una película por su título
     * @param titulo Título o parte del título a buscar
     * @return Lista de películas que coinciden
     */


    /** Filtra películas por género.
     * @param genero Género para filtrar
     * @return Lista de películas del género especificado (lista vacía si no hay coincidencias)
     */


    /** Da las películas estrenadas en año exacto.
     * @param año Año de estreno a buscar
     * @return Lista de películas estrenadas ese año (lista vacía si no hay coincidencias)
     */


    /** Da todas las películas disponibles ordenadas por título.
     * @return Lista completa de películas ordenadas alfabéticamente
     */


    // MÉTODOS PARA CONSULTA DE ACTORES
    /** Busca un actor por nombre o apellido (búsqueda insensible a mayúsculas).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de actores que coinciden con el criterio (lista vacía si no hay coincidencias)
     */


    /** Filtra actores por nacionalidad.
     * @param nacionalidad Nacionalidad a buscar
     * @return Lista de actores de la nacionalidad especificada (lista vacía si no hay coincidencias)
     */



    /** Da la filmografía completa de un actor. Verifiicando si existe y filtro las peliculas donde aparece ese actor
     * @param idActor
     * @return Lista de películas en las que ha participado el actor
     */



    /** Da todos los actores registrados ordenados alfabéticamente.
     * @return Lista completa de actores ordenada por apellido
     */



    // MÉTODOS PARA GÉNEROS
    /** Da todos los géneros cinematográficos disponibles.
     * @return Lista de todos los géneros
     */


    /** Muestra las estadísticas de películas por género.
     * @return cuenta las películas por cada género
     */
