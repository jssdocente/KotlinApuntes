package com.pmdm.example.projects.kotlinapuntes

// GenericosEnKotlin.kt

fun main() {
    println("--- Ejercicio 1: Clases Genéricas ---")
    ejercicio1ClasesGenericas()

    println("\n--- Ejercicio 2: Funciones Genéricas ---")
    ejercicio2FuncionesGenericas()

    println("\n--- Ejercicio 3: Restricciones de Tipo (Type Bounds) ---")
    ejercicio3RestriccionesDeTipo()

    println("\n--- Ejercicio 4: Covarianza y Contravarianza (In/Out) ---")
    ejercicio4CovarianzaContravarianza()

    println("\n--- Ejercicio 5: Genéricos con Clases y Funciones de Extensión ---")
    ejercicio5GenericosConExtensiones()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 1: Clases Genéricas
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Una clase genérica se define con uno o más parámetros de tipo (normalmente letras mayúsculas
 * como `T`, `E`, `K`, `V` por "Type", "Element", "Key", "Value"). Estos parámetros actúan como marcadores
 * de posición para los tipos reales que se utilizarán cuando se cree una instancia de la clase.
 *
 * **Utilidad:** Permite crear estructuras de datos o clases de utilidad que pueden operar sobre diferentes
 * tipos de datos sin tener que duplicar el código para cada tipo. Por ejemplo, una `Lista<T>` puede
 * almacenar enteros, cadenas, objetos personalizados, etc., usando el mismo código subyacente.
 */

// Clase genérica para un "contenedor" de un solo valor
class Contenedor<T>(var valor: T) {
    fun obtener(): T {
        return valor
    }

    fun poner(nuevoValor: T) {
        valor = nuevoValor
    }

    fun mostrarTipo() {
        println("El tipo de dato en este contenedor es: ${valor!!::class.simpleName}")
    }
}

// Clase genérica para un par de valores
class Par<K, V>(val primero: K, val segundo: V) {
    fun imprimirPar() {
        println("Par: ($primero, $segundo)")
    }
}

fun ejercicio1ClasesGenericas() {
    // Instanciamos Contenedor con un tipo String
    val contenedorDeStrings = Contenedor("Hola Kotlin")
    contenedorDeStrings.mostrarTipo()
    println("Valor: ${contenedorDeStrings.obtener()}")
    contenedorDeStrings.poner("Adiós Genéricos")
    println("Nuevo valor: ${contenedorDeStrings.obtener()}")

    println("---")

    // Instanciamos Contenedor con un tipo Int
    val contenedorDeEnteros = Contenedor(123)
    contenedorDeEnteros.mostrarTipo()
    println("Valor: ${contenedorDeEnteros.obtener()}")
    contenedorDeEnteros.poner(456)
    println("Nuevo valor: ${contenedorDeEnteros.obtener()}")

    println("---")

    // Instanciamos Par con diferentes tipos
    val parStringInt = Par("Edad", 30)
    parStringInt.imprimirPar()

    val parBooleanDouble = Par(true, 3.14)
    parBooleanDouble.imprimirPar()

    // Error en tiempo de compilación si intentas poner un tipo incorrecto
    // contenedorDeStrings.poner(123) // Error: Type mismatch
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 2: Funciones Genéricas
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Las funciones también pueden ser genéricas, lo que les permite operar con tipos
 * de datos variados sin tener que sobrecargar la función para cada tipo posible.
 * Los parámetros de tipo se declaran entre `< >` antes del nombre de la función.
 *
 * **Utilidad:** Crear funciones de utilidad que pueden trabajar con cualquier tipo, como
 * funciones para imprimir arrays, intercambiar valores, buscar elementos, etc.
 */

// Función genérica que imprime los elementos de una lista
fun <T> imprimirLista(items: List<T>) {
    println("Elementos de la lista:")
    for (item in items) {
        println("- $item")
    }
}

// Función genérica que devuelve el elemento del medio de una lista
fun <T> obtenerElementoMedio(lista: List<T>): T? {
    if (lista.isEmpty()) {
        return null
    }
    return lista[lista.size / 2]
}

// Función genérica que intercambia dos elementos en un array
fun <T> intercambiar(array: Array<T>, index1: Int, index2: Int) {
    if (index1 !in array.indices || index2 !in array.indices) {
        println("Error: Índices fuera de rango.")
        return
    }
    val temp = array[index1]
    array[index1] = array[index2]
    array[index2] = temp
    println("Elementos intercambiados.")
}

fun ejercicio2FuncionesGenericas() {
    val numeros = listOf(1, 2, 3, 4, 5)
    imprimirLista(numeros)
    println("Elemento medio: ${obtenerElementoMedio(numeros)}")

    println("---")

    val cadenas = listOf("Kotlin", "es", "genial")
    imprimirLista(cadenas)
    println("Elemento medio: ${obtenerElementoMedio(cadenas)}")

    println("---")

    val caracteres = arrayOf('A', 'B', 'C', 'D')
    println("Array original: ${caracteres.joinToString()}")
    intercambiar(caracteres, 0, 3)
    println("Array después del intercambio: ${caracteres.joinToString()}")

    // No hay necesidad de especificar el tipo explícitamente, Kotlin lo infiere
    val resultado = obtenerElementoMedio(listOf(true, false, true))
    println("Elemento medio de booleanos: $resultado")
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 3: Restricciones de Tipo (Type Bounds)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** A veces, queremos que nuestros genéricos trabajen con cualquier tipo, pero solo si ese tipo
 * cumple ciertos requisitos (por ejemplo, que sea un número, o que implemente una interfaz específica).
 * Para esto usamos las "restricciones de tipo" o `type bounds` con la palabra clave `where` o simplemente `:`.
 *
 * **Utilidad:** Permite a las funciones genéricas invocar métodos o propiedades de los tipos restringidos,
 * lo que no sería posible con un tipo `Any` genérico. Garantiza que los tipos pasados tienen las capacidades necesarias.
 */

// Función genérica que suma elementos de una lista, solo si son subtipos de Number
fun <T : Number> sumarElementos(lista: List<T>): Double {
    var suma = 0.0
    for (item in lista) {
        suma += item.toDouble() // Podemos llamar a toDouble() porque T es un Number
    }
    return suma
}

// Interfaz para objetos que tienen un nombre
interface ConNombre {
    val nombre: String
}

data class Persona(override val nombre: String, val edad: Int) : ConNombre
data class Animal(override val nombre: String, val especie: String) : ConNombre

// Función genérica que busca un elemento por nombre en una lista de objetos ConNombre
fun <T : ConNombre> buscarPorNombre(lista: List<T>, nombreBuscado: String): T? {
    return lista.find { it.nombre == nombreBuscado }
}

// Genéricos con múltiples restricciones (usando `where`)
fun <T> procesarDatoConMultipleRestriccion(dato: T)
        where T : Comparable<T>, T : ConNombre {
    println("Procesando dato: ${dato.nombre}. Su tipo es comparable.")
    // Podríamos usar dato.compareTo(...) aquí
}


fun ejercicio3RestriccionesDeTipo() {
    val ints = listOf(1, 2, 3, 4, 5)
    println("Suma de enteros: ${sumarElementos(ints)}")

    val doubles = listOf(1.5, 2.0, 3.5)
    println("Suma de doubles: ${sumarElementos(doubles)}")

    // Error: String no es un subtipo de Number
    // val strings = listOf("a", "b")
    // sumarElementos(strings)

    println("---")

    val personas = listOf(Persona("Ana", 25), Persona("Luis", 30))
    val p = buscarPorNombre(personas, "Luis")
    println("Persona encontrada: ${p?.nombre ?: "No encontrada"}")

    val animales = listOf(Animal("Fido", "Perro"), Animal("Garfield", "Gato"))
    val a = buscarPorNombre(animales, "Fido")
    println("Animal encontrado: ${a?.nombre ?: "No encontrado"}")

    println("---")

    val miPersona = Persona("Carla", 28)
    // procesarDatoConMultipleRestriccion(miPersona) // Funcionara si Persona implementa Comparable<Persona>

    // Para que funcione, Persona necesitaría implementar Comparable<Persona>:
    // data class Persona(override val nombre: String, val edad: Int) : ConNombre, Comparable<Persona> {
    //     override fun compareTo(other: Persona): Int = this.nombre.compareTo(other.nombre)
    // }
    // procesarDatoConMultipleRestriccion(miPersona)
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 4: Covarianza y Contravarianza (In/Out)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** La covarianza (`out`) y la contravarianza (`in`) son conceptos de seguridad de tipos
 * que se aplican a los parámetros de tipo genéricos.
 *
 * *   **`out` (Covarianza):** Un parámetro de tipo `out T` significa que `T` solo se produce (sale)
 *     de la clase genérica. Si `B` es un subtipo de `A`, entonces `Contenedor<B>` es un subtipo
 *     de `Contenedor<A>`. Esto es útil para tipos de retorno de funciones.
 * *   **`in` (Contravarianza):** Un parámetro de tipo `in T` significa que `T` solo se consume (entra)
 *     en la clase genérica. Si `B` es un subtipo de `A`, entonces `Contenedor<A>` es un subtipo
 *     de `Contenedor<B>`. Esto es útil para tipos de parámetros de funciones.
 *
 * **Utilidad:** Permite una mayor flexibilidad con la asignación de tipos genéricos sin romper
 * la seguridad de tipos. Es crucial para entender cómo funcionan las colecciones de Kotlin
 * (ej. `List<out T>`, `MutableList<T>`) y cómo interactúan las interfaces genéricas.
 */

// Interfaz Productor (solo produce T)
interface Productor<out T> {
    fun producir(): T
}

// Interfaz Consumidor (solo consume T)
interface Consumidor<in T> {
    fun consumir(item: T)
}

// Clase base y subtipo
open class Fruta
class Manzana : Fruta()
class Platano : Fruta()

// Implementación de Productor para Manzanas
class CosechadorManzanas : Productor<Manzana> {
    override fun producir(): Manzana {
        println("Cosechando manzana...")
        return Manzana()
    }
}

// Implementación de Consumidor para Frutas
class EmpacadorFrutas : Consumidor<Fruta> {
    override fun consumir(item: Fruta) {
        println("Empacando fruta: ${item::class.simpleName}")
    }
}

fun ejercicio4CovarianzaContravarianza() {
    // Covarianza (out): Productor<Manzana> es subtipo de Productor<Fruta>
    val cosechadorManzanas: Productor<Manzana> = CosechadorManzanas()
    val cosechadorFrutas: Productor<Fruta> = cosechadorManzanas // Esto es válido gracias a `out`
    val fruta = cosechadorFrutas.producir()
    println("Fruta producida: ${fruta::class.simpleName}")

    println("---")

    // Contravarianza (in): Consumidor<Fruta> es subtipo de Consumidor<Manzana>
    val empacadorFrutas: EmpacadorFrutas = EmpacadorFrutas()
    val empacadorManzanas: Consumidor<Manzana> = empacadorFrutas // Esto es válido gracias a `in`
    empacadorManzanas.consumir(Manzana()) // Empacador de frutas puede empacar manzanas
    // empacadorManzanas.consumir(Platano()) // Error: Consumidor de Manzanas no puede consumir Plátanos
    // Aunque el EmpacadorFrutas original sí podría, el tipo de referencia lo restringe.

    // ¿Por qué? Un Consumidor de Manzanas sabe cómo tratar con Manzanas.
    // Un Empacador de Frutas sabe cómo tratar con CUALQUIER Fruta (incluidas Manzanas).
    // Por lo tanto, un Empacador de Frutas es "más genérico" y puede ser usado donde se espera un Consumidor de Manzanas.

    println("---")

    // Ejemplo con colecciones:
    val listaManzanas: List<Manzana> = listOf(Manzana(), Manzana())
    val listaFrutas: List<Fruta> = listaManzanas // Válido porque List es covariante (`List<out E>`)
    println("Lista de frutas contiene: ${listaFrutas.size} elementos.")

    // val mutableListaFrutas: MutableList<Fruta> = mutableListOfManzanas // Error: MutableList no es covariante
    // Esto es porque una MutableList<Manzana> puede producir Manzanas, pero si la tratas como MutableList<Fruta>,
    // podrías intentar añadir un Platano, lo cual rompería la MutableList<Manzana>.
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 5: Genéricos con Clases y Funciones de Extensión
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Podemos combinar genéricos con funciones de extensión y clases para crear APIs muy expresivas y seguras.
 *
 * **Utilidad:** Añadir funcionalidad a clases genéricas sin modificarlas directamente, o crear funciones
 * de utilidad que operan sobre tipos genéricos de una manera muy específica.
 */

// Clase genérica "Pila"
class Pila<T> {
    val elementos: MutableList<T> = mutableListOf()

    fun push(item: T) {
        elementos.add(item)
    }

    fun pop(): T? {
        return if (elementos.isNotEmpty()) elementos.removeAt(elementos.lastIndex) else null
    }

    fun isEmpty(): Boolean = elementos.isEmpty()
    fun size(): Int = elementos.size

    override fun toString(): String = elementos.joinToString(prefix = "Pila[", postfix = "]")
}

// Función de extensión genérica para una Pila
fun <T> Pila<T>.peek(): T? {
    return if (elementos.isNotEmpty()) elementos.last() else null
}

// Otra función de extensión genérica, pero con una restricción de tipo
fun <T : Comparable<T>> Pila<T>.findMax(): T? {
    if (isEmpty()) return null
    var max = pop() ?: return null
    val tempPila = Pila<T>() // Para no modificar la pila original
    while (!isEmpty()) {
        val current = pop()
        if (current != null && current > max) {
            max = current
        }
        if (current != null) tempPila.push(current)
    }
    // Restaurar la pila (opcional, dependiendo del caso de uso)
    while (!tempPila.isEmpty()) {
        push(tempPila.pop()!!)
    }
    return max
}


fun ejercicio5GenericosConExtensiones() {
    val pilaDeStrings = Pila<String>()
    pilaDeStrings.push("Primero")
    pilaDeStrings.push("Segundo")
    pilaDeStrings.push("Tercero")

    println("Pila de Strings: $pilaDeStrings")
    println("Elemento superior (peek): ${pilaDeStrings.peek()}")
    println("Pop: ${pilaDeStrings.pop()}")
    println("Pila después de pop: $pilaDeStrings")

    println("---")

    val pilaDeInts = Pila<Int>()
    pilaDeInts.push(10)
    pilaDeInts.push(5)
    pilaDeInts.push(20)
    pilaDeInts.push(15)

    println("Pila de Ints: $pilaDeInts")
    println("Elemento máximo (findMax): ${pilaDeInts.findMax()}")
    // Nota: findMax modifica la pila original al hacer pop()
    // Una implementación más robusta debería evitar modificar la pila o crear una copia.
    // Para este ejercicio didáctico, lo dejamos así.
    println("Pila de Ints después de findMax: $pilaDeInts")
}