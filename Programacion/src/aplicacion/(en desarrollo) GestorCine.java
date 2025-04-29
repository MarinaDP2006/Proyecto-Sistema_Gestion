// En desarrollo
package aplicacion;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* IMPLEMENTO LOS DAO Y DTO */
import dao.AdminDAO;
import dao.UsuarioDAO;
import dto.Actor;
import dto.Genero;
import dto.Pelicula;
import dto.Reparto;

// Clase principal
public class GestorCine {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Actor> actores = new ArrayList<>();
    private static List<Pelicula> peliculas = new ArrayList<>();
    private static List<Reparto> repartos = new ArrayList<>();
    private static AdminDAO adminDAO = new AdminDAO();
    private static UsuarioDAO usuarioDAO = new UsuarioDAO(peliculas, actores);
    
   public static void main(String[] args) {
        cargarDatosIniciales(); 
        
        boolean salir = false;
        while (!salir) {
            System.out.println("\n ------ SISTEMA DE GESTIÓN CINEMATOGRÁFICA ------");
            System.out.println("1. Gestión de Actores");
            System.out.println("2. Gestión de Películas");
            System.out.println("3. Gestión de Reparto");
            System.out.println("4. Consultas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = obtenerOpcionValida();

            switch (opcion) {
                case 1:
                    gestionActores();
                    break;
                case 2:
                    gestionPeliculas();
                    break;
                case 3:
                    gestionReparto();
                    break;
                case 4:
                    realizarConsultas();
                    break;
                case 5:
                    salir = confirmarSalida();
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        System.out.println("\nSistema cerrado. ¡Hasta pronto!");
        scanner.close();
    }
