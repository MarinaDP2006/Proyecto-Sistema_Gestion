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
    
    public static void main(String[] args) {
        cargarDatosIniciales(); 
        
        boolean salir = false;
        while (!salir) {
            System.out.println("\n ------ SISTEMA DE GESTIÓN CINEMATOGRÁFICA ------");
            System.out.println("1. Gestión de Actores");
            System.out.println("2. Gestión de Películas");
            System.out.println("3. Gestión de Reparto");
            System.out.println("4. Consultas");
            System.out.println("5. Ver listado completo"); // Nueva opción
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 
            
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
                    mostrarListadoCompleto(); // Llama al nuevo método
                    break;
                case 6:
                    salir = confirmarSalida();
                    break;
                default:
                    System.out.println("Opción no válida. Intentálo otra vez.");
            }
        }
        System.out.println("\nSistema cerrado. ¡Hasta pronto!");
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

    private static void cargarDatosIniciales() {
        // Añadir actores
        actores.add(new Actor(1, "Robert", "Downey Jr", 1965, "Estadounidense"));
        actores.add(new Actor(2, "Chris", "Evans", 1981, "Estadounidense"));
        actores.add(new Actor(3, "Christian", "Bale", 1974, "Británico"));
        actores.add(new Actor(4, "Vera", "Farmiga", 1973, "Estadounidense"));
        actores.add(new Actor(5, "Patrick", "Wilson", 1973, "Estadounidense"));
        actores.add(new Actor(6, "Bill", "Skarsgård", 1990, "Sueco"));
        actores.add(new Actor(7, "Tom", "Hanks", 1956, "Estadounidense"));
        actores.add(new Actor(8, "Viggo", "Mortensen", 1958, "Estadounidense-Danés"));
        actores.add(new Actor(9, "Leonardo", "DiCaprio", 1974, "Estadounidense"));
        actores.add(new Actor(10, "Matthew", "McConaughey", 1969, "Estadounidense"));
        actores.add(new Actor(11, "Toni", "Collette", 1972, "Australiana"));
        actores.add(new Actor(12, "Alex", "Wolff", 1997, "Estadounidense"));
        actores.add(new Actor(13, "Tim", "Robbins", 1958, "Estadounidense"));
        actores.add(new Actor(14, "Morgan", "Freeman", 1937, "Estadounidense"));
        actores.add(new Actor(15, "Emma", "Stone", 1988, "Estadounidense"));
        actores.add(new Actor(16, "Ryan", "Gosling", 1980, "Canadiense"));
        actores.add(new Actor(17, "Kate", "Winslet", 1975, "Británica"));
        actores.add(new Actor(18, "Harrison", "Ford", 1942, "Estadounidense"));
        actores.add(new Actor(19, "Karen", "Allen", 1951, "Estadounidense"));
        actores.add(new Actor(20, "Sam", "Neill", 1947, "Neozelandés"));
        actores.add(new Actor(21, "Laura", "Dern", 1967, "Estadounidense"));
        actores.add(new Actor(22, "Elijah", "Wood", 1981, "Estadounidense"));
        actores.add(new Actor(23, "Ian", "McKellen", 1939, "Británico"));
        actores.add(new Actor(24, "Daniel", "Radcliffe", 1989, "Británico"));
        actores.add(new Actor(25, "Rupert", "Grint", 1988, "Británico"));
        actores.add(new Actor(26, "Ana", "de Armas", 1988, "Española"));
        actores.add(new Actor(27, "Mark", "Ruffalo", 1967, "Estadounidense"));
        actores.add(new Actor(28, "Scarlett", "Johansson", 1984, "Estadounidense"));
        
        // Añadir películas
        peliculas.add(new Pelicula(1, "El Conjuro", 2013, 112, "Investigadores paranormales enfrentan un caso real", Genero.TERROR));
        peliculas.add(new Pelicula(2, "Hereditary", 2018, 127, "Una familia atormentada por fuerzas oscuras", Genero.TERROR));
        peliculas.add(new Pelicula(3, "Forrest Gump", 1994, 142, "La vida de un hombre con discapacidad intelectual", Genero.DRAMA));
        peliculas.add(new Pelicula(4, "The Shawshank Redemption", 1994, 142, "Un hombre inocente en prisión busca redención", Genero.DRAMA));
        peliculas.add(new Pelicula(5, "Titanic", 1997, 195, "Historia de amor en el famoso barco", Genero.ROMANCE));
        peliculas.add(new Pelicula(6, "La La Land", 2016, 128, "Un músico y una actriz persiguen sus sueños en Los Ángeles", Genero.ROMANCE));
        peliculas.add(new Pelicula(7, "Avengers", 2012, 143, "Superhéroes unidos para salvar el mundo", Genero.CIENCIA_FICCION));
        peliculas.add(new Pelicula(8, "Blade Runner 2049", 2017, 164, "Un cazador de replicantes descubre un secreto oculto", Genero.CIENCIA_FICCION));
        peliculas.add(new Pelicula(9, "Indiana Jones: Raiders of the Lost Ark", 1981, 115, "Un arqueólogo busca el Arca de la Alianza", Genero.AVENTURA));
        peliculas.add(new Pelicula(10, "Jurassic Park", 1993, 127, "Un parque temático con dinosaurios clonados", Genero.AVENTURA));
        peliculas.add(new Pelicula(11, "El Señor de los Anillos: La Comunidad del Anillo", 2001, 178, "Una aventura épica en la Tierra Media", Genero.FANTASIA));
        peliculas.add(new Pelicula(12, "Harry Potter y la Piedra Filosofal", 2001, 152, "Un joven mago descubre su herencia mágica", Genero.FANTASIA));
        
        // Añadir reparto
        // El Conjuro
        repartos.add(new Reparto(1, 4, "Lorraine Warren"));
        repartos.add(new Reparto(1, 5, "Ed Warren"));
        
        // Hereditary
        repartos.add(new Reparto(2, 11, "Annie Graham"));
        repartos.add(new Reparto(2, 12, "Peter Graham"));
        
        // Forrest Gump
        repartos.add(new Reparto(3, 7, "Forrest Gump"));
        repartos.add(new Reparto(3, 17, "Jenny Curran"));
        
        // The Shawshank Redemption
        repartos.add(new Reparto(4, 13, "Andy Dufresne"));
        repartos.add(new Reparto(4, 14, "Ellis Boyd 'Red' Redding"));
        
        // Titanic
        repartos.add(new Reparto(5, 9, "Jack Dawson"));
        repartos.add(new Reparto(5, 17, "Rose DeWitt Bukater"));
        
        // La La Land
        repartos.add(new Reparto(6, 16, "Sebastian Wilder"));
        repartos.add(new Reparto(6, 15, "Mia Dolan"));
        
        // Avengers
        repartos.add(new Reparto(7, 1, "Tony Stark/Iron Man"));
        repartos.add(new Reparto(7, 28, "Natasha Romanoff/Black Widow"));
        
        // Blade Runner 2049
        repartos.add(new Reparto(8, 16, "K"));
        repartos.add(new Reparto(8, 26, "Joi"));
        
        // Indiana Jones
        repartos.add(new Reparto(9, 18, "Indiana Jones"));
        repartos.add(new Reparto(9, 19, "Marion Ravenwood"));
        
        // Jurassic Park
        repartos.add(new Reparto(10, 20, "Dr. Alan Grant"));
        repartos.add(new Reparto(10, 21, "Dr. Ellie Sattler"));
        
        // El Señor de los Anillos
        repartos.add(new Reparto(11, 22, "Frodo Bolsón"));
        repartos.add(new Reparto(11, 23, "Gandalf"));
        
        // Harry Potter
        repartos.add(new Reparto(12, 24, "Harry Potter"));
        repartos.add(new Reparto(12, 25, "Ron Weasley"));

        System.out.println("Datos iniciales cargados correctamente.");
    }

// Métodos para gestion de actores, reparto y peliculas
private static void gestionActores() {
    System.out.println("\n--- Gestión de Actores ---");
    System.out.println("Listado actual de actores:");
    actores.forEach(actor -> System.out.println(actor));
    System.out.print("\n Ingrese el nombre del actor: ");
    String nombre = scanner.nextLine();
    System.out.print("Ingrese el apellido del actor: ");
    String apellido = scanner.nextLine();
    System.out.print("Ingrese el año de nacimiento del actor: ");
    int añoNacimiento = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Ingrese la nacionalidad del actor: ");
    String nacionalidad = scanner.nextLine();

    // Evita duplicados por nombre y apellido
    boolean existe = actores.stream()
        .anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre) && a.getApellido().equalsIgnoreCase(apellido));
    if (existe) {
        System.out.println("Ese actor ya existe.");
        return;
    }

    actores.add(new Actor(actores.size() + 1, nombre, apellido, añoNacimiento, nacionalidad));
    System.out.println("Actor añadido correctamente.");
}

private static void gestionPeliculas() {
    System.out.println("\n--- Gestión de Películas ---");
    System.out.println("Listado de películas en el sistema:");
    peliculas.forEach(p -> System.out.println(p));
    System.out.print("\nIngrese el ID de la película: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    // Evita IDs duplicados
    boolean existe = peliculas.stream().anyMatch(p -> p.getIdPelicula() == id);
    if (existe) {
        System.out.println("Ya existe una película con ese ID.");
        return;
    }

    System.out.print("Ingrese el título de la película: ");
    String titulo = scanner.nextLine();
    System.out.print("Ingrese el año de estreno: ");
    int año = scanner.nextInt();
    scanner.nextLine();         
    System.out.print("Ingrese la duración (en minutos): ");
    int duracion = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Ingrese el resumen de la película: ");
    String resumen = scanner.nextLine();
    System.out.print("Ingrese el género de la película: ");
    String generoStr = scanner.nextLine();
    Genero genero = Genero.buscarPorNombre(generoStr);

    if (genero == null) {
        System.out.println("Género no válido.");
        return;
    }

    peliculas.add(new Pelicula(id, titulo, año, duracion, resumen, genero));
    System.out.println("Película añadida correctamente.");
}

private static void gestionReparto() {
    System.out.println("\n--- Gestión de Reparto ---");
    System.out.println("Películas disponibles:");
    peliculas.forEach(p -> System.out.println("ID: " + p.getIdPelicula() + " - " + p.getTitulo()));
    System.out.print("\nIngrese el ID de la película: ");
    int IDpelicula = scanner.nextInt();
    scanner.nextLine(); 
    System.out.println("\nActores disponibles:");
    actores.forEach(a -> System.out.println("ID: " + a.getId() + " - " + a.getNombre() + " " + a.getApellido()));
    System.out.print("Ingrese el ID del actor: ");
    int IDactor = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Ingrese el personaje que interpreta el actor: ");
    String personaje = scanner.nextLine();

    Pelicula pelicula = peliculas.stream()
        .filter(p -> p.getIdPelicula() == IDpelicula)
        .findFirst()
        .orElse(null);
    Actor actor = actores.stream()
        .filter(a -> a.getId() == IDactor)
        .findFirst()
        .orElse(null);

    if (pelicula == null) {
        System.out.println("La película con ese ID no existe.");
        return;
    }
    if (actor == null) {
        System.out.println("El actor con ese ID no existe.");
        return;
    }

    boolean yaExiste = repartos.stream()
        .anyMatch(r -> r.getPelicula() == IDpelicula && r.getActor() == IDactor && r.getPersonaje().equalsIgnoreCase(personaje));
    if (yaExiste) {
        System.out.println("El actor ya está en el reparto de la película con el mismo personaje.");
        return;
    }

    repartos.add(new Reparto(IDpelicula, IDactor, personaje));
    System.out.println("Reparto añadido correctamente.");
}
    /**
     * Método para realizar consultas sobre películas.
     */
    private static void realizarConsultas() {
        System.out.println("\n--- Consultas peliculas ---");
        System.out.print("Ingrese el año de estreno para buscar películas: ");
        int año = scanner.nextInt();
        scanner.nextLine();

        // Filtra y muestra películas:
        List<Pelicula> peliculasEncontradas = peliculas.stream()
            .filter(p -> p.getAño() == año)
            .collect(Collectors.toList());

        if (peliculasEncontradas.isEmpty()) {
            System.out.println("No se encontraron películas para el año especificado.");
        } else {
            peliculasEncontradas.forEach(p -> System.out.println("Película encontrada: " + p.getTitulo()));
        }
        
        System.out.println("\n--- Consultas de Actores ---");
        System.out.print("Ingrese el nombre o apellido del actor para buscar: ");
        String nombre = scanner.nextLine();

        // Filtra y muestra actores:
        List<Actor> actoresEncontrados = actores.stream()
            .filter(actor -> actor.getNombre().toLowerCase().contains(nombre.toLowerCase()) || 
                             actor.getApellido().toLowerCase().contains(nombre.toLowerCase()))
            .collect(Collectors.toList());

        if (actoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron actores con el nombre o apellido especificado.");
        } else {
            actoresEncontrados.forEach(actor -> System.out.println("Actor encontrado: " + actor));
        }
    }
}
