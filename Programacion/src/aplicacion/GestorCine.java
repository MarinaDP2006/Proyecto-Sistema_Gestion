package aplicacion;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import dto.Actor;
import dto.Genero;
import dto.Pelicula;
import dto.Reparto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

// BD:
import conexion.conexionBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Clase principal para gestionar el sistema cinematográfico. */
public class GestorCine {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Actor> actores = new ArrayList<>(); // guarda actores
    private static List<Pelicula> peliculas = new ArrayList<>(); // guarda peliculas
    private static List<Reparto> repartos = new ArrayList<>(); // guarda el reparto
    private static AdministradorDAO adminDAO = new AdministradorDAO(); // acceso para administrador
    private static UsuarioDAO usuarioDAO = new UsuarioDAO(peliculas, actores); // acceso para usuario, visualiza las peliculas con el reparto y los actores
    
    // Conexion a la base de datos (Comprobación):
    private static Connection conexion;
    
    private static final String ADMIN_NAME = "Marina"; // NOMBRE DEL ADMINISTRADOR
    
    public static void main(String[] args) throws SQLException {
        conexion = conexionBaseDatos.getConnection();
		if (conexion != null) {
		    System.out.println("Conexión a la base de datos establecida correctamente.");
		} else {
		    System.out.println("No se pudo establecer la conexión a la base de datos.");
		}

        System.out.print("\n Ingrese su nombre para acceder al sistema: ");
        String usuario = scanner.nextLine();
        boolean esAdmin = ADMIN_NAME.equalsIgnoreCase(usuario);

        boolean salir = false;
        
        while (!salir) { 
            System.out.println("\n------ SISTEMA DE GESTIÓN CINEMATOGRÁFICA ------");
            if (esAdmin) {
                System.out.println("1. Gestión de Actores");
                System.out.println("2. Gestión de Películas");
                System.out.println("3. Gestión de Reparto");
            }
            
            System.out.println("4. Consultas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1 -> {
                    if (esAdmin) gestionActores();
                    else System.out.println("Acceso denegado. Solo el administrador puede gestionar actores.");
                }
                case 2 -> {
                    if (esAdmin) gestionPeliculas();
                    else System.out.println("Acceso denegado. Solo el administrador puede gestionar películas.");
                }
                case 3 -> {
                    if (esAdmin) gestionReparto();
                    else System.out.println("Acceso denegado. Solo el administrador puede gestionar reparto.");
                }
                case 4 -> realizarConsultas();
                case 5 -> salir = confirmarSalida();
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }

        System.out.println("\nSistema cerrado. ¡Hasta pronto!");
        scanner.close();
    }

    /**
     * Confirma si el usuario desea salir del sistema.
     * @return true si desea salir, false en caso contrario
     */
    private static boolean confirmarSalida() {
        System.out.print("\n¿Está seguro que desea salir? (SI/NO): ");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("SI");
    }

    private static void gestionActores() {
        System.out.println("\n--- Gestión de Actores ---");
        System.out.println("Listado de actores:");
        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM actor");
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                System.out.println("ID: " + resultado.getInt("actor_id") + 
                                 " - Nombre: " + resultado.getString("nombre") + 
                                 " " + resultado.getString("apellidos") +
                                 " (" + resultado.getInt("año_nacimiento") + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar actores: " + e.getMessage());
        }

        System.out.print("\nIngrese el ID del actor a gestionar (o escriba '0' para crear uno nuevo): ");
        int idActor = scanner.nextInt();
        scanner.nextLine();
        
        if (idActor == 0) {
            crearActor();
            return;
        }

        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM actor WHERE actor_id = ?");
            statement.setInt(1, idActor);
            ResultSet resultado = statement.executeQuery();
            if (!resultado.next()) {
                System.out.println("No se encontró un actor con ese ID.");
                return;
            }
            
            System.out.print("¿Desea modificar o borrar el actor? (modificar/borrar): ");
            String accion = scanner.nextLine();

            if (accion.equalsIgnoreCase("modificar")) {
                modificarActor(idActor);
            } else if (accion.equalsIgnoreCase("borrar")) {
                borrarActor(idActor);
                System.out.println("Actor eliminado correctamente.");
            } else {
                System.out.println("Acción no válida.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar actor: " + e.getMessage());
        }
    }

    private static void crearActor() {
        System.out.print("Ingrese el nombre del actor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese los apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Ingrese el año de nacimiento: ");
        int añoNacimiento = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        try {
            PreparedStatement statement = conexion.prepareStatement(
                "INSERT INTO actor (nombre, apellidos, año_nacimiento, nacionalidad) VALUES (?, ?, ?, ?)");
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setInt(3, añoNacimiento);
            statement.setString(4, nacionalidad);
            statement.executeUpdate();
            System.out.println("Actor añadido correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear actor: " + e.getMessage());
        }
    }

    private static void modificarActor(int idActor) {
        System.out.print("Ingrese el nuevo nombre (o deje en blanco para no cambiarlo): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Ingrese los nuevos apellidos (o deje en blanco para no cambiarlos): ");
        String nuevosApellidos = scanner.nextLine();
        System.out.print("Ingrese el nuevo año de nacimiento (o 0 para no cambiarlo): ");
        int nuevoAño = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la nueva nacionalidad (o deje en blanco para no cambiarla): ");
        String nuevaNacionalidad = scanner.nextLine();

        try {
            PreparedStatement select = conexion.prepareStatement("SELECT * FROM actor WHERE actor_id = ?");
            select.setInt(1, idActor);
            ResultSet resultado = select.executeQuery();
            if (!resultado.next()) {
                System.out.println("Actor no encontrado.");
                return;
            }
            
            // Mantiene los datos o actualizo con unos nuevos
            String nombreFinal = nuevoNombre.isBlank() ? resultado.getString("nombre") : nuevoNombre;
            String apellidosFinal = nuevosApellidos.isBlank() ? resultado.getString("apellidos") : nuevosApellidos;
            int añoFinal = nuevoAño == 0 ? resultado.getInt("año_nacimiento") : nuevoAño;
            String nacionalidadFinal = nuevaNacionalidad.isBlank() ? resultado.getString("nacionalidad") : nuevaNacionalidad;

            PreparedStatement update = conexion.prepareStatement(
                "UPDATE actor SET nombre = ?, apellidos = ?, año_nacimiento = ?, nacionalidad = ? WHERE actor_id = ?");
            update.setString(1, nombreFinal);
            update.setString(2, apellidosFinal);
            update.setInt(3, añoFinal);
            update.setString(4, nacionalidadFinal);
            update.setInt(5, idActor);
            update.executeUpdate();
            System.out.println("Actor modificado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al modificar actor: " + e.getMessage());
        }
    }

    private static void borrarActor(int idActor) {
        try {
            // Primero eliminar de la tabla reparto
            PreparedStatement deleteReparto = conexion.prepareStatement(
                "DELETE FROM reparto WHERE actor_id = ?");
            deleteReparto.setInt(1, idActor);
            deleteReparto.executeUpdate();
            
            // Elimina el actor
            PreparedStatement deleteActor = conexion.prepareStatement(
                "DELETE FROM actor WHERE actor_id = ?");
            deleteActor.setInt(1, idActor);
            deleteActor.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar actor: " + e.getMessage());
        }
    }

    private static void gestionPeliculas() {
        System.out.println("\n--- Gestión de Películas ---");
        System.out.println("Listado de películas:");
        try {
            PreparedStatement statement = conexion.prepareStatement(
                "SELECT p.*, g.nombre as genero_nombre FROM pelicula p LEFT JOIN genero g ON p.genero_id = g.genero_id");
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                System.out.println("ID: " + resultado.getInt("pelicula_id") + 
                                 " - Título: " + resultado.getString("titulo") + 
                                 " (" + resultado.getInt("año_estreno") + ")" +
                                 " - Género: " + resultado.getString("genero_nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar películas: " + e.getMessage());
        }

        System.out.print("\nIngrese el ID de la película a gestionar (o '0' para crear una nueva): ");
        int idPelicula = scanner.nextInt();
        scanner.nextLine(); 
        
        if (idPelicula == 0) {
            crearPelicula();
            return;
        }

        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM pelicula WHERE pelicula_id = ?");
            statement.setInt(1, idPelicula);
            ResultSet resultado = statement.executeQuery();
            if (!resultado.next()) {
                System.out.println("No se encontró una película con ese ID.");
                return;
            }
            
            System.out.print("¿Desea modificar o borrar la película? (modificar/borrar): ");
            String accion = scanner.nextLine();

            if (accion.equalsIgnoreCase("modificar")) {
                modificarPelicula(idPelicula);
            } else if (accion.equalsIgnoreCase("borrar")) {
                borrarPelicula(idPelicula);
                System.out.println("Película eliminada correctamente.");
            } else {
                System.out.println("Acción no válida.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar película: " + e.getMessage());
        }
    }

    private static void crearPelicula() {
        System.out.print("Ingrese el título de la nueva película: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el año de estreno: ");
        int año = scanner.nextInt();
        System.out.print("Ingrese la duración (en minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Géneros disponibles:");
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM genero");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("genero_id") + ": " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar géneros: " + e.getMessage());
            return;
        }
        
        System.out.print("Ingrese el ID del género: ");
        int generoId = scanner.nextInt();
        scanner.nextLine();

        try {
            PreparedStatement statement = conexion.prepareStatement(
                "INSERT INTO pelicula (titulo, año_estreno, duracion, genero_id) VALUES (?, ?, ?, ?)");
            statement.setString(1, titulo);
            statement.setInt(2, año);
            statement.setInt(3, duracion);
            statement.setInt(4, generoId);
            statement.executeUpdate();
            System.out.println("Película añadida correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear película: " + e.getMessage());
        }
    }

    private static void modificarPelicula(int idPelicula) {
        try {
            PreparedStatement select = conexion.prepareStatement("SELECT * FROM pelicula WHERE pelicula_id = ?");
            select.setInt(1, idPelicula);
            ResultSet resultado = select.executeQuery();
            if (!resultado.next()) {
                System.out.println("Película no encontrada.");
                return;
            }

            System.out.print("Ingrese el nuevo título (actual: " + resultado.getString("titulo") + "): ");
            String nuevoTitulo = scanner.nextLine();
            System.out.print("Ingrese el nuevo año de estreno (actual: " + resultado.getInt("año_estreno") + "): ");
            int nuevoAño = scanner.nextInt();
            System.out.print("Ingrese la nueva duración en minutos (actual: " + resultado.getInt("duracion") + "): ");
            int nuevaDuracion = scanner.nextInt();
            scanner.nextLine();
            
            System.out.println("Géneros disponibles:");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM genero");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("genero_id") + ": " + rs.getString("nombre"));
            }
            
            System.out.print("Ingrese el nuevo ID del género (actual: " + resultado.getInt("genero_id") + "): ");
            int nuevoGeneroId = scanner.nextInt();
            scanner.nextLine();

            // Prepare update
            PreparedStatement update = conexion.prepareStatement(
                "UPDATE pelicula SET titulo = ?, año_estreno = ?, duracion = ?, genero_id = ? WHERE pelicula_id = ?");
            update.setString(1, nuevoTitulo.isBlank() ? resultado.getString("titulo") : nuevoTitulo);
            update.setInt(2, nuevoAño == 0 ? resultado.getInt("año_estreno") : nuevoAño);
            update.setInt(3, nuevaDuracion == 0 ? resultado.getInt("duracion") : nuevaDuracion);
            update.setInt(4, nuevoGeneroId);
            update.setInt(5, idPelicula);
            update.executeUpdate();
            System.out.println("Película modificada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al modificar película: " + e.getMessage());
        }
    }

    private static void borrarPelicula(int idPelicula) {
        try {
            // Primero elimino de la tabla de reparto
            PreparedStatement deleteReparto = conexion.prepareStatement(
                "DELETE FROM reparto WHERE pelicula_id = ?");
            deleteReparto.setInt(1, idPelicula);
            deleteReparto.executeUpdate();
            
            // Borra la pelicula
            PreparedStatement deletePelicula = conexion.prepareStatement(
                "DELETE FROM pelicula WHERE pelicula_id = ?");
            deletePelicula.setInt(1, idPelicula);
            deletePelicula.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al borrar película: " + e.getMessage());
        }
    }

    private static void gestionReparto() {
        System.out.println("\n--- Gestión de Reparto ---");
        System.out.println("Películas disponibles:");
        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM pelicula");
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                System.out.println("ID: " + resultado.getInt("pelicula_id") + 
                                 " - Título: " + resultado.getString("titulo"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar películas: " + e.getMessage());
        }

        System.out.print("\nIngrese el ID de la película para gestionar su reparto: ");
        int idPelicula = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nReparto actual:");
        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "SELECT a.actor_id, a.nombre, a.apellidos, r.personaje " +
                "FROM reparto r JOIN actor a ON r.actor_id = a.actor_id " +
                "WHERE r.pelicula_id = ?");
            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("actor_id") + 
                                 " - Actor: " + rs.getString("nombre") + " " + rs.getString("apellidos") +
                                 " como " + rs.getString("personaje"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar reparto: " + e.getMessage());
        }

        System.out.print("\n¿Desea añadir, modificar o eliminar un actor del reparto? (añadir/modificar/eliminar): ");
        String accion = scanner.nextLine();

        if (accion.equalsIgnoreCase("añadir")) {
            añadirActorReparto(idPelicula);
        } else if (accion.equalsIgnoreCase("modificar")) {
            modificarActorReparto(idPelicula);
        } else if (accion.equalsIgnoreCase("eliminar")) {
            eliminarActorReparto(idPelicula);
        } else {
            System.out.println("Acción no válida.");
        }
    }

    private static void añadirActorReparto(int idPelicula) {
        System.out.println("\nActores disponibles:");
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM actor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("actor_id") + 
                                 " - " + rs.getString("nombre") + " " + rs.getString("apellidos"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar actores: " + e.getMessage());
            return;
        }

        System.out.print("Ingrese el ID del actor a añadir: ");
        int idActor = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el personaje que interpreta: ");
        String personaje = scanner.nextLine();

        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "INSERT INTO reparto (pelicula_id, actor_id, personaje) VALUES (?, ?, ?)");
            stmt.setInt(1, idPelicula);
            stmt.setInt(2, idActor);
            stmt.setString(3, personaje);
            stmt.executeUpdate();
            System.out.println("Actor añadido al reparto correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al añadir actor al reparto: " + e.getMessage());
        }
    }

    private static void modificarActorReparto(int idPelicula) {
        System.out.print("Ingrese el ID del actor cuyo personaje desea modificar: ");
        int idActor = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Ingrese el nuevo personaje: ");
        String nuevoPersonaje = scanner.nextLine();

        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "UPDATE reparto SET personaje = ? WHERE pelicula_id = ? AND actor_id = ?");
            stmt.setString(1, nuevoPersonaje);
            stmt.setInt(2, idPelicula);
            stmt.setInt(3, idActor);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Personaje modificado correctamente.");
            } else {
                System.out.println("No se encontró ese actor en el reparto de esta película.");
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar reparto: " + e.getMessage());
        }
    }

    private static void eliminarActorReparto(int idPelicula) {
        System.out.print("Ingrese el ID del actor a eliminar del reparto: ");
        int idActor = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "DELETE FROM reparto WHERE pelicula_id = ? AND actor_id = ?");
            stmt.setInt(1, idPelicula);
            stmt.setInt(2, idActor);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Actor eliminado del reparto correctamente.");
            } else {
                System.out.println("No se encontró ese actor en el reparto de esta película.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar actor del reparto: " + e.getMessage());
        }
    }
    /**
     * Método para realizar consultas sobre películas y actores.
     */
    private static void realizarConsultas() {
        System.out.println("\n--- SISTEMA DE CONSULTAS CINEMATOGRÁFICAS ---");
        System.out.println("Seleccione el tipo de consulta:");
        System.out.println("1. Consultar películas por año de estreno");
        System.out.println("2. Consultar película por título");
        System.out.println("3. Consultar actores por nombre o apellido");
        System.out.println("4. Consultar personaje en una película");
        System.out.print("Ingrese la opción deseada: ");

        if (!scanner.hasNextInt()) { 
            System.out.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
            return;
        }

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> consultarPeliculasPorAño();
            case 2 -> consultarPeliculaPorTitulo();
            case 3 -> consultarActoresPorNombre();
            case 4 -> consultarPersonajeEnPelicula();
            default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
        }
    }

    /* Consulta películas por año de estreno.*/
    private static void consultarPeliculasPorAño() {
        System.out.print("\nIngrese el año de estreno para buscar películas: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Debe ingresar un año válido.");
            scanner.nextLine();
            return;
        }

        int año = scanner.nextInt();
        scanner.nextLine();

        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM pelicula WHERE año_estreno= ?");
            statement.setInt(1, año);
            ResultSet resultado = statement.executeQuery();

            if (!resultado.next()) {
                System.out.println("No se encontraron películas para el año especificado.");
            } else {
                do {
                    System.out.println("Película encontrada: " + resultado.getString("titulo"));
                } while (resultado.next());
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar películas: " + e.getMessage());
        }
    }

    /** Consulta película por título.*/
    private static void consultarPeliculaPorTitulo() {
        System.out.print("\nIngrese el título de la película a buscar: ");
        String titulo = scanner.nextLine();
// Es más complejo pero la consulta se divide en dos para que me salga toda la info y el tipo de genero
        try {
            PreparedStatement statement = conexion.prepareStatement(
                "SELECT p.*, g.nombre as nombre_genero " + "FROM pelicula p " +
                "JOIN genero g ON p.genero_id = g.genero_id " + "WHERE p.titulo = ?");
            statement.setString(1, titulo);
            ResultSet resultado = statement.executeQuery();

            if (!resultado.next()) {
                System.out.println("No se encontró una película con ese título.");
            } else {
                System.out.println("\nDetalles de la película:");
                System.out.println("Título: " + resultado.getString("titulo"));
                System.out.println("Año de estreno: " + resultado.getInt("año_estreno"));
                System.out.println("Duración: " + resultado.getInt("duracion") + " minutos");
                System.out.println("Género: " + resultado.getString("nombre_genero"));
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la película: " + e.getMessage());
        }
    }
    
    /* Consulta actores por nombre o apellido.*/
    private static void consultarActoresPorNombre() {
        System.out.print("\nIngrese el nombre del actor para buscar: ");
        String nombre = scanner.nextLine();

        try {
            PreparedStatement statement = conexion.prepareStatement("SELECT * FROM actor WHERE nombre LIKE ? OR apellidos LIKE ?");
            statement.setString(1, "%" + nombre + "%");
            statement.setString(2, "%" + nombre + "%");
            ResultSet resultado = statement.executeQuery();

            if (!resultado.next()) {
                System.out.println("No se encontraron actores con el especificado.");
            } else {
                do {
                    System.out.println("Actor encontrado: " + resultado.getString("nombre") + " " + resultado.getString("apellidos"));
                } while (resultado.next());
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar actores: " + e.getMessage());
        }
    }

    /**
     * Consulta un personaje en una película.
     */
    private static void consultarPersonajeEnPelicula() {
        System.out.print("\nIngrese el título de la película: ");
        String tituloPelicula = scanner.nextLine();

        try {
            // Consulta la película usando un parámetro preparado
            PreparedStatement statement = conexion.prepareStatement("SELECT pelicula_id FROM pelicula WHERE titulo = ?");
            statement.setString(1, tituloPelicula);
            ResultSet resultado = statement.executeQuery();

            if (!resultado.next()) {
                System.out.println("No se encontró una película con ese título.");
                return;
            }

            int idPelicula = resultado.getInt("pelicula_id");

            System.out.print("Ingrese el nombre del personaje que desea buscar: ");
            String personaje = scanner.nextLine();

            // Consulta el personaje correctamente con parámetros preparados
            statement = conexion.prepareStatement("SELECT personaje, actor_id FROM reparto WHERE pelicula_id = ? AND personaje = ?");
            statement.setInt(1, idPelicula);
            statement.setString(2, personaje);
            resultado = statement.executeQuery();

            if (!resultado.next()) {
                System.out.println("No se encontró ese personaje en la película.");
            } else {
                System.out.println("\n -- Detalles del personaje --");
                System.out.println("Personaje: " + resultado.getString("personaje"));

                int actorId = resultado.getInt("actor_id");

                // Consulta para obtener el nombre del actor
                PreparedStatement statementActor = conexion.prepareStatement("SELECT nombre, apellidos FROM actor WHERE actor_id = ?");
                statementActor.setInt(1, actorId);
                ResultSet resultadoActor = statementActor.executeQuery();
                if (resultadoActor.next()) {
                    System.out.println("Interpretado por: " + resultadoActor.getString("nombre") + " " + resultadoActor.getString("apellidos"));
                } else {
                    System.out.println("No se encontró información del actor.");
            }
         }
        } catch (SQLException e) {
            System.out.println("Error al consultar personaje: " + e.getMessage());
        }
    }
}
