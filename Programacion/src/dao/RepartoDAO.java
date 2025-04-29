package dao;
import dto.Reparto;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las relaciones entre actores y películas (reparto).
 * Permite asignar actores a películas y consultar repartos.
 */
public class RepartoDAO {
    private static List<Reparto> repartos = new ArrayList<>();

    /** Asigna un actor a una película con un personaje específico.
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
