package com.pmdm.example.projects.kotlinapuntes

import android.os.Build
import androidx.annotation.RequiresApi

// ObjetosEnKotlin.kt

fun main() {
    println("--- Ejercicio 1: Singleton con Object ---")
    ejercicio1Singleton()

    println("\n--- Ejercicio 2: Uso de Object como constante o utilidad ---")
    ejercicio2ConstantesYUtilidades()

    println("\n--- Ejercicio 3: Companion Object básico ---")
    ejercicio3CompanionObjectBasico()

    println("\n--- Ejercicio 4: Companion Object para métodos de fábrica (Factory Methods) ---")
    ejercicio4CompanionObjectFactoryMethod()

    println("\n--- Ejercicio 5: Companion Object y Extension Functions ---")
    ejercicio5CompanionObjectExtensionFunctions()

    println("\n--- Ejercicio 6: Object Expression (Objetos anónimos) ---")
    ejercicio6ObjectExpression()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 1: Singleton con Object
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** En Kotlin, la forma más sencilla de implementar el patrón de diseño Singleton es usando `object`.
 * Un singleton garantiza que solo hay una instancia de una clase y proporciona un punto de acceso global a ella.
 * Esto es útil para gestores de configuración, bases de datos, etc.
 *
 * **Explicación:** Cuando declaras un `object`, Kotlin se encarga de crear una única instancia de esa "clase"
 * y gestiona su ciclo de vida. No puedes crear instancias adicionales de un `object` con la palabra clave `new`
 * (que ni siquiera existe en Kotlin para esto).
 */
object ConfiguracionApp {
    var temaOscuro: Boolean = false
    var idioma: String = "es"

    fun mostrarConfiguracion() {
        println("Configuración actual: Tema oscuro = $temaOscuro, Idioma = $idioma")
    }

    fun cambiarIdioma(nuevoIdioma: String) {
        idioma = nuevoIdioma
        println("Idioma cambiado a: $idioma")
    }
}

fun ejercicio1Singleton() {
    println("Accediendo a la configuración por primera vez...")
    ConfiguracionApp.mostrarConfiguracion() // Accedemos directamente a la única instancia

    println("Cambiando el tema y el idioma...")
    ConfiguracionApp.temaOscuro = true // Modificamos propiedades
    ConfiguracionApp.cambiarIdioma("en")

    println("Accediendo a la configuración de nuevo (es la misma instancia)...")
    ConfiguracionApp.mostrarConfiguracion() // Vemos los cambios reflejados
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 2: Uso de Object como constante o utilidad
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Los `object` también son excelentes para agrupar constantes o funciones de utilidad
 * que no necesitan estar ligadas a una instancia específica de una clase.
 *
 * **Explicación:** Imagina que tienes una serie de valores que no cambian (URLs de API, nombres de ficheros, etc.)
 * o funciones matemáticas que no dependen del estado de un objeto. Agruparlas en un `object` mejora la
 * organización y la legibilidad de tu código.
 */
object ConstantesApp {
    const val API_URL = "https://api.myapp.com/v1/" // `const val` para constantes de tiempo de compilación
    const val VERSION_APP = 1.0
    const val TIMEOUT_RED = 3000L // En milisegundos

    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerSaludoDelDia(): String {
        val hora = java.time.LocalTime.now().hour
        return when (hora) {
            in 6..12 -> "¡Buenos días!"
            in 13..19 -> "¡Buenas tardes!"
            else -> "¡Buenas noches!"
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun ejercicio2ConstantesYUtilidades() {
    println("URL de la API: ${ConstantesApp.API_URL}")
    println("Versión de la aplicación: ${ConstantesApp.VERSION_APP}")
    println("Timeout de red: ${ConstantesApp.TIMEOUT_RED}ms")
    println("Saludo del día: ${ConstantesApp.obtenerSaludoDelDia()}")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 3: Companion Object básico
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Un `companion object` es un `object` especial que está asociado a una clase.
 * Se puede acceder a sus miembros (propiedades y funciones) directamente a través del nombre de la clase,
 * sin necesidad de crear una instancia de la clase.
 *
 * **Explicación:** Es similar a los miembros estáticos de Java, pero más potente y flexible.
 * Cada clase solo puede tener un `companion object`. Es como una "clase anidada" que comparte nombre
 * con la clase principal y se carga con ella.
 */
class Usuario(val nombre: String, val email: String) {
    // Declaramos un companion object dentro de la clase Usuario
    companion object {
        const val MAX_USUARIOS_REGISTRADOS = 1000 // Constante asociada a la clase
        var contadorUsuarios = 0 // Propiedad que puede cambiar

        fun crearUsuarioInvitado(): Usuario { // Función de utilidad
            contadorUsuarios++
            return Usuario("Invitado_${contadorUsuarios}", "invitado${contadorUsuarios}@example.com")
        }

        fun validarEmail(email: String): Boolean {
            return email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
        }
    }

    init {
        // En un constructor o init, podemos acceder a los miembros del companion object
        // Aunque generalmente las inicializaciones que modifican el companion object
        // se hacen en métodos o funciones específicas.
        // Aquí solo para ilustrar que es posible.
        // Si `contadorUsuarios` se incrementa aquí, cada nueva instancia lo haría.
        // Para este ejemplo, lo haremos en `crearUsuarioInvitado` para mayor control.
    }

    fun mostrarInfo() {
        println("Usuario: $nombre, Email: $email")
    }
}

fun ejercicio3CompanionObjectBasico() {
    // Accedemos a los miembros del companion object directamente a través del nombre de la clase
    println("Máximo de usuarios registrados: ${Usuario.MAX_USUARIOS_REGISTRADOS}")

    val emailValido = "alumno@ejemplo.com"
    val emailInvalido = "alumno@ejemplo"
    println("¿'$emailValido' es un email válido? ${Usuario.validarEmail(emailValido)}")
    println("¿'$emailInvalido' es un email válido? ${Usuario.validarEmail(emailInvalido)}")

    val usuario1 = Usuario("Alice", "alice@example.com")
    val usuario2 = Usuario.crearUsuarioInvitado() // Usamos el factory method del companion object
    val usuario3 = Usuario.crearUsuarioInvitado()

    usuario1.mostrarInfo()
    usuario2.mostrarInfo()
    usuario3.mostrarInfo()

    println("Contador de usuarios (a través del companion object): ${Usuario.contadorUsuarios}")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 4: Companion Object para métodos de fábrica (Factory Methods)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Los métodos de fábrica son un caso de uso muy común para los `companion object`.
 * Permiten crear instancias de una clase de formas alternativas o con lógica de inicialización compleja,
 * sin exponer múltiples constructores o haciendo que el proceso sea más descriptivo.
 *
 * **Explicación:** En lugar de `val boton = Boton("Aceptar")`, podrías tener `val boton = Boton.crearBotonPrimario("Aceptar")`.
 * Esto hace el código más legible y encapsula la lógica de creación.
 * También son útiles cuando quieres devolver subtipos o instancias de caches.
 */
class Boton(val texto: String, val colorFondo: String, val colorTexto: String) {

    fun click() {
        println("Botón '$texto' clickeado. Color de fondo: $colorFondo, Color de texto: $colorTexto")
    }

    companion object {
        // Método de fábrica para un botón por defecto
        fun crearBotonDefault(texto: String): Boton {
            return Boton(texto, "#CCCCCC", "#333333") // Gris y negro
        }

        // Método de fábrica para un botón primario
        fun crearBotonPrimario(texto: String): Boton {
            return Boton(texto, "#007BFF", "#FFFFFF") // Azul y blanco
        }

        // Método de fábrica para un botón de advertencia
        fun crearBotonAdvertencia(texto: String): Boton {
            return Boton(texto, "#FFC107", "#333333") // Amarillo y negro
        }
    }
}

fun ejercicio4CompanionObjectFactoryMethod() {
    val botonAceptar = Boton.crearBotonPrimario("Aceptar")
    val botonCancelar = Boton.crearBotonDefault("Cancelar")
    val botonBorrar = Boton.crearBotonAdvertencia("Eliminar")

    botonAceptar.click()
    botonCancelar.click()
    botonBorrar.click()

    // Intentamos crear un botón directamente con el constructor.
    // Esto es posible si el constructor no es privado.
    // Los métodos de fábrica son una alternativa a tener constructores múltiples
    // o para añadir lógica antes de la creación.
    val botonManual = Boton("Manual", "#000000", "#FFFFFF")
    botonManual.click()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 5: Companion Object y Extension Functions
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Puedes definir funciones de extensión para los `companion object`.
 * Esto te permite añadir funcionalidad a una clase que se invoca de manera "estática"
 * (a través del nombre de la clase), incluso si no tienes acceso al código fuente de la clase.
 *
 * **Explicación:** Es una forma muy poderosa de extender clases sin modificarlas.
 * Imagina que tienes una clase de una librería externa y quieres añadirle un método "estático"
 * sin heredarla o modificarla.
 */
class Documento(val titulo: String, val contenido: String) {
    companion object {
        // Este companion object podría estar vacío o tener sus propios miembros
        // La clave es que existan para poder extenderlos.
    }

    fun mostrarContenido() {
        println("--- $titulo ---\n$contenido")
    }
}

// Definimos una función de extensión para el companion object de Documento
fun Documento.Companion.crearDocumentoVacio(): Documento {
    println("Creando documento vacío...")
    return Documento("Nuevo Documento", "Contenido vacío.")
}

fun Documento.Companion.crearDocumentoDePlantilla(nombrePlantilla: String): Documento {
    println("Creando documento de plantilla: $nombrePlantilla")
    return when (nombrePlantilla.toLowerCase()) {
        "carta" -> Documento("Carta Formal", "Estimado/a Señor/a,\n\n[Texto de la carta]\n\nAtentamente,\n[Tu Nombre]")
        "informe" -> Documento("Informe Mensual", "## Introducción\n\n[Resumen del informe]\n\n## Datos\n\n[Datos y análisis]")
        else -> crearDocumentoVacio()
    }
}

fun ejercicio5CompanionObjectExtensionFunctions() {
    // Usamos las funciones de extensión como si fueran miembros del companion object
    val docVacio = Documento.crearDocumentoVacio()
    val carta = Documento.crearDocumentoDePlantilla("carta")
    val informe = Documento.crearDocumentoDePlantilla("informe")
    val docDesconocido = Documento.crearDocumentoDePlantilla("desconocida")

    docVacio.mostrarContenido()
    println("------------------------------------")
    carta.mostrarContenido()
    println("------------------------------------")
    informe.mostrarContenido()
    println("------------------------------------")
    docDesconocido.mostrarContenido()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 6: Object Expression (Objetos anónimos)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Una `object expression` te permite crear un objeto de una clase anónima,
 * es decir, una clase que no tiene nombre. Esto es especialmente útil para implementar interfaces
 * o clases abstractas sobre la marcha, similar a las clases anónimas internas de Java.
 *
 * **Explicación:** Cuando necesitas un objeto que implemente una interfaz o extienda una clase
 * para un solo uso y en un punto específico del código, una `object expression` es perfecta.
 * No necesitas definir una clase completa en otro lugar.
 */

// Definimos una interfaz simple
interface ClickListener {
    fun onClick()
}

// Definimos una clase con un método que acepta un ClickListener
class BotonUI(val nombre: String) {
    var listener: ClickListener? = null

    fun simularClick() {
        println("Simulando click en el botón '$nombre'")
        listener?.onClick() ?: println("No hay listener asociado.")
    }
}

fun ejercicio6ObjectExpression() {
    val botonGuardar = BotonUI("Guardar")

    // Creamos un objeto anónimo que implementa la interfaz ClickListener
    botonGuardar.listener = object : ClickListener {
        override fun onClick() {
            println("¡El botón 'Guardar' ha sido clickeado! Guardando datos...")
            // Aquí iría la lógica para guardar datos
        }
    }
    botonGuardar.simularClick()

    println("\n------------------------------------")

    val botonCerrar = BotonUI("Cerrar")
    // Otra object expression con diferente comportamiento
    botonCerrar.listener = object : ClickListener {
        override fun onClick() {
            println("¡El botón 'Cerrar' ha sido clickeado! Cerrando aplicación...")
            // Aquí iría la lógica para cerrar la aplicación
        }
    }
    botonCerrar.simularClick()

    println("\n------------------------------------")

    val botonSinListener = BotonUI("Sin Listener")
    botonSinListener.simularClick() // No hace nada más que imprimir que no hay listener
}