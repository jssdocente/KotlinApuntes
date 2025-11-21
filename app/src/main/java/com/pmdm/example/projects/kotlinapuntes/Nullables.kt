package com.pmdm.example.projects.kotlinapuntes

// NullablesCorregidosEnKotlin.kt

fun main() {
    println("--- Ejercicio 1: Declaración y Uso Básico de Nullables ---")
    ejercicio1DeclaracionUsoBasico()

    println("\n--- Ejercicio 2: Operador de Llamada Segura (?.) en Acción ---")
    ejercicio2LlamadaSeguraEnAccion()

    println("\n--- Ejercicio 3: Operador Elvis (?:) para Valores por Defecto ---")
    ejercicio3OperadorElvisParaDefecto()

    println("\n--- Ejercicio 4: Verificación Explícita de Nulos (if != null) ---")
    ejercicio4VerificacionExplicita()

    println("\n--- Ejercicio 5: El Operador de Afirmación No Nula (!!) (Con Precaución) ---")
    ejercicio5AfirmacionNoNula()

    println("\n--- Ejercicio 6: Nullables en Funciones y Colecciones ---")
    ejercicio6NullablesEnFuncionesYColecciones()

    println("\n--- Ejercicio 7: Nullables y Conversiones Seguras ---")
    ejercicio7NullablesYConversionesSeguras()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 1: Declaración y Uso Básico de Nullables
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** En Kotlin, para declarar una variable que *puede* contener `null`,
 * se añade un signo de interrogación `?` al final de su tipo (ej. `String?`, `Int?`, `MyClass?`).
 * Si no se añade `?`, la variable es `non-nullable` y el compilador impedirá que se le asigne `null`.
 *
 * **Utilidad:** Esta es la base de la seguridad contra `NullPointerException`. El compilador nos
 * obliga a reconocer y manejar los casos en los que un valor podría no estar presente.
 */
fun ejercicio1DeclaracionUsoBasico() {
    // 1. Declaración de un tipo non-nullable (no puede ser null)
    var nombreCompleto: String = "Alice Smith"
    // nombreCompleto = null // Esto generaría un ERROR DE COMPILACIÓN

    println("Nombre completo (non-nullable): $nombreCompleto")

    // 2. Declaración de un tipo nullable (puede ser null)
    var segundoNombre: String? = "Jane"
    println("Segundo nombre inicial (nullable): $segundoNombre")

    segundoNombre = null // Esto es VÁLIDO para un tipo nullable
    println("Segundo nombre después de asignar null: $segundoNombre")

    // 3. Tipos numéricos también pueden ser nullable
    var puntos: Int? = 150
    println("Puntos iniciales: $puntos")

    puntos = null
    println("Puntos después de asignar null: $puntos")

    // 4. Intentar usar un valor nullable sin seguridad NO COMPILA
    // val longitud: Int = segundoNombre.length // ERROR DE COMPILACIÓN
    // El compilador te fuerza a manejar la posibilidad de que 'segundoNombre' sea null.
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 2: Operador de Llamada Segura (?.) en Acción
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** El operador `?.` es la forma más común y segura de acceder a propiedades o
 * métodos de un objeto que podría ser `null`.
 * - Si el objeto a la izquierda de `?.` **no es `null`**, la operación se ejecuta.
 * - Si el objeto a la izquierda de `?.` **es `null`**, la expresión completa devuelve `null`
 *   inmediatamente, sin intentar la operación, previniendo un `NullPointerException`.
 *
 * **Utilidad:** Permite encadenar llamadas a propiedades y métodos de forma concisa y segura
 * en situaciones donde cualquier parte de la cadena podría ser `null`.
 */
fun ejercicio2LlamadaSeguraEnAccion() {
    var ciudad: String? = "Barcelona"
    val longitudCiudad: Int? = ciudad?.length // Si ciudad es null, longitudCiudad será null
    println("Longitud de '$ciudad': $longitudCiudad")

    ciudad = null
    val longitudCiudadNula: Int? = ciudad?.length // Ahora longitudCiudadNula es null
    println("Longitud de '$ciudad': $longitudCiudadNula")

    println("--- Encadenamiento de llamadas seguras ---")

    data class Direccion(val calle: String, val numero: Int?)
    data class Persona(val nombre: String, val direccion: Direccion?)
    // Corregido: El campo 'numero' en Direccion ahora es Int?

    val persona1 = Persona("Laura", Direccion("Calle Mayor", 15))
    val persona2 = Persona("Carlos", null) // Sin dirección
    val persona3 = Persona("Maria", Direccion("Avenida Central", null)) // Dirección sin número

    // Acceder a la calle de persona1: direccion no es null, se accede a calle
    val callePersona1: String? = persona1.direccion?.calle
    val numeroPersona1: Int? = persona1.direccion?.numero
    println("${persona1.nombre}: Calle: $callePersona1, Número: $numeroPersona1")

    // Acceder a la calle de persona2: direccion es null, la expresión completa ?.calle devuelve null
    val callePersona2: String? = persona2.direccion?.calle
    val numeroPersona2: Int? = persona2.direccion?.numero
    println("${persona2.nombre}: Calle: $callePersona2, Número: $numeroPersona2")

    // Acceder al número de persona3: direccion no es null, pero numero sí lo es
    val callePersona3: String? = persona3.direccion?.calle
    val numeroPersona3: Int? = persona3.direccion?.numero
    println("${persona3.nombre}: Calle: $callePersona3, Número: $numeroPersona3")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 3: Operador Elvis (?:) para Valores por Defecto
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** El operador Elvis `?:` (inspirado en el peinado de Elvis, que es como un
 * "si no hay uno, toma el otro") proporciona un valor por defecto cuando la expresión
 * de su izquierda es `null`.
 * `expresionNullable ?: valorPorDefectoNonNullable`
 * - Si `expresionNullable` **no es `null`**, se devuelve `expresionNullable`.
 * - Si `expresionNullable` **es `null`**, se devuelve `valorPorDefectoNonNullable`.
 * El resultado final de la expresión con Elvis es siempre un tipo `non-nullable`.
 *
 * **Utilidad:** Ideal para convertir tipos `nullable` en `non-nullable` de forma concisa,
 * proporcionando un valor predeterminado cuando los datos esperados no están presentes.
 */
fun ejercicio3OperadorElvisParaDefecto() {
    var nombreDeUsuario: String? = "usuario123"
    val nombreParaMostrar1: String = nombreDeUsuario ?: "Anónimo" // nombreDeUsuario no es null
    println("Nombre para mostrar 1: $nombreParaMostrar1")

    nombreDeUsuario = null
    val nombreParaMostrar2: String = nombreDeUsuario ?: "Anónimo" // nombreDeUsuario es null, usa "Anónimo"
    println("Nombre para mostrar 2: $nombreParaMostrar2")

    println("--- Combinando llamada segura y Elvis ---")

    data class Item(val id: String, val descripcion: String?, val pesoKg: Double?)

    val item1 = Item("A001", "Caja de herramientas", 5.0)
    val item2 = Item("B002", null, 2.5) // Sin descripción
    val item3 = Item("C003", "Documentos importantes", null) // Sin peso

    // Obtener la descripción o un valor por defecto
    val descItem1: String = item1.descripcion ?: "Sin descripción disponible"
    val descItem2: String = item2.descripcion ?: "Sin descripción disponible"
    println("${item1.id}: Descripción: $descItem1")
    println("${item2.id}: Descripción: $descItem2")

    // Obtener el peso o un valor por defecto (0.0 si es nulo)
    val pesoItem1: Double = item1.pesoKg ?: 0.0
    val pesoItem3: Double = item3.pesoKg ?: 0.0
    println("${item1.id}: Peso: $pesoItem1 kg")
    println("${item3.id}: Peso: $pesoItem3 kg")

    // También se puede usar en una cadena de llamadas
    val primeraLetra: Char = item1.descripcion?.first() ?: '?'
    println("Primera letra de descripción de ${item1.id}: $primeraLetra")

    val primeraLetraNull: Char = item2.descripcion?.first() ?: '?'
    println("Primera letra de descripción de ${item2.id} (nula): $primeraLetraNull")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 4: Verificación Explícita de Nulos (if != null)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Cuando sabemos que una variable `nullable` no es `null` después de una comprobación,
 * Kotlin realiza un "smart cast" y la trata como un tipo `non-nullable` dentro de ese bloque.
 *
 * **Utilidad:** Es la forma más explícita y segura de manejar nulos, especialmente cuando necesitas
 * realizar múltiples operaciones con un objeto que podría ser `null` o cuando la lógica de manejo
 * de nulos es más compleja que un simple valor por defecto.
 */
fun ejercicio4VerificacionExplicita() {
    var mensajeDeError: String? = "Error de conexión con el servidor."

    // Comprobación explícita de nulos
    if (mensajeDeError != null) {
        // Dentro de este bloque, 'mensajeDeError' se considera String (non-nullable)
        println("Se detectó un error: $mensajeDeError")
        println("Longitud del mensaje de error: ${mensajeDeError.length}") // Acceso directo a .length
        println("El mensaje en mayúsculas: ${mensajeDeError.toUpperCase()}")
    } else {
        println("No hay ningún mensaje de error.")
    }

    println("--- Variando el valor ---")
    mensajeDeError = null // Asignamos null
    if (mensajeDeError != null) {
        println("No debería entrar aquí.")
    } else {
        println("Ahora el mensaje de error es nulo.")
    }

    // Otra forma de usarlo para ejecutar un bloque de código
    fun procesarConfiguracion(config: Map<String, String>?) {
        config?.let {
            // 'it' dentro de 'let' es la configuración (non-nullable Map<String, String>)
            println("Configuración encontrada:")
            it.forEach { (key, value) -> println("  $key: $value") }
        } ?: run {
            // Si 'config' es null, se ejecuta este bloque
            println("No se encontró ninguna configuración.")
        }
    }

    val miConfig = mapOf("idioma" to "es", "tema" to "oscuro")
    procesarConfiguracion(miConfig)

    val noConfig = null
    procesarConfiguracion(noConfig)
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 5: El Operador de Afirmación No Nula (!!) (Con Precaución)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** El operador `!!` convierte un tipo `nullable` en un tipo `non-nullable`.
 * Le dice al compilador: "Estoy ABSOLUTAMENTE SEGURO de que este valor no es `null`".
 * - Si el valor **no es `null`**, la operación procede normalmente.
 * - Si el valor **ES `null`**, se lanza un `NullPointerException` en tiempo de ejecución.
 *
 * **Utilidad:** Debe usarse con extrema cautela y solo cuando tienes una garantía absoluta
 * de que el valor no será `null` (por ejemplo, después de una validación exhaustiva o en
 * el contexto de tests). Su uso excesivo o incorrecto anula la seguridad de nulos de Kotlin.
 * **Evitadlo siempre que podáis.**
 */
fun ejercicio5AfirmacionNoNula() {
    var nombreDelArchivo: String? = "documento.txt"

    // Uso de !!: asumimos que nombreDelArchivo no es null
    val nombreLimpio = nombreDelArchivo!!.substringBefore(".")
    println("Nombre limpio del archivo: $nombreLimpio")

    println("--- Provocando un NPE con '!!' ---")

    var pathDeUsuario: String? = null // ¡Ahora es null!

    try {
        // Esto provocará un NullPointerException
        val carpetaRaiz = pathDeUsuario!!.substringBefore("/")
        println("Esta línea nunca se ejecutará: $carpetaRaiz")
    } catch (e: NullPointerException) {
        println("¡Capturado el NullPointerException! El operador '!!' falló porque el valor era null.")
        println("Mensaje de error: ${e.message}")
    }

    println("--- Cuándo usarlo (ejemplo raro y desaconsejado) ---")
    // Solo en situaciones muy específicas donde la lógica externa garantiza la no-nulidad
    // y quieres fallar ruidosamente si esa garantía se rompe.
    // Un ejemplo de uso justificado podría ser en un test unitario donde se espera un NPE.
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 6: Nullables en Funciones y Colecciones
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Los tipos `nullable` se integran en las firmas de funciones (parámetros y retornos)
 * y en las colecciones, permitiendo modelar datos del mundo real de forma precisa.
 *
 * **Utilidad:** Crear APIs que reflejen la opcionalidad de los datos, y manipular colecciones
 * que pueden contener elementos `null` de manera segura y eficiente.
 */
// Función que acepta un String? y devuelve un Int?
fun obtenerLongitudSegura(texto: String?): Int? {
    return texto?.length // Si texto es null, ?.length devuelve null. El tipo de retorno es Int?.
}

// Función que acepta un String? y devuelve un String (non-nullable)
fun generarMensajeBienvenida(nombre: String?): String {
    val nombreParaSaludo = nombre ?: "Usuario anónimo" // Usamos Elvis para asegurar un String non-nullable
    return "¡Bienvenido, $nombreParaSaludo!"
}

// Data class con propiedades nullable
data class Tarea(val id: Int, val titulo: String, var fechaLimite: String?, var completadaPor: String?)

fun ejercicio6NullablesEnFuncionesYColecciones() {
    println("Longitud de 'Desarrollo': ${obtenerLongitudSegura("Desarrollo")}")
    println("Longitud de null: ${obtenerLongitudSegura(null)}")

    println("---")

    println(generarMensajeBienvenida("Marta"))
    println(generarMensajeBienvenida(null))

    println("--- Tareas con datos opcionales ---")
    val tarea1 = Tarea(1, "Configurar entorno", "2023-12-31", null)
    val tarea2 = Tarea(2, "Diseñar UI", null, "Ana Lopez")
    val tarea3 = Tarea(3, "Implementar backend", "2024-01-15", "Juan Perez")

    // Acceso seguro a propiedades nullable
    println("Tarea 1: Fecha límite: ${tarea1.fechaLimite ?: "No especificada"}, Completada por: ${tarea1.completadaPor ?: "Nadie"}")
    println("Tarea 2: Fecha límite: ${tarea2.fechaLimite ?: "No especificada"}, Completada por: ${tarea2.completadaPor ?: "Nadie"}")

    println("--- Colecciones con elementos nullables ---")
    val posiblesValores: List<String?> = listOf("Kotlin", null, "Compose", "Android", null)

    println("Valores originales: $posiblesValores")

    // Filtrar elementos que NO son null
    val valoresNoNulos: List<String> = posiblesValores.filterNotNull()
    println("Valores no nulos (filterNotNull): $valoresNoNulos")

    // Mapear elementos y generar nulls, luego filtrarlos
    val longitudesDeValores: List<Int?> = posiblesValores.map { it?.length }
    println("Longitudes (incluye nulls): $longitudesDeValores")

    val longitudesNoNulas: List<Int> = posiblesValores.mapNotNull { it?.length }
    println("Longitudes (mapNotNull elimina nulls): $longitudesNoNulas")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 7: Nullables y Conversiones Seguras
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Kotlin proporciona funciones de extensión seguras para conversiones, como `toIntOrNull()`,
 * `toDoubleOrNull()`, etc., que devuelven `null` si la conversión falla, en lugar de lanzar una excepción.
 *
 * **Utilidad:** Muy útil al procesar entradas de usuario o datos externos que pueden no tener el formato
 * esperado, permitiendo un manejo de errores elegante y sin interrupciones.
 */
fun ejercicio7NullablesYConversionesSeguras() {
    val inputNumero1 = "123"
    val numero1: Int? = inputNumero1.toIntOrNull()
    println("Entrada '$inputNumero1': $numero1 (tipo: ${numero1?.javaClass?.simpleName ?: "null"})")

    val inputNumero2 = "abc"
    val numero2: Int? = inputNumero2.toIntOrNull() // Devuelve null porque "abc" no es un Int
    println("Entrada '$inputNumero2': $numero2 (tipo: ${numero2?.javaClass?.simpleName ?: "null"})")

    println("--- Combinando con Elvis ---")
    fun obtenerEdadValida(input: String?): Int {
        // Convierte a Int, si falla devuelve null. Si el input era null, también devuelve null.
        // Si el resultado final es null, Elvis lo reemplaza con 0.
        return input?.toIntOrNull() ?: 0
    }

    println("Edad de '25': ${obtenerEdadValida("25")}")
    println("Edad de 'cuarenta': ${obtenerEdadValida("cuarenta")}")
    println("Edad de null: ${obtenerEdadValida(null)}")
}