    // Métodos para la gestion de actores, peliculas, reparto y consultas
    private static void gestionActores() {
        Scanner sc = new Scanner(System.in);
        
       System.out.println("¿Qué deseas hacer?");
       System.out.println("Añadir actor");
        String añadir = sc.nextLine();
       System.out.println("Borrar actor");
        String borrar = sc.nextLine() ;
       System.out.println("Modificar actor");
        String modificar = sc.nextLine();
    System.out.println("Salir al menu principal");
        String salir = sc.nextLine();
    /*Completar para volver al menú principal (switch) */
    }

    private static void gestionPeliculas() {
        Scanner sc = new Scanner(System.in);
    
    System.out.println("¿Qué quieres hacer?");
    System.out.println("Ver información de una pelicula");
        String pelicula = sc.nextLine(); //info de la pelicula
    System.out.println("Añadir nueva pelicula");
        String añadirNueva = sc.nextLine();
    System.out.println("Eliminar pelicula existente");
        String eliminar = sc.nextLine();
    System.out.println("Salir al menú principal");
        String salir = sc.nextLine();
     /*Completar para volver al menú principal (switch) */
    }

    private static void gestionReparto() {
     Scanner sc = new Scanner(System.in);
       /*Completar para volver al menú principal (switch) */
    }

    private static void realizarConsultas() {
    Scanner sc = new Scanner(System.in);

     System.out.println("Hola! ¿Qué deseas hacer?");
     System.out.println("Buscar película por año de estreno");
        String buscarAño = sc.nextLine();
    System.out.println("Buscar un actor/actriz por su edad");
        String buscarEdad = sc.nextLine();
    System.out.println("Consultar la nacionalidad de un actor/actriz");
        String buscarNacionalidad = sc.nextLine();
    System.out.println("Consultar el género de una pelicula");
        String buscarGenero = sc.nextLine();
    System.out.println("Volver al menú inicial");
        String volver = sc.nextLine();
    /*Completar para volver al menú principal (switch) */
    }
}
