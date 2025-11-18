package com.pmdm.example.projects.kotlinapuntes

// main.kt
fun main() {
    println("--- Ejercicio 1: Enum Básico para Días de la Semana ---")
    manejarDia(DiaSemana.LUNES)
    manejarDia(DiaSemana.SABADO)
    manejarDia(DiaSemana.MIERCOLES)
    println("\n")

    println("--- Ejercicio 2: Enum con Propiedades Personalizadas ---")
    mostrarInfoPrioridad(Prioridad.ALTA)
    mostrarInfoPrioridad(Prioridad.BAJA)
    mostrarInfoPrioridad(Prioridad.MEDIA)
    println("\n")

    println("--- Ejercicio 3: Enum con Métodos y Lógica Interna ---")
    ejecutarAccionUsuario(AccionUsuario.LOGIN)
    ejecutarAccionUsuario(AccionUsuario.LOGOUT)
    ejecutarAccionUsuario(AccionUsuario.PERFIL)
    ejecutarAccionUsuario(AccionUsuario.COMPRAR)
    println("\n")

    println("--- Ejercicio 4: Enum en un 'when' para UI en Compose (simulado) ---")
    simularPantallaEstado(EstadoCarga.CARGANDO)
    simularPantallaEstado(EstadoCarga.EXITO)
    simularPantallaEstado(EstadoCarga.ERROR("No se pudo cargar la lista de productos."))
    simularPantallaEstado(EstadoCarga.VACIO) // Nuevo estado para demostrar más flexibilidad
    println("\n")
}

// --- Definición de Enumerados y Funciones ---

/**
 * Ejercicio 1: Enum Básico
 * Define un conjunto fijo de constantes (días de la semana).
 * Útil para evitar errores de escritura y hacer el código más legible.
 */
enum class DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
}

fun manejarDia(dia: DiaSemana) {
    // El 'when' es perfecto para trabajar con enums, ya que cubre todos los casos posibles.
    when (dia) {
        DiaSemana.LUNES -> println("Es Lunes. ¡Ánimo con la semana!")
        DiaSemana.MARTES, DiaSemana.MIERCOLES, DiaSemana.JUEVES -> println("Es mitad de semana, a tope.")
        DiaSemana.VIERNES -> println("¡Por fin es Viernes!")
        DiaSemana.SABADO, DiaSemana.DOMINGO -> println("¡Es fin de semana a disfrutar!")
    }
}

/**
 * Ejercicio 2: Enum con Propiedades Personalizadas
 * Cada constante del enum puede tener sus propias propiedades y valores.
 * Se definen en el constructor del enum.
 */
enum class Prioridad(val nivelNumerico: Int, val descripcion: String) {
    BAJA(1, "Tareas que pueden esperar"),
    MEDIA(2, "Tareas importantes, pero no urgentes"),
    ALTA(3, "Tareas críticas que requieren atención inmediata");

    // También se pueden añadir funciones a los enums
    fun esCritica() = this == ALTA
}

fun mostrarInfoPrioridad(prioridad: Prioridad) {
    println("Prioridad: ${prioridad.name} (Nivel: ${prioridad.nivelNumerico}, Descripción: ${prioridad.descripcion})")
    if (prioridad.esCritica()) {
        println("¡ATENCIÓN! Esta prioridad es CRÍTICA.")
    }
}

/**
 * Ejercicio 3: Enum con Métodos y Lógica Interna
 * Los enums pueden contener métodos, lo que les da comportamiento.
 * Esto es muy útil para encapsular lógica relacionada con cada estado.
 */
enum class AccionUsuario {
    LOGIN {
        override fun ejecutar() {
            println("Realizando proceso de login...")
            // Lógica compleja para login
        }
    },
    LOGOUT {
        override fun ejecutar() {
            println("Cerrando sesión del usuario...")
            // Lógica para logout
        }
    },
    PERFIL {
        override fun ejecutar() {
            println("Navegando a la pantalla de perfil...")
            // Lógica para ver perfil
        }
    },
    COMPRAR {
        override fun ejecutar() {
            println("Procesando compra de producto...")
            // Lógica para realizar una compra
        }
    }; // El punto y coma es necesario si hay métodos o propiedades después de las constantes

    // Define un método abstracto que cada constante del enum debe implementar.
    abstract fun ejecutar()
}

fun ejecutarAccionUsuario(accion: AccionUsuario) {
    println("Intento de acción: ${accion.name}")
    accion.ejecutar() // Cada constante ejecuta su propia lógica definida
}

/**
 * Ejercicio 4: Enum de Clases Selladas (Sealed Class) para Estados de UI
 * No es un 'enum' clásico, sino una 'sealed class'.
 * Ideal para representar estados mutuamente excluyentes y con datos asociados.
 * Muy usado en Compose para manejar el estado de las pantallas.
 */
sealed class EstadoCarga {
    object CARGANDO : EstadoCarga() // Un estado sin datos adicionales
    object EXITO : EstadoCarga()    // Otro estado simple
    data class ERROR(val mensaje: String) : EstadoCarga() // Un estado con datos (mensaje de error)
    object VACIO : EstadoCarga() // Añadimos un estado "vacío"
}

fun simularPantallaEstado(estado: EstadoCarga) {
    print("Estado actual de la pantalla: ")
    when (estado) {
        EstadoCarga.CARGANDO -> {
            println("Mostrando spinner de carga...")
            // Composable para un spinner (simulado)
        }
        EstadoCarga.EXITO -> {
            println("Datos cargados correctamente. Mostrando contenido principal.")
            // Composable con los datos ya cargados
        }
        is EstadoCarga.ERROR -> { // Usamos 'is' para acceder a las propiedades de la data class
            println("Ha ocurrido un error: ${estado.mensaje}. Mostrando mensaje de error y botón de reintento.")
            // Composable para mostrar un mensaje de error con 'estado.mensaje'
        }
        EstadoCarga.VACIO -> {
            println("No hay datos para mostrar. Sugiriendo al usuario añadir elementos.")
            // Composable para un estado vacío (ej. "No tienes productos en tu carrito")
        }
    }
}