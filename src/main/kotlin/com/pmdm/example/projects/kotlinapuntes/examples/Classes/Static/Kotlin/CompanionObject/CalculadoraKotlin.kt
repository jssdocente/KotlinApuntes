package com.pmdm.example.projects.kotlinapuntes.examples.Classes.Static.Kotlin.CompanionObject

/*
Problema: Queremos una clase Calculadora que tenga una constante PI y
un méetodo para generar instancias de Usuario de forma simplificada, sin necesidad de crear un objeto Calculadora.
*/

// Una 'data class' simple para representar un usuario en Kotlin.
// 'data class' genera automáticamente 'equals()', 'hashCode()', 'toString()', 'copy()'.
data class UsuarioKotlin(val nombre: String, val email: String) {
    // Un 'companion object' vacío es necesario para poder extenderlo más tarde (Ejercicio 3).
    companion object {}
}

// Clase que contiene un 'companion object' en Kotlin
class CalculadoraKotlin {
    // El 'companion object' es un objeto singleton anidado dentro de la clase.
    // Contiene miembros que se asocian a la clase misma, no a instancias específicas.
    companion object {
        // Constante de tiempo de compilación. Similar a 'public static final' en Java.
        const val PI = 3.14159

        // Función que actúa como un método estático.
        fun calcularCircunferencia(radio: Double): Double {
            return 2 * PI * radio
        }

        // Método de fábrica. Crea y devuelve una instancia de 'UsuarioKotlin'.
        fun crearUsuarioInvitado(): UsuarioKotlin {
            return UsuarioKotlin("Invitado", "guest@example.com")
        }

        // Se puede nombrar el companion object si se necesita (ej. para referenciarlo explícitamente o para extensiones específicas).
        // companion object Factory { ... }
    }

    // Métodos de instancia de la clase 'CalculadoraKotlin'. Requieren un objeto para ser llamados.
    fun sumar(a: Int, b: Int) = a + b
}

fun main() {
    println("--- Usando Companion Object en Kotlin ---")

    // Acceso a la constante 'PI' directamente a través del nombre de la clase.
    println("Kotlin: Valor de PI = ${CalculadoraKotlin.PI}")

    // Llamada a una función del 'companion object' directamente a través del nombre de la clase.
    val radio = 5.0
    println("Kotlin: Circunferencia de radio $radio = ${CalculadoraKotlin.calcularCircunferencia(radio)}")

    // Uso del método de fábrica del 'companion object'.
    val invitado = CalculadoraKotlin.crearUsuarioInvitado()
    println("Kotlin: Usuario invitado creado: $invitado")

    // Para llamar a un método de instancia, necesitamos crear un objeto de la clase.
    val calculadora = CalculadoraKotlin()
    println("Kotlin: Suma 5 + 3 = ${calculadora.sumar(5, 3)}")
    println("\n")
}

