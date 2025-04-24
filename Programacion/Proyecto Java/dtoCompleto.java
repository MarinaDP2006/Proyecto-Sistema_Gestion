import dto.Actor;
import java.util.ArrayList;
import java.util.List;

/** Clase DAO para gestionar las operaciones CRUD de actores
 * Tiene métodos para crear, leer, actualizar y eliminar actores en el sistema
 */
public class ActorDAO {
    private static List<Actor> actores = new ArrayList<>();

    /**
     * Registra un nuevo actor en el sistema.
     * @param actor
     * @throws IllegalArgumentException Si el actor es nulo
     */
    public void crearActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("El actor no puede ser nulo");
        }
        actores.add(actor);
    }

    /**
     * Busca un actor por su ID.
     * @param id Identificador único del actor
     * @return El actor encontrado o null si no existe
     */
    public Actor buscarPorId(int id) {
        return actores.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }

    /**
     * Obtiene una lista de todos los actores registrados
     * @return Lista de actores
     */
    public List<Actor> listarActores() {
        return new ArrayList<>(actores);
    }

    /**
     * Actualiza los datos de un actor existente
     * @param actorActualizado
     * @throws IllegalArgumentException Si el actor no existe en el sistema
     */
    public void actualizarActor(Actor actorActualizado) {
        Actor actor = buscarPorId(actorActualizado.getId());
        if (actor == null) {
            throw new IllegalArgumentException("Actor no encontrado");
   }
        actor.setNombre(actorActualizado.getNombre());
        actor.setApellido(actorActualizado.getApellido());
        actor.setAñoNacimiento(actorActualizado.getAñoNacimiento());
        actor.setNacionalidad(actorActualizado.getNacionalidad());
    }

    /**
     * Elimina un actor del sistema por su ID.
     * @param id
     * @return true si se eliminó, false sino  hay actor
     */
    public boolean eliminarActor(int id) {
        return actores.removeIf(a -> a.getId() == id);
    }
}

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
public class AdminDAO {
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
     * @param peliculas 
     * @param actores
     */
    public UsuarioDAO(List<Pelicula> peliculas, List<Actor> actores) {
        this.peliculas = new ArrayList<>(peliculas);
        this.actores = new ArrayList<>(actores);
    }

    // MÉTODOS PARA CONSULTA DE PELÍCULAS
    /**
     * Busca una película por su título
     * @param titulo Título o parte del título a buscar
     * @return Lista de películas que coinciden
     */
    public List<Pelicula> buscarPeliculaPorTitulo(String titulo) {
        List<Pelicula> resultado = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Filtra películas por género.
     * @param genero Género para filtrar
     * @return Lista de películas del género especificado (lista vacía si no hay coincidencias)
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

    /**
     * Obtiene películas estrenadas en un año específico.
     * @param año Año de estreno a buscar
     * @return Lista de películas estrenadas ese año (lista vacía si no hay coincidencias)
     */
    public List<Pelicula> buscarPeliculasPorAño(int año) {
        List<Pelicula> resultado = new ArrayList<>();
        for (Pelicula p : peliculas) {
            if (p.getAño() == año) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Obtiene todas las películas disponibles ordenadas por título.
     * @return Lista completa de películas ordenadas alfabéticamente
     */
    public List<Pelicula> listarTodasLasPeliculas() {
        List<Pelicula> copia = new ArrayList<>(peliculas);
        copia.sort((p1, p2) -> p1.getTitulo().compareToIgnoreCase(p2.getTitulo()));
        return copia;
    }

    // MÉTODOS PARA CONSULTA DE ACTORES
    /**
     * Busca un actor por nombre o apellido (búsqueda insensible a mayúsculas).
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de actores que coinciden con el criterio (lista vacía si no hay coincidencias)
     */
    public List<Actor> buscarActorPorNombre(String nombre) {
        List<Actor> resultado = new ArrayList<>();
        for (Actor a : actores) {
            if (a.getNombre().toLowerCase().contains(nombre.toLowerCase()) ||
                a.getApellido().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    /**
     * Filtra actores por nacionalidad.
     * @param nacionalidad Nacionalidad a buscar
     * @return Lista de actores de la nacionalidad especificada (lista vacía si no hay coincidencias)
     */
    public List<Actor> filtrarActoresPorNacionalidad(String nacionalidad) {
        List<Actor> resultado = new ArrayList<>();
        for (Actor a : actores) {
            if (a.getNacionalidad().equalsIgnoreCase(nacionalidad)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    /**
     * Obtiene la filmografía completa de un actor.
     * @param idActor
     * @return Lista de películas en las que ha participado el actor
     */
    public List<Pelicula> obtenerFilmografiaActor(int idActor) {
        List<Pelicula> resultado = new ArrayList<>();
        
        // Verificar si el actor existe
        boolean actorExiste = actores.stream()
                       .anyMatch(a -> a.getId() == idActor);
        
        if (actorExiste) {
            // Filtrar las películas donde aparece este actor
            resultado = peliculas.stream()
                               .filter(p -> p.getActores().stream()
                                           .anyMatch(a -> a.getId() == idActor))
                               .collect(Collectors.toList());
        }
        return resultado;
    }
    
    /**
     * Obtiene todos los actores registrados ordenados alfabéticamente.
     * @return Lista completa de actores ordenada por apellido
     */
    public List<Actor> listarTodosLosActores() {
        List<Actor> copia = new ArrayList<>(actores);
        copia.sort((a1, a2) -> a1.getApellido().compareToIgnoreCase(a2.getApellido()));
        return copia;
    }

    // MÉTODOS PARA GÉNEROS
    /**
     * Obtiene todos los géneros cinematográficos disponibles.
     * @return Lista de todos los géneros
     */
    public List<Genero> listarTodosLosGeneros() {
        return List.of(Genero.values());
    }

    /**
     * Obtiene las estadísticas de películas por género.
     * @return ceutna las películas por cada género
     */
    public Map<Genero, Long> obtenerEstadisticasPorGenero() {
        return peliculas.stream()
               .collect(Collectors.groupingBy(
               Pelicula::getGenero,
               Collectors.counting()
       ));
    }
}

import dto.Reparto;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las relaciones entre actores y películas (reparto).
 * Permite asignar actores a películas y consultar repartos.
 */
public class RepartoDAO {
    private static List<Reparto> repartos = new ArrayList<>();

    /**
     * Asigna un actor a una película con un personaje específico.
     * @param reparto  relación película-actor-personaje.
     * @throws IllegalArgumentException Si el reparto es nulo.
     */
    public void asignarActorAPelicula(Reparto reparto) {
        if (reparto == null) {
            throw new IllegalArgumentException("El reparto no puede ser nulo");
        }
        repartos.add(reparto);
    }

    /**
     * Busca todas las asignaciones de reparto para una película
     * @param tituloPelicula  
     * @return Lista de repartos asociados a la película
     */
    public List<Reparto> buscarPorPelicula(String tituloPelicula) {
        List<Reparto> resultado = new ArrayList<>();
        for (Reparto r : repartos) {
        	resultado.add(r);
            }
		return resultado;
        }

    /**
     * Elimina una asignación de reparto por su índice en la lista.
     * @param indice Índice del reparto a eliminar
     * @throws IndexOutOfBoundsException Si el índice está fuera de rango.
     */
    public void eliminarReparto(int indice) {
        if (indice < 0 || indice >= repartos.size()) {
            throw new IndexOutOfBoundsException("Índice de reparto no válido");
        }
        repartos.remove(indice);
    }
}

import dto.Pelicula;
import dto.Genero;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de películas.
 * Tiene métodos para filtrar películas por género y otras consultas.
 */
public class PeliculaDAO {
    private static List<Pelicula> peliculas = new ArrayList<>();

    /**
     * Registra una nueva película en el sistema.
     * @param pelicula
     * @throws IllegalArgumentException Si la película es nula o el género no es válido.
     */
    public void crearPelicula(Pelicula pelicula) {
        if (pelicula == null || pelicula.getGenero() == null) {
            throw new IllegalArgumentException("Película o género no válidos");
        }
        peliculas.add(pelicula);
    }

    /**
     * Filtra películas por género.
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

    /**
     * Busca una película por su título.
     * @param titulo Título exacto de la película (no sensible a mayúsculas).
     * @return La película encontrada o null si no existe.
     */
    public Pelicula buscarPorTitulo(String titulo) {
        return peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElse(null);
    }

    /**
     * Lista de todas las películas registradas.
     * @return Lista completa de películas (copia para evitar modificaciones externas).
     */
    public List<Pelicula> listarPeliculas() {
        return new ArrayList<>(peliculas);
    }
}
