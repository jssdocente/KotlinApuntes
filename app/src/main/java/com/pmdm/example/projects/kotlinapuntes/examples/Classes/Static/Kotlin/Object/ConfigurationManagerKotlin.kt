package com.pmdm.example.projects.kotlinapuntes.examples.Classes.Static.Kotlin.Object

import com.pmdm.example.projects.kotlinapuntes.Classes.Static.Kotlin.CompanionObject.testCompanionObject

// 'object' en Kotlin declara un singleton de forma nativa y concisa.
// El compilador se encarga de todo el boilerplate del patrón Singleton.
// Se inicializa de forma lazy (cuando se accede por primera vez).
object ConfiguracionManagerKotlin {
    // Propiedades de configuración. Son directamente accesibles.
    var tema: String = "Claro"
    var tamanoFuente: Int = 14

    // Bloque de inicialización para el 'object'. Se ejecuta solo la primera vez que se accede.
    init {
        println("Kotlin: ConfiguracionManagerKotlin instanciado por primera vez.")
    }

    // Método para mostrar el estado actual de la configuración.
    fun mostrarConfiguracion() {
        println("Kotlin Config: Tema=$tema, Fuente=$tamanoFuente")
    }
}


fun main() {
    println("--- Usando Object en Kotlin ---")

    // Acceso directo al 'object' por su nombre. No se usa 'new' ni un método 'getInstance()'.
    ConfiguracionManagerKotlin.tema = "Oscuro" // Modificamos el tema
    ConfiguracionManagerKotlin.mostrarConfiguracion() // Imprimimos la configuración

    // Accedemos de nuevo al 'object'. Es la misma y única instancia.
    ConfiguracionManagerKotlin.tamanoFuente = 16 // Modificamos el tamaño de fuente
    ConfiguracionManagerKotlin.mostrarConfiguracion() // Imprimirá el tema "Oscuro" y la fuente 16

    // Comprobamos si las referencias apuntan a la misma instancia.
    val configRef1 = ConfiguracionManagerKotlin
    val configRef2 = ConfiguracionManagerKotlin
    println("¿configRef1 y configRef2 son la misma instancia? ${configRef1 === configRef2}") // '===' para igualdad de referencia
    println("\n")

    // Más pruebas para el siguiente ejercicio...
    testCompanionObject()
}