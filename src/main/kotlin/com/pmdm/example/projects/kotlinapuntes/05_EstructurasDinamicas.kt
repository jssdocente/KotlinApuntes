package com.pmdm.example.projects.kotlinapuntes

/**
 * ---------------------------------------------------------
 * DEFINICIÓN DE MODELO DE DATOS
 * ---------------------------------------------------------
 * Usamos 'data class' porque nos regala el método toString(),
 * equals() y copy() automáticamente.
 */
data class Videojuego(
    val id: Int,
    val titulo: String,
    val precio: Double,
    val plataforma: String
)

// ---------------------------------------------------------
// FUNCIÓN PRINCIPAL (Punto de entrada)
// ---------------------------------------------------------
fun main() {
    println("=== INICIO DEL REPASO DE ESTRUCTURAS DINÁMICAS ===\n")

    ejercicio1_ListasBasicas()
    ejercicio2_MapasDiccionarios()
    ejercicio3_FiltradoYTransformacion()
    ejercicio4_GestionDeClases()

    println("\n=== FIN DEL REPASO ===")
}

/**
 * ---------------------------------------------------------
 * EJERCICIO 1: LISTAS MUTABLES
 * Objetivo: Crear, añadir y eliminar objetos de una lista.
 * ---------------------------------------------------------
 */
fun ejercicio1_ListasBasicas() {
    println("--- Ejercicio 1: Listas Mutables ---")

    // PASO 1: Crear una lista mutable vacía tipada
    // "Quiero una lista que SOLO acepte Videojuegos"
    val inventario: MutableList<Videojuego> = mutableListOf()

    // PASO 2: Añadir elementos
    inventario.add(Videojuego(1, "The Legend of Zelda", 59.99, "Switch"))
    inventario.add(Videojuego(2, "God of War", 49.90, "PS5"))
    inventario.add(Videojuego(3, "Halo Infinite", 69.99, "Xbox"))

    // PASO 3: Recorrer la lista (Iterar)
    println("Inventario inicial:")
    // 'juego' es una variable temporal que toma el valor de cada ítem
    for (juego in inventario) {
        println(" - ${juego.titulo} (${juego.precio}€)")
    }

    // PASO 4: Eliminar un elemento
    // Vamos a borrar el juego con id 3.
    // removeIf es muy potente: "Borra SI se cumple esta condición"
    val eliminado = inventario.removeIf { it.id == 3 }

    if (eliminado) {
        println("\n>> Se ha eliminado el juego de Xbox.")
    }

    println("Total juegos actuales: ${inventario.size}\n")
}

/**
 * ---------------------------------------------------------
 * EJERCICIO 2: MAPAS (DICCIONARIOS)
 * Objetivo: Asociar claves únicas a objetos.
 * Situación: Queremos buscar un juego rápido por su ID sin recorrer toda la lista.
 * ---------------------------------------------------------
 */
fun ejercicio2_MapasDiccionarios() {
    println("--- Ejercicio 2: Mapas (Clave -> Valor) ---")

    // PASO 1: Crear datos de prueba
    val j1 = Videojuego(10, "Minecraft", 20.0, "PC")
    val j2 = Videojuego(11, "Elden Ring", 60.0, "PC")

    // PASO 2: Crear un Mapa.
    // Clave: Int (el ID), Valor: Videojuego (el objeto completo)
    val catalogoMap: MutableMap<Int, Videojuego> = mutableMapOf()

    // Insertar valores (se usa como un array pero con la clave que quieras)
    catalogoMap[j1.id] = j1
    catalogoMap[11] = j2 // Podemos poner la clave literal

    // PASO 3: Acceso rápido
    // Imagina que el usuario pulsa en el juego con ID 10
    val idBuscado = 10
    val juegoEncontrado = catalogoMap[idBuscado] // Esto devuelve Videojuego? (Nulable)

    // Usamos el operador seguro del repaso anterior
    println("Buscando ID $idBuscado: ${juegoEncontrado?.titulo ?: "No encontrado"}")

    // PASO 4: Recorrer un mapa (Clave y Valor)
    println("Listado del mapa:")
    catalogoMap.forEach { (clave, valor) ->
        println("ID: $clave -> Juego: ${valor.titulo}")
    }
    println()
}

/**
 * ---------------------------------------------------------
 * EJERCICIO 3: FILTRADO Y TRANSFORMACIÓN (ESTILO FUNCIONAL)
 * Objetivo: Lo más usado en Compose. Convertir datos sin bucles 'for'.
 * ---------------------------------------------------------
 */
fun ejercicio3_FiltradoYTransformacion() {
    println("--- Ejercicio 3: Filter y Map ---")

    val listaJuegos = listOf(
        Videojuego(1, "Mario Kart", 45.0, "Switch"),
        Videojuego(2, "Spider-Man", 30.0, "PS5"),
        Videojuego(3, "Fortnite", 0.0, "Multi"),
        Videojuego(4, "Genshin Impact", 0.0, "Multi")
    )

    // CASO 1: FILTER
    // Queremos solo los juegos gratuitos (precio == 0.0)
    // filter crea una NUEVA lista, no modifica la original.
    val juegosGratis = listaJuegos.filter { it.precio == 0.0 }

    println("Juegos Gratuitos encontrados: ${juegosGratis.size}")
    juegosGratis.forEach { println(" * ${it.titulo}") }

    // CASO 2: MAP
    // Queremos solo una lista de NOMBRES (String) para mostrar en un menú simple.
    // Transformamos List<Videojuego> a List<String>
    val listaNombres = listaJuegos.map { it.titulo.uppercase() }

    println("Nombres en mayúsculas para la UI: $listaNombres")

    // CASO 3: Encadenamiento (La magia de Kotlin)
    // "Dame los nombres de los juegos de Switch que cuesten menos de 50€"
    val gangasSwitch = listaJuegos
        .filter { it.plataforma == "Switch" && it.precio < 50.0 }
        .map { it.titulo }

    println("Gangas de Switch: $gangasSwitch\n")
}

/**
 * ---------------------------------------------------------
 * EJERCICIO 4: SIMULACIÓN DE CARRITO (CLASE QUE GESTIONA LISTA)
 * Objetivo: Encapsulamiento. Una clase que contiene la lógica y la lista.
 * Esto es la antesala de un ViewModel en Android.
 * ---------------------------------------------------------
 */
class CarritoDeCompra {
    // La lista es privada. Nadie desde fuera puede borrar cosas "a mano".
    // Solo se puede modificar a través de las funciones (métodos).
    private val items = mutableListOf<Videojuego>()

    fun agregar(juego: Videojuego) {
        items.add(juego)
        println("🛒 ${juego.titulo} añadido al carrito.")
    }

    fun calcularTotal(): Double {
        // sumOf es una función maravillosa para sumar propiedades de objetos
        return items.sumOf { it.precio }
    }

    fun mostrarResumen() {
        if (items.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("--- Ticket de Compra ---")
            items.forEach { println("${it.titulo} .... ${it.precio}€") }
            println("TOTAL A PAGAR: ${calcularTotal()}€")
        }
    }
}

fun ejercicio4_GestionDeClases() {
    println("--- Ejercicio 4: Lógica de Negocio en Clases ---")

    val miCarrito = CarritoDeCompra()

    val j1 = Videojuego(1, "FIFA", 70.0, "PS5")
    val j2 = Videojuego(2, "Hollow Knight", 15.0, "Switch")

    miCarrito.agregar(j1)
    miCarrito.agregar(j2)

    // Intento acceder a la lista directamente:
    // miCarrito.items // ERROR! Es privada. Esto protege nuestros datos.

    miCarrito.mostrarResumen()
    println()
}