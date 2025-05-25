package aplicacion;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import dto.Actor;
import dto.Genero;
import dto.Pelicula;
import dto.Reparto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// PONER CONEXION A BASE DE DATOS
/**
 * Clase principal para gestionar el sistema cinematográfico.
 */
public class GestorCine {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Actor> actores = new ArrayList<>();
    private static List<Pelicula> peliculas = new ArrayList<>();
    private static List<Reparto> repartos = new ArrayList<>();
    private static AdministradorDAO adminDAO = new AdministradorDAO();
    private static UsuarioDAO usuarioDAO = new UsuarioDAO(peliculas, actores);
    
    // El sistema pide que se identifique el administrador, sino lo es, solo puede hacer consultas y ver la pagina completa de datos
    private static final String ADMIN_NAME = "Marina";
    
    public static void main(String[] args) {
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
            System.out.println("5. Ver listado completo");
            System.out.println("6. Salir");
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
                case 5 -> mostrarListadoCompleto();
                case 6 -> salir = confirmarSalida();
                default -> System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }

        System.out.println("\nSistema cerrado.");
        scanner.close();
    }

// Agrega este método al final de la clase
private static void mostrarListadoCompleto() {
    System.out.println("\n--- Listado de Actores (ordenados por apellido) ---");
    actores.stream()
        .sorted((a1, a2) -> a1.getApellido().compareToIgnoreCase(a2.getApellido()))
        .forEach(actor -> System.out.println(actor));

    System.out.println("\n--- Listado de Películas (ordenadas por título) ---");
    peliculas.stream()
        .sorted((p1, p2) -> p1.getTitulo().compareToIgnoreCase(p2.getTitulo()))
        .forEach(pelicula -> System.out.println(pelicula));

    System.out.println("\n--- Listado de Reparto (ordenados por película y actor) ---");
    repartos.stream()
        .sorted((r1, r2) -> {
            int compare = Integer.compare(r1.getPelicula(), r2.getPelicula());
            if (compare == 0) {
                compare = Integer.compare(r1.getActor(), r2.getActor());
            }
            return compare;
        })
        .forEach(reparto -> {
            // Busca el nombre de la película y del actor para mostrarlo más claro
            Pelicula peli = peliculas.stream().filter(p -> p.getIdPelicula() == reparto.getPelicula()).findFirst().orElse(null);
            Actor actor = actores.stream().filter(a -> a.getId() == reparto.getActor()).findFirst().orElse(null);
            String nombrePeli = (peli != null) ? peli.getTitulo() : "Desconocida";
            String nombreActor = (actor != null) ? actor.getNombre() + " " + actor.getApellido() : "Desconocido";
            System.out.println("Película: " + nombrePeli + " | Actor: " + nombreActor + " | Personaje: " + reparto.getPersonaje());
        });
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

// Métodos para gestion de actores, reparto y peliculas
    private static void gestionActores() {
         System.out.println("\n--- Gestión de Actores ---");
        System.out.println("Listado de actores:");
        actores.forEach(actor -> System.out.println(actor.getNombre() + " " + actor.getApellido()));

        System.out.print("\nIngrese el nombre del actor a gestionar (o escriba 'nuevo' para crear uno nuevo): ");
        String nombre = scanner.nextLine();

        if (nombre.equalsIgnoreCase("nuevo")) {
            crearActor();
            return;
        }

        Actor actor = actores.stream()
            .filter(a -> a.getNombre().equalsIgnoreCase(nombre))
            .findFirst()
            .orElse(null);

        if (actor == null) {
            System.out.println("No se encontró un actor con ese nombre.");
            return;
        }

        System.out.print("¿Desea modificar o borrar el actor? (modificar/borrar): ");
        String accion = scanner.nextLine();

        if (accion.equalsIgnoreCase("modificar")) {
            modificarActor(actor);
        } else if (accion.equalsIgnoreCase("borrar")) {
            actores.remove(actor);
            System.out.println("Actor eliminado correctamente.");
        } else {
            System.out.println("Acción no válida.");
        }
    }

    private static void crearActor() {
        System.out.print("Ingrese el nombre del actor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el año de nacimiento: ");
        int añoNacimiento = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        actores.add(new Actor(actores.size() + 1, nombre, apellido, añoNacimiento, nacionalidad));
        System.out.println("Actor añadido correctamente.");
    }

    private static void modificarActor(Actor actor) {
        System.out.print("Ingrese el nuevo nombre (o deje en blanco para no cambiarlo): ");
        String nuevoNombre = scanner.nextLine();
        if (!nuevoNombre.isBlank()) actor.setNombre(nuevoNombre);

        System.out.print("Ingrese el nuevo apellido (o deje en blanco para no cambiarlo): ");
        String nuevoApellido = scanner.nextLine();
        if (!nuevoApellido.isBlank()) actor.setApellido(nuevoApellido);

        System.out.print("Ingrese el nuevo año de nacimiento (o 0 para no cambiarlo): ");
        int nuevoAño = scanner.nextInt();
        scanner.nextLine();
        if (nuevoAño != 0) actor.setAñoNacimiento(nuevoAño);

        System.out.print("Ingrese la nueva nacionalidad (o deje en blanco para no cambiarla): ");
        String nuevaNacionalidad = scanner.nextLine();
        if (!nuevaNacionalidad.isBlank()) actor.setNacionalidad(nuevaNacionalidad);

        System.out.println("Actor modificado correctamente.");
    }

    private static void gestionPeliculas() {
        System.out.println("\n--- Gestión de Películas ---");
        System.out.println("Listado de películas:");
        peliculas.forEach(p -> System.out.println("Título: " + p.getTitulo()));

        System.out.print("\nIngrese el título de la película a gestionar (o escriba 'nuevo' para crear una nueva): ");
        String titulo = scanner.nextLine();

        if (titulo.equalsIgnoreCase("nuevo")) {
            crearPelicula();
            return;
        }

        Pelicula pelicula = peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElse(null);

        if (pelicula == null) {
            System.out.println("No se encontró una película con ese título.");
            return;
        }

        System.out.print("¿Desea modificar o borrar la película? (modificar/borrar): ");
        String accion = scanner.nextLine();

        if (accion.equalsIgnoreCase("modificar")) {
            modificarPelicula(pelicula);
        } else if (accion.equalsIgnoreCase("borrar")) {
            peliculas.remove(pelicula);
            System.out.println("Película eliminada correctamente.");
        } else {
            System.out.println("Acción no válida.");
        }
    }

    private static void crearPelicula() {
        System.out.print("Ingrese el título de la nueva película: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el año de estreno: ");
        int año = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la duración (en minutos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el resumen: ");
        String resumen = scanner.nextLine();
        System.out.print("Ingrese el género: ");
        String generoStr = scanner.nextLine();
        Genero genero = Genero.buscarPorNombre(generoStr);

        if (genero == null) {
            System.out.println("Género no válido.");
            return;
        }

        peliculas.add(new Pelicula(peliculas.size() + 1, titulo, año, duracion, resumen, genero));
        System.out.println("Película añadida correctamente.");
    }

    private static void modificarPelicula(Pelicula pelicula) {
        System.out.print("Ingrese el nuevo título de la película (o deje en blanco para no cambiarlo): ");
        String nuevoTitulo = scanner.nextLine();
        if (!nuevoTitulo.isBlank()) pelicula.setTitulo(nuevoTitulo);

        System.out.print("Ingrese el nuevo año de estreno (o 0 para no cambiarlo): ");
        int nuevoAño = scanner.nextInt();
        scanner.nextLine();
        if (nuevoAño != 0) pelicula.setAño(nuevoAño);

        System.out.print("Ingrese la nueva duración en minutos (o 0 para no cambiarla): ");
        int nuevaDuracion = scanner.nextInt();
        scanner.nextLine();
        if (nuevaDuracion != 0) pelicula.setDuracion(nuevaDuracion);

        System.out.print("Ingrese el nuevo resumen (o deje en blanco para no cambiarlo): ");
        String nuevoResumen = scanner.nextLine();
        if (!nuevoResumen.isBlank()) pelicula.setResumen(nuevoResumen);

        System.out.print("Ingrese el nuevo género (o deje en blanco para no cambiarlo): ");
        String nuevoGeneroStr = scanner.nextLine();
        Genero nuevoGenero = Genero.buscarPorNombre(nuevoGeneroStr);
        if (nuevoGenero != null) pelicula.setGenero(nuevoGenero);

        System.out.println("Película modificada correctamente.");
    }

    private static void gestionReparto() {
        System.out.println("\n--- Gestión de Reparto ---");
        System.out.println("Películas disponibles:");
        peliculas.forEach(p -> System.out.println("Título: " + p.getTitulo()));

        System.out.print("\nIngrese el título de la película para gestionar su reparto (o 'nuevo' para crear uno nuevo): ");
        String tituloPelicula = scanner.nextLine();

        if (tituloPelicula.equalsIgnoreCase("nuevo")) {
            crearReparto();
            return;
        }

        Pelicula pelicula = peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(tituloPelicula))
            .findFirst()
            .orElse(null);

        if (pelicula == null) {
            System.out.println("No se encontró una película con ese título.");
            return;
        }

        System.out.print("¿Desea modificar o borrar un reparto? (modificar/borrar): ");
        String accion = scanner.nextLine();

        if (accion.equalsIgnoreCase("modificar")) {
            modificarReparto(pelicula);
        } else if (accion.equalsIgnoreCase("borrar")) {
            borrarReparto(pelicula);
        } else {
            System.out.println("Acción no válida.");
        }
    }

    private static void crearReparto() {
        System.out.println("\nCreando nuevo reparto...");
        System.out.print("Ingrese el título de la película: ");
        String tituloPelicula = scanner.nextLine();
        
        Pelicula pelicula = peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(tituloPelicula))
            .findFirst()
            .orElse(null);

        if (pelicula == null) {
            System.out.println("No se encontró una película con ese título.");
            return;
        }

        System.out.println("\nActores disponibles:");
        actores.forEach(a -> System.out.println(a.getNombre() + " " + a.getApellido()));

        System.out.print("Ingrese el nombre del actor: ");
        String nombreActor = scanner.nextLine();

        Actor actor = actores.stream()
            .filter(a -> a.getNombre().equalsIgnoreCase(nombreActor))
            .findFirst()
            .orElse(null);

        if (actor == null) {
            System.out.println("No se encontró un actor con ese nombre.");
            return;
        }

        System.out.print("Ingrese el personaje que interpreta: ");
        String personaje = scanner.nextLine();

        repartos.add(new Reparto(pelicula.getIdPelicula(), actor.getId(), personaje));
        System.out.println("Reparto añadido correctamente.");
    }

    private static void modificarReparto(Pelicula pelicula) {
        System.out.println("\nModificando reparto de la película: " + pelicula.getTitulo());

        System.out.print("Ingrese el nombre del actor a modificar en el reparto: ");
        String nombreActor = scanner.nextLine();

        Reparto reparto = repartos.stream()
            .filter(r -> r.getPelicula() == pelicula.getIdPelicula() && r.getActor() == actores.stream()
            .filter(a -> a.getNombre().equalsIgnoreCase(nombreActor))
            .map(Actor::getId)
            .findFirst().orElse(-1))
            .findFirst()
            .orElse(null);

        if (reparto == null) {
            System.out.println("No se encontró ese actor en el reparto.");
            return;
        }

        System.out.print("Ingrese el nuevo personaje que interpreta (o deje en blanco para no cambiarlo): ");
        String nuevoPersonaje = scanner.nextLine();
        if (!nuevoPersonaje.isBlank()) reparto.setPersonaje(nuevoPersonaje);

        System.out.println("Reparto modificado correctamente.");
    }

    private static void borrarReparto(Pelicula pelicula) {
        System.out.println("\nBorrando un reparto de la película: " + pelicula.getTitulo());

        System.out.print("Ingrese el nombre del actor a eliminar del reparto: ");
        String nombreActor = scanner.nextLine();

        boolean eliminado = repartos.removeIf(r -> r.getPelicula() == pelicula.getIdPelicula() &&
            actores.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombreActor) && a.getId() == r.getActor()));

        if (eliminado) {
            System.out.println("Reparto eliminado correctamente.");
        } else {
            System.out.println("No se encontró ese actor en el reparto.");
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

    /**
     * Consulta películas por año de estreno.
     */
    private static void consultarPeliculasPorAño() {
        System.out.print("\nIngrese el año de estreno para buscar películas: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Debe ingresar un año válido.");
            scanner.nextLine();
            return;
        }

        int año = scanner.nextInt();
        scanner.nextLine();

        List<Pelicula> peliculasEncontradas = peliculas.stream()
            .filter(p -> p.getAño() == año)
            .collect(Collectors.toList());

        if (peliculasEncontradas.isEmpty()) {
            System.out.println("No se encontraron películas para el año especificado.");
        } else {
            peliculasEncontradas.forEach(p -> System.out.println("Película encontrada: " + p.getTitulo()));
        }
    }

    /**
     * Consulta película por título.
     */
    private static void consultarPeliculaPorTitulo() {
        System.out.print("\nIngrese el título de la película a buscar: ");
        String titulo = scanner.nextLine();

        Pelicula peliculaEncontrada = peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElse(null);

        if (peliculaEncontrada == null) {
            System.out.println("No se encontró una película con ese título.");
        } else {
            System.out.println("\nDetalles de la película:");
            System.out.println("Título: " + peliculaEncontrada.getTitulo());
            System.out.println("Año de estreno: " + peliculaEncontrada.getAño());
            System.out.println("Duración: " + peliculaEncontrada.getDuracion() + " minutos");
            System.out.println("Resumen: " + peliculaEncontrada.getResumen());
            System.out.println("Género: " + peliculaEncontrada.getGenero());
        }
    }

    /**
     * Consulta actores por nombre o apellido.
     */
    private static void consultarActoresPorNombre() {
        System.out.print("\nIngrese el nombre del actor para buscar: ");
        String nombre = scanner.nextLine();

        List<Actor> actoresEncontrados = actores.stream()
            .filter(actor -> actor.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .collect(Collectors.toList());

        if (actoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron actores con el especificado.");
        } else {
            actoresEncontrados.forEach(actor -> System.out.println("Actor encontrado: " + actor.getNombre() + " " + actor.getApellido()));
        }
    }

    /**
     * Consulta un personaje en una película.
     */
    private static void consultarPersonajeEnPelicula() {
        System.out.print("\nIngrese el título de la película: ");
        String tituloPelicula = scanner.nextLine();

        Pelicula pelicula = peliculas.stream()
            .filter(p -> p.getTitulo().equalsIgnoreCase(tituloPelicula))
            .findFirst()
            .orElse(null);

        if (pelicula == null) {
            System.out.println("No se encontró una película con ese título.");
            return;
        }

        System.out.print("Ingrese el nombre del personaje que desea buscar: ");
        String personaje = scanner.nextLine();

        List<Reparto> personajesEncontrados = repartos.stream()
            .filter(r -> r.getPelicula() == pelicula.getIdPelicula() && r.getPersonaje().equalsIgnoreCase(personaje))
            .collect(Collectors.toList());

        if (personajesEncontrados.isEmpty()) {
            System.out.println("No se encontró ese personaje en la película.");
        } else {
            System.out.println("\nDetalles del personaje:");
            personajesEncontrados.forEach(r -> {
                Actor actor = actores.stream()
                    .filter(a -> a.getId() == r.getActor())
                    .findFirst()
                    .orElse(null);
                
                System.out.println("Personaje: " + r.getPersonaje());
                System.out.println("Interpretado por: " + (actor != null ? actor.getNombre() + " " + actor.getApellido() : "Actor desconocido"));
            });
        }
    }
}
