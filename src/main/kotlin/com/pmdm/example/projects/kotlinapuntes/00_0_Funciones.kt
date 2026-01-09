package com.pmdm.example.projects.kotlinapuntes

// main.kt
fun main() {
    // --- Ejercicio Resuelto 1: Funciones Básicas ---
    // Problema: Crear una función que salude a una persona.
    println("--- Ejercicio 1: Función 'saludar' ---")
    saludar("Alicia") // Llamada a la función con un nombre
    saludar("Roberto") // Otra llamada
    println("\n")

    // --- Ejercicio Resuelto 2: Funciones que Devuelven Valores ---
    // Problema: Crear una función que sume dos números y devuelva el resultado.
    println("--- Ejercicio 2: Función 'sumar' ---")
    val resultadoSuma1 = sumar(10, 5) // La función devuelve un Int que se guarda en resultadoSuma1
    println("La suma de 10 y 5 es: $resultadoSuma1")
    println("La suma de 7 y 3 es: ${sumar(7, 3)}") // Llamada directa en println
    println("\n")

    // --- Ejercicio Resuelto 3: Valores por Defecto en Parámetros ---
    // Problema: Modificar 'saludar' para que salude a "Desconocido" si no se da un nombre.
    println("--- Ejercicio 3: Función 'saludarConDefault' ---")
    saludarConDefault("Marta") // Se proporciona un nombre, se usa "Marta"
    saludarConDefault()        // No se proporciona nombre, se usa el valor por defecto "Desconocido"
    saludarConDefault("Javier")
    println("\n")

    // --- Ejercicio Resuelto 4: Múltiples Parámetros con Valores por Defecto y Parámetros Nombrados ---
    // Problema: Una función para configurar una notificación con opciones por defecto.
    println("--- Ejercicio 4: Función 'configurarNotificacion' ---")

    // Caso 1: Solo el mensaje obligatorio. Los demás parámetros usan sus valores por defecto.
    configurarNotificacion("Nueva versión disponible.")
    println("---")

    // Caso 2: Mensaje y tipo personalizado. urgente sigue por defecto (false).
    configurarNotificacion("Error de red.", tipo = "Error del Sistema")
    println("---")

    // Caso 3: Mensaje y urgente activado. tipo sigue por defecto ("General").
    configurarNotificacion("¡Alerta crítica de seguridad!", urgente = true)
    println("---")

    // Caso 4: Todos los parámetros personalizados.
    configurarNotificacion("Recordatorio de pago.",  urgente = true,tipo = "Finanzas")
    println("---")

    // Caso 5: Uso de parámetros nombrados para cambiar el orden, mejorando la legibilidad.
    configurarNotificacion(urgente = true, mensaje = "El servidor se reiniciará en 5 minutos.", tipo = "Mantenimiento")
    println("\n")

    // --- Reto (Solución) ---
    println("--- Reto: Función 'dibujarCaja' ---")
    dibujarCaja(ancho = 100, alto = 50)
    dibujarCaja(ancho = 200, alto = 80, color = "Azul")
    dibujarCaja(ancho = 150, alto = 70, relleno = false)
    dibujarCaja(alto = 60, ancho = 120, color = "Rojo", relleno = true)
    println("\n")
}

// --- Definición de Funciones ---

/**
 * Ejercicio 1: Función básica para saludar.
 * @param nombre String El nombre de la persona a saludar.
 */
fun saludar(nombre: String) {
    println("¡Hola, $nombre! Encantado de verte.")
}

/**
 * Ejercicio 2: Función que suma dos enteros y devuelve el resultado.
 * @param num1 Int Primer número.
 * @param num2 Int Segundo número.
 * @return Int La suma de num1 y num2.
 */
fun sumar(num1: Int, num2: Int): Int {
    return num1 + num2 // Return devuelve el valor calculado.
}

/**
 * Ejercicio 3: Función de saludo con un valor por defecto para el nombre.
 * Si no se proporciona 'nombre', se usa "Desconocido".
 * @param nombre String Nombre de la persona, por defecto "Desconocido".
 */
fun saludarConDefault(nombre: String = "Desconocido") {
    println("Saludos, $nombre. ¡Bienvenido/a!")
}

/**
 * Ejercicio 4: Configura un mensaje de notificación con múltiples parámetros.
 * Demuestra valores por defecto y uso de parámetros nombrados.
 * @param mensaje String El contenido del mensaje (obligatorio).
 * @param tipo String El tipo de notificación, por defecto "General".
 * @param urgente Boolean Indica si la notificación es urgente, por defecto 'false'.
 */
fun configurarNotificacion(
    mensaje: String,
    tipo: String = "General", // Valor por defecto: "General"
    urgente: Boolean = false  // Valor por defecto: false
) {
    val estadoUrgencia = if (urgente) "URGENTE" else "Normal"
    println("--- NOTIFICACIÓN ---")
    println("Mensaje: '$mensaje'")
    println("Tipo: $tipo")
    println("Estado: $estadoUrgencia")
}

/**
 * Solución al Reto: Función para describir una caja con dimensiones y estilos.
 * Muestra el uso combinado de parámetros obligatorios y con valores por defecto.
 * @param ancho Int El ancho de la caja (obligatorio).
 * @param alto Int El alto de la caja (obligatorio).
 * @param color String El color de la caja, por defecto "Gris".
 * @param relleno Boolean Indica si la caja está rellena, por defecto 'true'.
 */
fun dibujarCaja(
    ancho: Int,
    alto: Int,
    color: String = "Gris",
    relleno: Boolean = true
) {
    val estadoRelleno = if (relleno) "rellena" else "sin relleno"
    println("Se va a dibujar una caja de ${ancho}x${alto} píxeles, de color $color y $estadoRelleno.")
}