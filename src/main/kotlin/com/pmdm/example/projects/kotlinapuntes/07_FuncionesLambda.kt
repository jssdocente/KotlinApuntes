package com.pmdm.example.projects.kotlinapuntes

import kotlin.random.Random

fun main() {
    println("--- REPASO AVANZADO DE FUNCIONES LAMBDA EN KOTLIN ---")
    println("¡Hoy nos centramos en la sintaxis y el uso de lambdas como parámetros!")
    println("\n---------------------------------------------------------")

    ejercicio1DefinicionYUsoFuncionAnonima()
    println("\n---------------------------------------------------------")

    ejercicio2LambdaComoUltimoParametro()
    println("\n---------------------------------------------------------")

    ejercicio3TrailingLambdaYParametroUnicoIT()
    println("\n---------------------------------------------------------")

    ejercicio4CombinandoConceptos()
    println("\n---------------------------------------------------------")

    println("\n--- FIN DEL REPASO AVANZADO ---")
    println("¡Dominar esto os dará una gran ventaja en Compose!")
}

/**
 * Ejercicio 1: Definición y Uso de una Función Anónima
 *
 * Objetivo: Entender la sintaxis explícita de una función anónima.
 * Concepto clave: Aunque las lambdas son la forma más común, Kotlin permite definir funciones
 *                 anónimas con la palabra clave `fun` y un cuerpo de bloque.
 */
fun ejercicio1DefinicionYUsoFuncionAnonima() {
    println("EJERCICIO 1: Definición y Uso de una Función Anónima")
    println("--------------------------------------------------")

    // Una función anónima sin parámetros y que no devuelve nada (Unit)
    // Es muy similar a una lambda, pero se usa 'fun' y no los '->'
    val miFuncionAnonima: () -> Unit = fun() {
        println("¡Hola desde una función anónima explícita!")
    }
    miFuncionAnonima() // Llamamos a la función anónima

    // Una función anónima que recibe un Int y devuelve un String
    val describirNumero: (Int) -> String = fun(numero: Int): String {
        return if (numero % 2 == 0) {
            "El número $numero es par."
        } else {
            "El número $numero es impar."
        }
    }
    println(describirNumero(10))
    println(describirNumero(11))

    // Diferencia principal con una lambda: los 'return'
    // En una función anónima, 'return' devuelve de la propia función anónima.
    // En una lambda, por defecto, 'return' devuelve de la función que la contiene (non-local return).
    // Veremos las etiquetas de retorno más adelante para controlar esto en lambdas.

    println("\nReflexión: Aunque las lambdas son más concisas, las funciones anónimas son útiles si necesitas un 'return' local explícito.")
}

/**
 * Ejercicio 2: Lambda como Último Parámetro de una Función
 *
 * Objetivo: Mostrar cómo una función acepta una lambda como su último argumento.
 * Concepto clave: Cuando una lambda es el último parámetro, se puede usar la sintaxis de 'trailing lambda'.
 */
fun ejercicio2LambdaComoUltimoParametro() {
    println("EJERCICIO 2: Lambda como Último Parámetro de una Función")
    println("------------------------------------------------------")

    // Función que recibe un nombre y una acción a realizar con ese nombre.
    // La lambda es el último parámetro.
    fun saludarYEjecutar(nombre: String, accion: (String) -> Unit) {
        println("Preparando saludo para $nombre...")
        accion("Hola, $nombre!") // Ejecutamos la acción con el saludo
        println("Saludo completado.")
    }

    // Forma estándar: la lambda dentro de los paréntesis
    println("--- Sintaxis estándar de lambda como parámetro ---")
    saludarYEjecutar(
        "Alicia",
        accion = { mensajeCompleto ->
            println("  [Lambda Estándar] Recibido: $mensajeCompleto")
        }
    )

    // Forma de Trailing Lambda: si la lambda es el ÚLTIMO parámetro,
    // se puede sacar fuera de los paréntesis de la llamada a la función.
    println("\n--- Sintaxis de Trailing Lambda (¡MUY COMÚN EN COMPOSE!) ---")
    saludarYEjecutar("Bob")
    { mensajeCompleto ->
        println("  [Trailing Lambda] Recibido: $mensajeCompleto. ¡Qué tal!")
        // Aquí dentro, 'mensajeCompleto' es el String que le pasa 'saludarYEjecutar'
    }

    // Otro ejemplo: una función para aplicar un descuento
    fun aplicarDescuento(producto: String, precioOriginal: Double, calculoDescuento: (Double) -> Double): Double {
        val precioConDescuento = calculoDescuento(precioOriginal)
        println("Precio original de $producto: %.2f€".format(precioOriginal))
        println("Precio con descuento: %.2f€".format(precioConDescuento))
        return precioConDescuento
    }

    // Usando trailing lambda para definir el cálculo del descuento
    println("\n--- Aplicando descuentos con trailing lambda ---")
    aplicarDescuento("Camiseta", 25.0) { precio ->
        precio * 0.80 // Descuento del 20%
    }

    // Ejemplo Trailing Lambda con 'it' para el único parámetro
    aplicarDescuento("Pantalones", 50.0) {
        if (it > 40.0) it * 0.70 else it * 0.90 // Descuento del 30% si > 40€, si no 10%
    }

    //Ejemplo sin Trailing Lambda y con It.
    aplicarDescuento(
        producto = "Pantalones",
        precioOriginal = 50.0,
        calculoDescuento = {
            // Se devuelve la última línea ejecutada en una Lambda.
            if (it > 40.0)
                it * 0.70
            else
                it * 0.90;
        }
    )

    //Ejemplo sin Trailing Lambda y con un parámetro con nombre.
    aplicarDescuento(
        producto = "Pantalones",
        precioOriginal = 50.0,
        calculoDescuento = { precio ->
            // Se devuelve la última línea ejecutada en una Lambda.
            if (precio > 40.0)
                precio * 0.70
            else
                precio * 0.90;
        }
    )

    println("\nRecordatorio: La trailing lambda mejora mucho la legibilidad, haciendo que parezca un bloque de control del lenguaje.")
}

/**
 * Ejercicio 3: Trailing Lambda y 'it' (parámetro único implícito)
 *
 * Objetivo: Combinar la sintaxis de trailing lambda con el uso implícito de 'it' para el único parámetro.
 * Concepto clave: Si una lambda tiene UN SOLO parámetro y se usa como trailing lambda, se puede omitir
 *                 la declaración del parámetro y usar 'it'.
 */
fun ejercicio3TrailingLambdaYParametroUnicoIT() {
    println("EJERCICIO 3: Trailing Lambda y 'it' (parámetro único implícito)")
    println("-------------------------------------------------------------")

    // Reutilizamos la función 'saludarYEjecutar' del Ejercicio 2:
    fun saludarYEjecutar(nombre: String, accion: (String) -> Unit) {
        println("Preparando saludo para $nombre...")
        accion("Hola, $nombre!") // Ejecutamos la acción con el saludo
        println("Saludo completado.")
    }

    // Aquí, la lambda `accion` recibe UN SOLO parámetro (String).
    // Podemos usar 'it' para referirnos a ese String.
    println("--- Usando Trailing Lambda con 'it' ---")
    saludarYEjecutar("Clara") {
        println("  [Trailing + 'it'] ¡Mensaje recibido y procesado: $it!")
        // 'it' es el 'mensajeCompleto' que le pasa 'saludarYEjecutar'
    }

    // Otro ejemplo con listas (¡muy común en Compose con LazyColumn, por ejemplo!)
    val ciudades = listOf("Madrid", "Barcelona", "Valencia")

    println("\n--- Recorriendo ciudades con forEach (Trailing + 'it') ---")
    ciudades.forEach {
        println("  Visitando la ciudad de $it")
    }

    //Función simula al forEeach. Es una función de extensión
    fun List<String>.forCada(accion: (String) -> Unit) {
        for (ciudad in this) {
            accion(ciudad);
        }
    }

    //Función simula al forEeach genérica. Es una función de extensión
    fun <T> List<T>.forCada(accion: (T) -> Unit) {
        for (elemento in this) {
            accion(elemento);
        }
    }

    val numeros = (1..100).toList()

    //Al ser genérica la pueda llamar con números (Int) y con (String)
    numeros.forCada { numero -> numero * numero }
    ciudades.forCada { ciudad -> println(ciudad) }

    // Filtrando ciudades que terminan en 'a'
    println("\n--- Filtrando ciudades con filter (Trailing + 'it') ---")
    val ciudadesConA = ciudades.filter {
        it.endsWith("a")
    }
    println("Ciudades que terminan en 'a': $ciudadesConA")


    // Transformando a mayúsculas
    println("\n--- Transformando ciudades con map (Trailing + 'it') ---")
    val ciudadesMayusculas = ciudades.map { it.uppercase() }
    println("Ciudades en mayúsculas: $ciudadesMayusculas")

    println("\nReflexión: 'it' es una convención de Kotlin que, usada correctamente, hace el código extremadamente conciso y legible.")
    println("¡Lo veréis en cada esquina de vuestras aplicaciones Compose!")
}

/**
 * Ejercicio 4: Combinando Conceptos y Ejemplos de la vida real en Compose
 *
 * Objetivo: Mostrar cómo estos conceptos se unen en escenarios comunes, especialmente en Compose.
 */
fun ejercicio4CombinandoConceptos() {
    println("EJERCICIO 4: Combinando Conceptos y Ejemplos de la vida real en Compose (simulado)")
    println("---------------------------------------------------------------------------------")

    // Simulación de un Composable Button en Compose
    // Imagina una función 'Button' que toma un 'onClick' (lambda sin parámetros)
    // y un 'content' (lambda con receptor 'ComposableScope' para dibujar contenido).
    // Aquí simplificamos 'content' como una lambda que recibe un String y devuelve Unit.

    // Definición de una función simulada de un Composable 'Button'
    fun Button(onClick: () -> Unit, content: (text: String) -> Unit) {
        println("--- Renderizando Botón ---")
        println("  Preparando listener de click...")
        // Aquí el framework de Compose registraría el onClick
        println("  Invocando contenido del botón...")
        content("Pulsa aquí") // Pasamos un texto simulado para el contenido
        println("--- Botón Renderizado ---")

        // Simulación de un click
        println("Simulando click del usuario...")
        onClick() // Ejecutamos la lambda que se definió para el click
        println("Click procesado.")
    }

    // USO REAL EN COMPOSE (Sintaxis combinada):
    println("\n--- Ejemplo de Button en 'Compose' real (simulado) ---")
    Button(
        onClick = {
            println("  ¡Has hecho click en el botón!")
            // Aquí iría la lógica cuando el botón es pulsado
        }
    ) { // Trailing lambda para el 'content'
        // 'it' aquí es el "Pulsa aquí" que le pasaría el Composable de Button
        println("  El contenido del botón es: '$it'")
        println("  Podría ser un Text(\"Hola\") real en Compose.")
    }

    // Otro ejemplo: un diálogo de confirmación
    fun showConfirmationDialog(
        title: String,
        message: String,
        onConfirm: () -> Unit, // Lambda para cuando se confirma
        onCancel: () -> Unit   // Lambda para cuando se cancela
    ) {
        println("\n--- Mostrando Diálogo: $title ---")
        println("  Mensaje: $message")
        println("  (Simulando la interacción del usuario)")

        val userChoice = Random.nextInt(2) // 0 para cancelar, 1 para confirmar

        if (userChoice == 1) {
            println("  Usuario CONFIRMA.")
            onConfirm()
        } else {
            println("  Usuario CANCELA.")
            onCancel()
        }
        println("--- Diálogo Cerrado ---")
    }

    // Uso con múltiples trailing lambdas (aunque Kotlin solo permite una real, aquí simulamos para mostrar la idea)
    // Normalmente tendrías: showConfirmationDialog(title, message) { onConfirm } y luego otro parámetro con nombre para onCancel
    // Sin embargo, para fines didácticos, imaginad que onCancel es el ÚLTIMO
    println("\n--- Ejemplo de Diálogo de Confirmación ---")
    showConfirmationDialog(
        title = "¿Eliminar elemento?",
        message = "¿Estás seguro de que quieres eliminar este elemento de forma permanente?",
        onConfirm = {
            println("    -> Eliminando elemento de la base de datos...")
        }
    ) { // onCancel es el último parámetro, por eso usa trailing lambda
        println("    -> Operación de eliminación CANCELADA.")
    }

    println("\nConclusión: La sintaxis de trailing lambda, el uso de 'it' y las lambdas con receptor son la base de la expresividad de Compose.")
    println("Entenderlas os permitirá leer, escribir y crear vuestras propias UIs de forma muy eficaz.")
}