package com.pmdm.example.projects.kotlinapuntes

// HerenciaEnKotlin.kt

fun main() {
    println("--- Ejercicio 1: Herencia Básica y Sobrescritura ---")
    ejercicio1HerenciaBasica()

    println("\n--- Ejercicio 2: Clases Abstractas ---")
    ejercicio2ClasesAbstractas()

    println("\n--- Ejercicio 3: Constructores en Herencia ---")
    ejercicio3ConstructoresHerencia()

    println("\n--- Ejercicio 4: Herencia de Interfaces vs Clases ---")
    ejercicio4HerenciaInterfaces()

    println("\n--- Ejercicio 5: Clases con Métodos Final (No Sobrescribibles) ---")
    ejercicio5MetodosFinal()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 1: Herencia Básica y Sobrescritura
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** En Kotlin, todas las clases son `final` por defecto, lo que significa que no se pueden heredar.
 * Para permitir la herencia, debemos marcar la clase base con la palabra clave `open`.
 * Para sobrescribir un miembro (propiedad o función) de la clase padre en la clase hija,
 * tanto el miembro en la clase padre como en la hija deben estar marcados con `open` y `override` respectivamente.
 *
 * **Utilidad:** Reutilización de código base. Permite definir un comportamiento común en la clase padre
 * y luego especializarlo o extenderlo en las clases hijas.
 * Por ejemplo, una clase `Shape` puede tener un método `draw()`, y `Circle` y `Square` pueden sobrescribirlo
 * para dibujar sus formas específicas.
 */

// Clase padre (Superclase)
open class Vehiculo(val marca: String, val modelo: String, var velocidadActual: Int = 0) {
    // Para que un método pueda ser sobrescrito, debe ser `open`
    open fun acelerar(cantidad: Int) {
        velocidadActual += cantidad
        println("$marca $modelo acelera a $velocidadActual km/h.")
    }

    open fun frenar(cantidad: Int) {
        velocidadActual = maxOf(0, velocidadActual - cantidad) // Asegura que la velocidad no sea negativa
        println("$marca $modelo frena a $velocidadActual km/h.")
    }

    open fun obtenerInfo(): String {
        return "Vehículo: $marca $modelo, Velocidad actual: $velocidadActual km/h"
    }
}

// Clase hija (Subclase) que hereda de Vehiculo
class Coche(marca: String, modelo: String, val numeroPuertas: Int) : Vehiculo(marca, modelo) {
    // Sobrescribimos el método acelerar para añadir un comportamiento específico del coche
    override fun acelerar(cantidad: Int) {
        super.acelerar(cantidad) // Llama al método acelerar de la clase padre
        println("El coche tiene $numeroPuertas puertas y está en marcha.")
    }

    // Sobrescribimos obtenerInfo para añadir información de las puertas
    override fun obtenerInfo(): String {
        return "${super.obtenerInfo()}, Puertas: $numeroPuertas"
    }
}

class Moto(marca: String, modelo: String, val cilindrada: Int) : Vehiculo(marca, modelo) {
    // Sobrescribimos el método acelerar para añadir un comportamiento específico de la moto
    override fun acelerar(cantidad: Int) {
        super.acelerar(cantidad) // Llama al método acelerar de la clase padre
        println("La moto de $cilindrada cc ruge.")
    }
}

fun ejercicio1HerenciaBasica() {
    val miCoche = Coche("Audi", "A4", 5)
    miCoche.acelerar(50)
    miCoche.frenar(10)
    println(miCoche.obtenerInfo())

    println("---")

    val miMoto = Moto("Yamaha", "MT-07", 700)
    miMoto.acelerar(80)
    miMoto.frenar(20)
    println(miMoto.obtenerInfo())
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 2: Clases Abstractas
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Una clase `abstract` no se puede instanciar directamente. Está diseñada para ser
 * heredada por otras clases. Puede contener miembros abstractos (funciones o propiedades) que
 * no tienen implementación y deben ser implementados por las subclases concretas.
 * También puede tener miembros no abstractos con implementación por defecto.
 *
 * **Utilidad:** Define una interfaz común y un comportamiento base para una familia de clases,
 * pero deja ciertos detalles de implementación a las subclases. Es muy útil cuando sabes que
 * hay una base común, pero cada variante necesita su propia forma de hacer algo.
 */

// Clase base abstracta
abstract class Empleado(val nombre: String, val id: String) {
    var salarioBase: Double = 1500.0

    // Función abstracta: las subclases DEBEN implementarla
    abstract fun calcularSalarioAnual(): Double

    // Función no abstracta con implementación por defecto
    fun mostrarInformacion() {
        println("Empleado: $nombre (ID: $id), Salario base mensual: $salarioBase€")
    }
}

// Subclase concreta que hereda e implementa de Empleado
class Desarrollador(nombre: String, id: String, val lenguajePrincipal: String) : Empleado(nombre, id) {
    var bonusRendimiento: Double = 0.0

    // Implementación OBLIGATORIA de la función abstracta
    override fun calcularSalarioAnual(): Double {
        return (salarioBase + bonusRendimiento) * 12
    }

    fun codificar() {
        println("$nombre está codificando en $lenguajePrincipal.")
    }
}

// Otra subclase concreta
class Manager(nombre: String, id: String, val numeroSubordinados: Int) : Empleado(nombre, id) {
    var bonusGestion: Double = 0.0

    // Implementación OBLIGATORIA de la función abstracta
    override fun calcularSalarioAnual(): Double {
        return (salarioBase * 1.5) * 12 + bonusGestion // Salario base de manager es más alto
    }

    fun gestionarEquipo() {
        println("$nombre está gestionando un equipo de $numeroSubordinados personas.")
    }
}

fun ejercicio2ClasesAbstractas() {
    val dev = Desarrollador("Alice Smith", "DEV001", "Kotlin")
    dev.salarioBase = 2000.0
    dev.bonusRendimiento = 300.0
    dev.mostrarInformacion()
    dev.codificar()
    println("Salario anual de ${dev.nombre}: ${dev.calcularSalarioAnual()}€")

    println("---")

    val manager = Manager("Bob Johnson", "MNG001", 10)
    manager.bonusGestion = 1000.0
    manager.mostrarInformacion()
    manager.gestionarEquipo()
    println("Salario anual de ${manager.nombre}: ${manager.calcularSalarioAnual()}€")

    // Error: No se puede instanciar una clase abstracta
    // val empleadoGenerico = Empleado("John Doe", "EMP001")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 3: Constructores en Herencia
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Cuando una clase hija hereda de una clase padre, debe llamar a uno de los constructores
 * de la clase padre. Esto se hace pasando los argumentos necesarios al constructor de la clase padre
 * en la cabecera de la declaración de la clase hija.
 *
 * **Utilidad:** Asegura que la clase padre se inicialice correctamente antes que la clase hija.
 * Permite a la clase hija especializarse en sus propios datos, mientras la clase padre maneja los comunes.
 */

// Clase padre con constructor primario
open class Producto(val nombre: String, val precio: Double) {
    open fun mostrarDetalles() {
        println("Producto: $nombre, Precio: $precio€")
    }
}

// Clase hija que usa el constructor primario de la padre
class Libro(nombre: String, precio: Double, val autor: String, val paginas: Int) : Producto(nombre, precio) {
    override fun mostrarDetalles() {
        super.mostrarDetalles()
        println("  Tipo: Libro, Autor: $autor, Páginas: $paginas")
    }
}

// Clase hija con constructor secundario (menos común en Kotlin)
open class Electronico(nombre: String, precio: Double, val garantiaMeses: Int) : Producto(nombre, precio) {
    init {
        println("Inicializando electrónico '$nombre' con garantía de $garantiaMeses meses.")
    }

    // Constructor secundario que llama al constructor primario de la clase padre
    constructor(nombre: String, precio: Double, garantiaMeses: Int, esImportado: Boolean) :
            this(nombre, precio, garantiaMeses) {
        if (esImportado) println("  (Producto importado)")
    }

    override fun mostrarDetalles() {
        super.mostrarDetalles()
        println("  Tipo: Electrónico, Garantía: $garantiaMeses meses")
    }
}

class Smartphone(nombre: String, precio: Double, garantiaMeses: Int, val sistemaOperativo: String) :
    Electronico(nombre, precio, garantiaMeses) {
    override fun mostrarDetalles() {
        super.mostrarDetalles()
        println("  Sistema Operativo: $sistemaOperativo")
    }
}


fun ejercicio3ConstructoresHerencia() {
    val libro1 = Libro("El Señor de los Anillos", 25.99, "J.R.R. Tolkien", 1200)
    libro1.mostrarDetalles()

    println("---")

    val tele = Electronico("Televisor Smart", 799.99, 24)
    tele.mostrarDetalles()

    println("---")

    val smartphone = Smartphone("Galaxy S23", 999.0, 36, "Android")
    smartphone.mostrarDetalles()

    println("---")

    val portatilImportado = Electronico("Laptop Ultra", 1500.0, 12, true)
    portatilImportado.mostrarDetalles()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 4: Herencia de Interfaces vs Clases
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Kotlin soporta herencia de implementación solo de una clase (herencia simple),
 * pero una clase puede implementar múltiples interfaces. Las interfaces pueden contener
 * declaraciones de métodos abstractos, implementaciones de métodos por defecto y propiedades.
 *
 * **Utilidad:** Las interfaces se utilizan para definir contratos de comportamiento.
 * Una clase que implementa una interfaz promete proporcionar una implementación para
 * todos sus miembros abstractos. Esto es útil para lograr polimorfismo y para definir
 * capacidades que pueden ser compartidas por clases de jerarquías de herencia completamente diferentes.
 * A menudo se prefiere la composición sobre la herencia de clases, y las interfaces facilitan esto.
 */

// Interfaz para elementos que se pueden guardar
interface Guardable {
    fun guardar()
    fun cargar()
}

// Interfaz para elementos que se pueden dibujar
interface Dibujable {
    fun dibujar()
    fun obtenerColor(): String // Propiedad abstracta de interfaz
}

// Clase base, puede heredar de otra clase, pero aquí solo la dejamos open
open class Elemento(val id: String) {
    open fun describir() {
        println("Elemento con ID: $id")
    }
}

// Una clase que implementa múltiples interfaces
class Boton(id: String, val texto: String) : Elemento(id), Guardable, Dibujable {
    override fun guardar() {
        println("Guardando estado del botón '$texto' (ID: $id).")
    }

    override fun cargar() {
        println("Cargando estado del botón '$texto' (ID: $id).")
    }

    override fun dibujar() {
        println("Dibujando botón con texto '$texto' y color: ${obtenerColor()}.")
    }

    override fun obtenerColor(): String {
        return "Azul" // Implementación de la propiedad abstracta
    }

    override fun describir() {
        super.describir()
        println("  Es un botón de UI.")
    }
}

class DocumentoEditable(id: String, var contenido: String) : Elemento(id), Guardable {
    override fun guardar() {
        println("Guardando contenido del documento (ID: $id): '$contenido'.")
    }

    override fun cargar() {
        println("Cargando contenido del documento (ID: $id)...")
        contenido = "Contenido cargado de archivo." // Simula la carga
    }
}

fun ejercicio4HerenciaInterfaces() {
    val botonAceptar = Boton("btn-ok", "Aceptar")
    botonAceptar.describir()
    botonAceptar.guardar()
    botonAceptar.dibujar()
    println("---")

    val miDocumento = DocumentoEditable("doc-report", "Borrador inicial.")
    miDocumento.describir()
    miDocumento.guardar()
    miDocumento.cargar()
    miDocumento.guardar()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 5: Clases con Métodos Final (No Sobrescribibles)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Aunque una clase sea `open`, puedes declarar propiedades o funciones individuales
 * como `final`. Esto significa que no pueden ser sobrescritas por las subclases.
 * Todos los miembros no `open` de una clase `open` son `final` por defecto.
 *
 * **Utilidad:** Se utiliza cuando quieres asegurar que una parte específica del comportamiento
 * de la clase padre no sea modificada por ninguna subclase. Es una forma de controlar
 * qué aspectos de una clase pueden ser extendidos y cuáles no.
 */

open class CuentasBancarias(val numeroCuenta: String, var saldo: Double) {
    // Este método puede ser sobrescrito
    open fun depositar(cantidad: Double) {
        if (cantidad > 0) {
            saldo += cantidad
            println("Depósito de $cantidad€. Saldo actual: $saldo€")
        } else {
            println("La cantidad a depositar debe ser positiva.")
        }
    }

    // Este método NO puede ser sobrescrito porque es 'final'
    final fun obtenerNumeroCuenta(): String {
        return "Nº de Cuenta: $numeroCuenta"
    }

    // Este método también puede ser sobrescrito (es open por defecto en una clase open)
    open fun obtenerSaldo(): Double {
        return saldo
    }
}

class CuentaAhorro(numeroCuenta: String, saldoInicial: Double, val tasaInteres: Double) :
    CuentasBancarias(numeroCuenta, saldoInicial) {

    override fun depositar(cantidad: Double) {
        if (cantidad > 0) {
            super.depositar(cantidad)
            println("  (Se aplica un pequeño interés al depositar en Cuenta de Ahorro)")
        }
    }

    fun aplicarIntereses() {
        val intereses = saldo * tasaInteres
        saldo += intereses
        println("Se aplicaron $intereses€ en intereses. Nuevo saldo: $saldo€")
    }

    // Intentar sobrescribir obtenerNumeroCuenta() daría un error de compilación:
    // override fun obtenerNumeroCuenta(): String { // Error: 'obtenerNumeroCuenta' in 'CuentasBancarias' is final and cannot be overridden
    //     return "Cuenta de Ahorro: ${super.obtenerNumeroCuenta()}"
    // }
}

fun ejercicio5MetodosFinal() {
    val miCuenta = CuentasBancarias("12345-6789", 100.0)
    miCuenta.depositar(50.0)
    println(miCuenta.obtenerNumeroCuenta()) // Acceso al método final

    println("---")

    val cuentaAhorro = CuentaAhorro("98765-4321", 500.0, 0.02)
    cuentaAhorro.depositar(100.0)
    cuentaAhorro.aplicarIntereses()
    println(cuentaAhorro.obtenerNumeroCuenta()) // La subclase puede usar el método final, pero no cambiarlo
    println("Saldo de cuenta de ahorro: ${cuentaAhorro.obtenerSaldo()}€")
}