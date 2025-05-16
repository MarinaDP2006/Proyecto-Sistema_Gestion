package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.Actor;
import dto.Genero;
import dto.Pelicula;

/**
 * Clase DAO para la gestión del sistema
 * Con métodos CRUD para actores, películas y géneros.
 */
public class AdministradorDAO {
    private List<Actor> actores = new ArrayList<>();
    private List<Pelicula> peliculas = new ArrayList<>();

    // MÉTODOS PARA GESTIÓN DE ACTORES
    /**
     * Agrega un nuevo actor al sistema.
     * @param actor Objeto Actor a agregar
     * @throws IllegalArgumentException Si el actor es nulo o ya existe en el sistema
     */
    public void agregarActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("El actor no puede ser nulo");
        }
        if (buscarActor(actor.getId()) != null) {
            throw new IllegalArgumentException("El actor ya existe en el sistema");
        }
        actores.add(actor);
    }

    /**
     * Elimina un actor del sistema por su ID.
     * @param idActor ID del actor a eliminar
     * @return true si se eliminó correctamente, false si no se encontró el actor
     */
    public boolean eliminarActor(int idActor) {
        return actores.removeIf(a -> a.getId() == idActor);
    }

    /**
     * Busca un actor por su ID.
     * @param idActor ID del actor a buscar
     * @return Objeto Actor si se encuentra, null si no existe
     */
    public Actor buscarActor(int idActor) {
        return actores.stream()
                     .filter(a -> a.getId() == idActor)
                     .findFirst()
                     .orElse(null);
    }
    
    /**
     * Obtiene una lista de todos los actores registrados.
     * @return Lista de actores (copia para evitar modificaciones externas)
     */
    public List<Actor> listarActores() {
        return new ArrayList<>(actores);
    }

    /**
     * Actualiza la información de un actor existente.
     * @param actorActualizado Actor con los nuevos datos
     * @throws IllegalArgumentException Si el actor no existe en el sistema
     */
    public void actualizarActor(Actor actorActualizado) {
        Actor actor = buscarActor(actorActualizado.getId());
        if (actor == null) {
            throw new IllegalArgumentException("Actor no encontrado");
        }
        actor.setNombre(actorActualizado.getNombre());
        actor.setApellido(actorActualizado.getApellido());
        actor.setAñoNacimiento(actorActualizado.getAñoNacimiento());
        actor.setNacionalidad(actorActualizado.getNacionalidad());
    }

    // MÉTODOS PARA GESTIÓN DE PELÍCULAS

    /**
     * Agrega una nueva película al sistema.
     * @param pelicula Objeto Pelicula a agregar
     * @throws IllegalArgumentException Si la película es nula o ya existe
     */
    public void agregarPelicula(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula");
        }
        if (buscarPelicula(pelicula.getIdPelicula()) != null) {
            throw new IllegalArgumentException("La película ya existe en el sistema");
        }
        peliculas.add(pelicula);
    }

    /**
     * Elimina una película del sistema por su ID.
     * @param idPelicula ID de la película a eliminar
     * @return true si se eliminó correctamente, false si no se encontró la película
     */
    public boolean eliminarPelicula(int idPelicula) {
        return peliculas.removeIf(p -> p.getIdPelicula() == idPelicula);
    }

    /**
     * Busca una película por su ID.
     * @param idPelicula ID de la película a buscar
     * @return Objeto Pelicula si se encuentra, null si no existe
     */
    public Pelicula buscarPelicula(int idPelicula) {
        return peliculas.stream()
                       .filter(p -> p.getIdPelicula() == idPelicula)
                       .findFirst()
                       .orElse(null);
    }

    /**
     * Obtiene una lista de todas las películas registradas.
     * @return Lista de películas (copia para evitar modificaciones externas)
     */
    public List<Pelicula> listarPeliculas() {
        return new ArrayList<>(peliculas);
    }

    /**
     * Actualiza la información de una película existente.
     * @param peliculaActualizada Película con los nuevos datos
     * @throws IllegalArgumentException Si la película no existe en el sistema
     */
    public void actualizarPelicula(Pelicula peliculaActualizada) {
        Pelicula pelicula = buscarPelicula(peliculaActualizada.getIdPelicula());
        if (pelicula == null) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        pelicula.setTitulo(peliculaActualizada.getTitulo());
        pelicula.setAño(peliculaActualizada.getAño());
        pelicula.setDuracion(peliculaActualizada.getDuracion());
        pelicula.setResumen(peliculaActualizada.getResumen());
        pelicula.setGenero(peliculaActualizada.getGenero());
    }

    // MÉTODOS PARA GESTIÓN DE GÉNEROS
    /**
     * Obtiene todos los géneros disponibles en el sistema.
     * @return Lista de todos los géneros
     */
    public List<Genero> listarGeneros() {
        return Arrays.asList(Genero.values());
    }

    /**
     * Busca un género por su nombre.
     * @param nombre Nombre del género a buscar
     * @return Objeto Genero si se encuentra, null si no existe
     */
    public Genero buscarGenero(String nombre) {
        return Genero.buscarPorNombre(nombre);
    }

    /**
     * Filtra películas por género.
     * @param genero Género para filtrar
     * @return Lista de películas que coinciden con el género
     */
    public List<Pelicula> filtrarPeliculasPorGenero(Genero genero) {
        List<Pelicula> resultado = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getGenero() == genero) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
