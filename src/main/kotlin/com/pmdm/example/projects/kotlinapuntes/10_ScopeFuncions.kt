package com.pmdm.example.projects.kotlinapuntes

/**
 * ========================================================================================
 * APUNTES: SCOPE FUNCTIONS (Funciones de Alcance)
 * ========================================================================================
 *
 * Las Scope Functions nos permiten ejecutar un bloque de código dentro del contexto de un objeto.
 * Su principal objetivo es hacer el código más conciso y legible.
 *
 * Hay 5 funciones principales: `let`, `run`, `with`, `apply`, `also`.
 *
 * ¿En qué se diferencian?
 * 1. ¿Cómo hacen referencia al objeto dentro del bloque?
 *    - `this` (Receptor Lambda): run, with, apply
 *    - `it` (Argumento Lambda): let, also
 *
 * 2. ¿Qué devuelve la función?
 *    - El resultado de la lambda (la última línea): let, run, with
 *    - El objeto de contexto mismo (el objeto original): apply, also
 *
 * REGLA NEMOTÉCNICA RÁPIDA:
 * - Quiero configurar el objeto y devolverlo -> apply
 * - Quiero hacer algo con el objeto y devolver otra cosa -> let
 * - Quiero hacer operaciones adicionales (logs) sin cambiar el flujo -> also
 */

fun main() {
    println("=== PARTE 1: FUNDAMENTOS DE KOTLIN PURO ===\n")

    ejercicio1LetNullsafety()
    ejercicio2_Apply_Configuracion()
    ejercicio3_Run_Y_With_BloquesLogicos()
    ejercicio4_Also_EfectosSecundarios()

    println("\n=== PARTE 2: ENFOQUE HACIA JETPACK COMPOSE ===\n")
    ejercicio5_Compose_UiModels()
    ejercicio6_Compose_NavegacionSimulada()
}

// ----------------------------------------------------------------------------------------
// CLASES DE APOYO PARA LOS EJERCICIOS
// ----------------------------------------------------------------------------------------
data class UsuarioApp(
    var nombre: String,
    var edad: Int,
    var ciudad: String = "Desconocida",
    var email: String = "",
    var bio: String = "",
    var esVip: Boolean = false,
    var puntuacion: Int = 0
) {
    fun mover(nuevaCiudad: String) {
        ciudad = nuevaCiudad
    }

    fun incrementarPuntuacion(puntos: Int) {
        puntuacion += puntos
    }
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 1: 'let'
// Uso principal: Ejecutar código solo si no es nulo y mapear resultados.
// Contexto: 'it' | Retorno: Resultado de la lambda
// ----------------------------------------------------------------------------------------
fun ejercicio1LetNullsafety() {
    println("--- Ejercicio 1: 'let' para seguridad de nulos y transformaciones ---")

    val usuarioNulable: UsuarioApp? = UsuarioApp("Profe", 40, email = "profe@pmdm.com")
    val nulo: UsuarioApp? = null

    // Estilo clásico (Java-like)
    if (usuarioNulable != null) {
        println("Clásico: El usuario ${usuarioNulable.nombre} tiene ${usuarioNulable.email}")
    }

    // Estilo Kotlin con 'let'
    // El bloque solo se ejecuta si la variable NO es nula.
    usuarioNulable?.let {
        // Aquí dentro, 'it' es el objeto UsuarioApp no nulo
        println("Con let: Procesando a '${it.nombre}' con email '${it.email}'")
        it.incrementarPuntuacion(10)
    }

    // Con el valor nulo, no se imprime nada
    nulo?.let {
        println("Esto no se imprimirá nunca para $it")
    }

    // 'let' también sirve para transformar el objeto en otra cosa y devolverlo
    val resumenUsuario = usuarioNulable?.let {
        "Resumen: ${it.nombre} (${it.edad}) vive en ${it.ciudad}" // La última línea es lo que se devuelve
    } ?: "Usuario inexistente"

    println("Resultado final: $resumenUsuario")
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 2: 'apply'
// Uso principal: Inicializar o configurar un objeto de forma exhaustiva.
// Contexto: 'this' | Retorno: El objeto mismo
// ----------------------------------------------------------------------------------------
fun ejercicio2_Apply_Configuracion() {
    println("\n--- Ejercicio 2: 'apply' para inicialización de objetos ---")

    // Sin apply: La inicialización es dispersa y requiere repetir el nombre de la variable
    val usuario1 = UsuarioApp("Pepe", 30)
    usuario1.ciudad = "Madrid"
    usuario1.email = "pepe@example.com"
    usuario1.bio = "Amante de la tecnología y el senderismo."
    usuario1.esVip = true
    usuario1.mover("Valencia")
    usuario1.incrementarPuntuacion(100)

    // Con apply: Agrupamos toda la configuración en un bloque atómico y legible.
    // Muy útil para configurar objetos complejos (UI, Entidades, Configs) antes de usarlos.
    val usuario2 = UsuarioApp("Laura", 25).apply {
        // Dentro usamos 'this' (implícito), accedemos a propiedades y métodos directamente.
        ciudad = "Barcelona"
        email = "laura@dev.com"
        bio = "Desarrolladora Android con ganas de aprender Kotlin."
        esVip = false
        puntuacion = 500
        
        mover("Sevilla")
        incrementarPuntuacion(50)
        
        println("Dentro del apply: Configurando a $nombre...")
    }

    println("Usuario 1 (Estilo tradicional):\n   $usuario1")
    println("Usuario 2 (Estilo Kotlin 'apply'):\n   $usuario2")
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 3: 'run' y 'with'
// Uso principal: Ejecutar un bloque de código y calcular un resultado.
// Contexto: 'this' | Retorno: Resultado de la lambda
// ----------------------------------------------------------------------------------------
fun ejercicio3_Run_Y_With_BloquesLogicos() {
    println("\n--- Ejercicio 3: 'run' y 'with' para procesamiento y retorno ---")

    val usuario = UsuarioApp("Carlos", 40, "Bilbao", email = "carlos@auth.com")

    // WITH: No es una función de extensión. Se pasa el objeto como parámetro.
    // Se lee: "Con este objeto, haz esto..."
    // Ideal para agrupar llamadas a métodos de un objeto cuando no necesitas el objeto de vuelta.
    val tarjetaPresentacion = with(usuario) {
        incrementarPuntuacion(20)
        "TARJETA: $nombre | $email | Ciudad: $ciudad | Puntos: $puntuacion" // Última línea retorna
    }
    println(tarjetaPresentacion)

    // RUN: Es igual que 'with' pero ES una función de extensión.
    // Muy potente para: 1. Comprobación de nulos + bloque de código. 2. Inicializar y calcular algo.
    val esCandidatoVip = usuario.run {
        println("Analizando perfil de $nombre para ascenso a VIP...")
        incrementarPuntuacion(100)
        puntuacion > 100 && edad >= 18 // Retorna un booleano
    }
    println("¿Es candidato a VIP? $esCandidatoVip")

    // Ejemplo de 'run' sin receptor (bloque aislado para limitar el alcance de variables)
    val resultadoOperacion = run {
        val x = 10
        val y = 20
        println("Calculando algo interno en run: ${x + y}")
        x + y
    }
    println("Resultado run aislado: $resultadoOperacion")
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 4: 'also'
// Uso principal: Efectos secundarios (logs, debug) sin alterar el flujo.
// Contexto: 'it' | Retorno: El objeto mismo
// ----------------------------------------------------------------------------------------
fun ejercicio4_Also_EfectosSecundarios() {
    println("\n--- Ejercicio 4: 'also' para efectos secundarios y validaciones encadenadas ---")

    // Escenario: Creamos un usuario, realizamos una acción, logueamos el estado intermedio
    // y seguimos configurándolo.
    val usuario = UsuarioApp("Marcos", 19)
        .also { println("LOG: Iniciando creación de usuario: $it") }
        .apply {
            email = "marcos@test.es"
            bio = "Nuevo miembro"
        }
        .also { println("LOG: Usuario configurado antes de puntuación: ${it.nombre}, Puntos: ${it.puntuacion}") }
        .apply { incrementarPuntuacion(10) }
        .also { println("LOG: Proceso finalizado para: ${it.email}") }

    println("Referencia final lista para usar: $usuario")

    // Otro ejemplo común con colecciones
    val numeros = mutableListOf(5, 2, 9, 1)
        .also { println("Lista original: $it") }
        .apply { sort() }
        .also { println("Lista ordenada: $it") }
}

// ========================================================================================
// PARTE 2: ENFOQUE HACIA COMPOSE
// ========================================================================================
/*
    En Compose, las Scope Functions son vitales para:
    1. Modificar estados de UI de forma segura.
    2. Configurar ViewModels o UiStates.
    3. Navegación y paso de argumentos.
*/

// Simulamos una clase de configuración de UI (típico UiState expandido)
data class ComposeUiState(
    var isLoading: Boolean = false,
    var userProfile: UsuarioApp? = null,
    var data: List<String> = emptyList(),
    var error: String? = null,
    var lastUpdate: Long = 0L
)

// ----------------------------------------------------------------------------------------
// EJERCICIO 5: Configuración de UiState (Simulación ViewModel)
// ----------------------------------------------------------------------------------------
fun ejercicio5_Compose_UiModels() {
    println("--- Ejercicio 5: Compose - Manipulación de UiState complejo ---")

    // Escenario: Recibimos datos de una API y queremos actualizar el estado completo.
    val currentState = ComposeUiState(isLoading = true)
    println("Estado inicial: $currentState")

    // Simulamos respuesta de API
    val usuarioApi = UsuarioApp("Admin", 30, "Málaga", "admin@api.com")
    val listaProductos = listOf("Portátil", "Ratón", "Teclado")

    // Usamos 'apply' para realizar una actualización multivariable clara
    val newState = currentState.copy().apply {
        isLoading = false
        userProfile = usuarioApi
        data = listaProductos
        error = null
        lastUpdate = System.currentTimeMillis()
        
        println("Actualizando estado de UI para el usuario: ${userProfile?.nombre}")
    }

    println("Estado actualizado: $newState")

    // Escenario: Renderizar UI condicionalmente con 'let' y 'run'
    // Si hay usuario, mostramos su bio; si no, un mensaje genérico.
    newState.userProfile?.let {
        println("MOSTRANDO PERFIL: ${it.nombre} - ${it.bio.ifEmpty { "Sin biografía" }}")
    } ?: run {
        println("MOSTRANDO PANTALLA DE INICIO DE SESIÓN")
    }
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 6: Navegación y Argumentos (Simulación)
// ----------------------------------------------------------------------------------------
fun ejercicio6_Compose_NavegacionSimulada() {
    println("\n--- Ejercicio 6: Compose - Navegación y Argumentos ---")

    // Escenario: Queremos navegar a un detalle, pero antes queremos loguear
    // la acción para analíticas (Firebase, etc.) sin romper la cadena.

    data class Ruta(val path: String, val args: String?)

    fun navegar(ruta: Ruta) {
        println(">> Navegando internamente a: ${ruta.path}/${ruta.args}")
    }

    val idProductoSeleccionado = "PROD-123"

    // Creamos la ruta, la logueamos con 'also' y navegamos con 'run' o llamada directa
    Ruta("detalle", idProductoSeleccionado)
        .also { println("ANALYTICS: Usuario intenta ir al detalle de ${it.args}") }
        .run { navegar(this) }

    // Escenario: Argumentos nulos en navegación
    // Típico en Compose: backStackEntry.arguments?.getString("id")
    val argumentoRecibido: String? = null

    // Validamos y usamos 'run' para lógica de error temprana o fallback
    val idValido = argumentoRecibido ?: run {
        println("INFO: El argumento ID es nulo. Generando ID temporal...")
        "TEMP-${System.currentTimeMillis()}" // Generamos un valor por defecto en el run
    }

    println("ID Final procesado: $idValido")
}