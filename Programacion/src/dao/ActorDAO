package dao;
import dto.Actor;
import java.util.ArrayList;
import java.util.List;

/** Clase DAO para gestionar las operaciones CRUD de actores. Tiene métodos para crear, leer, actualizar y eliminar actores en el sistema */
public class ActorDAO {
    private static List<Actor> actores = new ArrayList<>();
    /** Registra un nuevo actor en el sistema.
     * @param actor
     * @throws IllegalArgumentException Si el actor es nulo*/
    public void crearActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("El actor no puede ser nulo");
        }
        actores.add(actor);
    }

    /** Busca un actor por su ID.
     * @param id Identificador único del actor
     * @return El actor encontrado o null si no existe
     */
    public Actor buscarPorId(int id) {
        return actores.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }

    /** Obtiene una lista de todos los actores registrados
     * @return Lista de actores
     */
    public List<Actor> listarActores() {
        return new ArrayList<>(actores);
    }

    /** Actualiza los datos de un actor existente
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

    /** Elimina un actor del sistema por su ID.
     * @param id
     * @return true si se eliminó, false sino  hay actor
     */
    public boolean eliminarActor(int id) {
        return actores.removeIf(a -> a.getId() == id);
    }
}
