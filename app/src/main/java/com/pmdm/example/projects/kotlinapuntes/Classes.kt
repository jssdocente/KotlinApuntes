package com.pmdm.example.projects.kotlinapuntes

// main.kt
fun main() {
    println("--- 1. Clase 'Usuario_V1' (Estilo Java: Constructor, Getters/Setters explícitos) ---")
    val user1 = Usuario_V1("Alice", "alice@example.com")
    println("Nombre: ${user1.getNombre()}, Email: ${user1.getEmail()}")
    user1.setNombre("Alicia Nueva") // Usamos el setter
    println("Nuevo Nombre: ${user1.getNombre()}")
    println("Saludo: ${user1.generarSaludo()}") // Método
    println("\n")

    println("--- 2. Clase 'Usuario_V2' (Kotlin: Propiedades en Constructor, Getters/Setters automáticos) ---")
    val user2 = Usuario_V2("Bob", "bob@example.com")
    println("Nombre: ${user2.nombre}, Email: ${user2.email}") // Acceso directo a las propiedades
    user2.nombre = "Robert" // 'nombre' es 'var', tiene setter automático
    // user2.email = "new@example.com" // ERROR: 'email' es 'val', inmutable
    println("Nuevo Nombre: ${user2.nombre}")
    println("Saludo: ${user2.generarSaludo()}")
    println("\n")

    println("--- 3. Clase 'Rectangulo_V3' (Kotlin: Propiedades Calculadas) ---")
    val rect1 = Rectangulo_V3(10.0, 5.0)
    println("Ancho: ${rect1.ancho}, Alto: ${rect1.alto}")
    println("Área: ${rect1.area}")           // Propiedad calculada
    println("Perímetro: ${rect1.perimetro}") // Propiedad calculada
    rect1.ancho = 12.0 // Modificamos una propiedad mutable
    println("Nuevo Ancho: ${rect1.ancho}, Nuevo Área: ${rect1.area}") // Área se recalcula automáticamente
    println("\n")

    println("--- 4. Clase 'Articulo_V4' (Kotlin: Constructor Secundario y Valores por Defecto) ---")
    val art1 = Articulo_V4("Libro Kotlin") // Constructor primario, usa valores por defecto para los demás
    val art2 = Articulo_V4("Teclado", 75.0) // Constructor primario, precio personalizado
    val art3 = Articulo_V4("Monitor", 150.0, "Electrónica") // Constructor primario, todo personalizado
    val art4 = Articulo_V4("USB") // Constructor secundario sin precio ni categoría
    val art5 = Articulo_V4("Ratón", 25.0) // Constructor secundario con precio
    println("Art. 1: ${art1.nombre} (${art1.precio}€) [${art1.categoria}]")
    println("Art. 2: ${art2.nombre} (${art2.precio}€) [${art2.categoria}]")
    println("Art. 3: ${art3.nombre} (${art3.precio}€) [${art3.categoria}]")
    println("Art. 4 (Secundario): ${art4.nombre} (${art4.precio}€) [${art4.categoria}]")
    println("Art. 5 (Secundario): ${art5.nombre} (${art5.precio}€) [${art5.categoria}]")
    println("\n")

    println("--- 5. Data Class 'Coordenada_V5' (La forma más concisa para datos) ---")
    val coord1 = Coordenada_V5(10, 20)
    val coord2 = Coordenada_V5(5, 15)
    val coord3 = Coordenada_V5(10, 20) // Mismos valores que coord1

    println("Coord1: $coord1") // toString() automático
    println("Coord2: $coord2")
    println("¿Coord1 == Coord3? ${coord1 == coord3}") // equals() automático (contenido)
    println("¿Coord1 == Coord2? ${coord1 == coord2}")
    println("Mover Coord1 a (12, 22): ${coord1.mover(2, 2)}") // Método
    val coord1_copy = coord1.copy(y = 25) // copy() automático, cambia solo 'y'
    println("Coord1 (copia con Y diferente): $coord1_copy")
    println("\n")
}

// --- Definición de Clases (Evolución) ---

/**
 * 1. Clase 'Usuario_V1' (Estilo "Java-like")
 * - Propiedades declaradas como campos privados.
 * - Constructor explícito para inicializarlas.
 * - Getters y Setters definidos manualmente para acceso.
 */
class Usuario_V1(nombreParam: String, emailParam: String) {
    private var nombre: String // Propiedad mutable (var)
    private val email: String // Propiedad inmutable (val)

    init { // Bloque de inicialización que se ejecuta al crear el objeto
        this.nombre = nombreParam
        this.email = emailParam
        println("Usuario_V1 creado: ${this.nombre}")
    }

    fun getNombre(): String { return nombre } // Getter manual
    fun setNombre(nuevoNombre: String) { this.nombre = nuevoNombre } // Setter manual
    fun getEmail(): String { return email } // Getter manual

    fun generarSaludo(): String = "Hola, soy ${nombre}." // Método simple
}

/**
 * 2. Clase 'Usuario_V2' (Kotlin: Constructor Primario con Propiedades)
 * - ¡Mucho más conciso! Las propiedades se declaran directamente en el constructor primario.
 * - 'val' crea una propiedad inmutable con getter automático.
 * - 'var' crea una propiedad mutable con getter y setter automáticos.
 */
class Usuario_V2(var nombre: String, val email: String) { // 'var' para nombre, 'val' para email
    init {
        println("Usuario_V2 creado: ${this.nombre}")
    }
    fun generarSaludo(): String = "Hola, soy ${nombre} (versión Kotlin)."
}

/**
 * 3. Clase 'Rectangulo_V3' (Kotlin: Propiedades Calculadas)
 * - Una propiedad calculada no almacena un valor, sino que lo calcula cada vez que se accede a ella.
 * - Se definen con un bloque 'get()'. No necesitan un campo de respaldo.
 */
class Rectangulo_V3(var ancho: Double, var alto: Double) {
    // Propiedad calculada 'area': su valor se obtiene de 'ancho * alto'
    val area: Double
        get() = ancho * alto // Cada vez que se accede a 'area', se ejecuta esta expresión.

    // Otra propiedad calculada 'perimetro'
    val perimetro: Double
        get() {
            println("Calculando perímetro...") // Podemos añadir lógica aquí
            return 2 * (ancho + alto)
        }

    init {
        println("Rectángulo_V3 creado: ${ancho}x${alto}")
    }
}

/**
 * 4. Clase 'Articulo_V4' (Kotlin: Constructor Secundario y Valores por Defecto)
 * - Muestra la flexibilidad de constructores primarios con valores por defecto
 *   y constructores secundarios que delegan al primario.
 */
class Articulo_V4(
    val nombre: String,
    val precio: Double = 0.0, // Valor por defecto para precio
    val categoria: String = "General" // Valor por defecto para categoría
) {
    init {
        println("Articulo_V4 (Primario) creado: $nombre")
    }

    // Constructor secundario 1: Sólo recibe el nombre y delega al primario con valores por defecto.
    constructor(nombre: String) : this(nombre, 0.0, "Desconocida") {
        println("Articulo_V4 (Secundario: Solo nombre) creado.")
    }

    // Constructor secundario 2: Recibe nombre y precio, delega al primario con categoría por defecto.
    constructor(nombre: String, precio: Double) : this(nombre, precio, "Variado") {
        println("Articulo_V4 (Secundario: Nombre y precio) creado.")
    }
}


/**
 * 5. Data Class 'Coordenada_V5' (La forma más concisa para Clases de Datos)
 * - Diseñadas para almacenar datos.
 * - El compilador genera automáticamente: `equals()`, `toString()`, `copy()`.
 * - Propiedades deben estar en el constructor primario.
 */
data class Coordenada_V5(val x: Int, val y: Int) {
    init {
        println("Coordenada_V5 creada: ($x, $y)")
    }

    fun mover(dx: Int, dy: Int): Coordenada_V5 {
        return Coordenada_V5(x + dx, y + dy) // Retorna una nueva coordenada (inmutabilidad)
    }
}