package com.pmdm.example.projects.kotlinapuntes.examples.Classes.Static.Java;

/**
 * Clase que implementa el patrón Singleton en Java de forma manual.
 * Garantiza que solo exista una instancia de esta clase en toda la aplicación.
 */
// Clase que implementa el patrón Singleton en Java
public class ConfiguracionManager {
    // 1. Declaración de la única instancia posible de la clase.
    // 'private static' asegura que solo la clase puede accederla y que es compartida por todas las instancias (aunque solo haya una).
    private static ConfiguracionManager instance;

    // 2. Propiedades de configuración que el Singleton gestionará.
    private String tema = "Claro";
    private int tamanoFuente = 14;

    // 3. Constructor privado para evitar que la clase sea instanciada directamente desde fuera.
    private ConfiguracionManager() {
        System.out.println("Java: ConfiguracionManager instanciado por primera vez.");
    }

    // 4. Método estático público que proporciona la única instancia de la clase.
    // Utiliza 'lazy initialization' (la instancia se crea solo cuando se necesita).
    // El bloque 'synchronized' es esencial para asegurar que, en entornos multi-hilo,
    // solo un hilo pueda crear la instancia, evitando la creación de múltiples singletons.
    public static ConfiguracionManager getInstance() {
        if (instance == null) {
            synchronized (ConfiguracionManager.class) { // Bloque sincronizado
                if (instance == null) { // Doble chequeo para eficiencia
                    instance = new ConfiguracionManager();
                }
            }
        }
        return instance;
    }

    // Getters y Setters para acceder y modificar las propiedades de configuración.
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public int getTamanoFuente() { return tamanoFuente; }
    public void setTamanoFuente(int tamanoFuente) { this.tamanoFuente = tamanoFuente; }

    // Método para mostrar el estado actual de la configuración.
    public void mostrarConfiguracion() {
        System.out.println("Java Config: Tema=" + tema + ", Fuente=" + tamanoFuente);
    }

    public static void main(String[] args) {
        System.out.println("--- Usando Singleton en Java ---");

        // Obtenemos la única instancia del gestor de configuración.
        ConfiguracionManager config1 = ConfiguracionManager.getInstance();
        config1.setTema("Oscuro"); // Modificamos el tema
        config1.mostrarConfiguracion(); // Imprimimos la configuración actualizada

        // Obtenemos la instancia de nuevo. Será la misma que 'config1'.
        ConfiguracionManager config2 = ConfiguracionManager.getInstance();
        config2.setTamanoFuente(16); // Modificamos el tamaño de fuente
        config2.mostrarConfiguracion(); // Imprimirá el tema "Oscuro" y la fuente 16

        // Comprobamos si ambas referencias apuntan a la misma instancia.
        System.out.println("¿config1 y config2 son la misma instancia? " + (config1 == config2)); // Debería ser 'true'
        System.out.println();
    }
}

