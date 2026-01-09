package com.pmdm.example.projects.kotlinapuntes

import kotlinx.coroutines.*
import java.util.concurrent.Executors

/**
 * APUNTES SOBRE KOTLIN COROUTINES (CORRUTINAS)
 *
 * Una corrutina es un "hilo ligero". A diferencia de los hilos tradicionales (Threads),
 * las corrutinas son gestionadas por Kotlin, son mucho más eficientes en memoria y 
 * permiten escribir código asíncrono de forma secuencial.
 */

fun main() = runBlocking {
    println("=== EJERCICIOS DE KOTLIN COROUTINES ===\n")

    ejercicio1_ConceptoBasico()
    ejercicio2_ParalelismoAsync()
    ejercicio3_ContextosYDispatchers()
    ejercicio4_CicloDeVidaYCancion()
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 1: El Concepto de "suspend" (La Magia de las Corrutinas)
// ----------------------------------------------------------------------------------------
/**
 * EXPLICACIÓN PROFUNDA DE 'suspend':
 *
 * 1. ¿QUÉ SIGNIFICA SUSPENDER?
 *    A diferencia de una función normal que se ejecuta de principio a fin bloqueando
 *    el hilo, una función 'suspend' puede PAUSAR su ejecución.
 *    - Pausa: La corrutina libera el hilo en el que está trabajando.
 *    - Hilo libre: El hilo puede hacer otras cosas (como renderizar la UI).
 *    - Reanudación: Cuando la tarea (ej. red) termina, la corrutina "pide" un hilo
 *      para continuar donde se quedó.
 *
 * 2. ANALOGÍA DEL CAMARERO:
 *    - Bloqueante (Normal): Un camarero pide la comida a cocina y SE QUEDA ESPERANDO 
 *      frente al pase sin hacer nada hasta que el plato sale. No atiende a nadie más.
 *    - Suspendible (suspend): El camarero pide la comida, deja la nota y SE VA a atender
 *      otras mesas. Cuando la cocina avisa (callback/evento), el camarero vuelve a 
 *      recoger el plato y lo sirve.
 *
 * 3. BAJO EL CAPÓ:
 *    Kotlin transforma estas funciones en una "Máquina de Estados". No es magia,
 *    es el compilador pasando un objeto llamado 'Continuation' que sabe por qué
 *    línea de código iba la ejecución.
 */
suspend fun ejercicio1_ConceptoBasico() {
    println("--- 1. El concepto de Suspensión vs Bloqueo ---")

    // Esta función simula una tarea que suspende
    suspend fun tareaConSuspension(id: Int, tiempo: Long) {
        println("[${Thread.currentThread().name}] -> Tarea $id: Iniciando (Voy a suspender)")
        
        // delay() es una función suspend. 
        // Libera el hilo durante 'tiempo' ms.
        delay(tiempo) 
        
        println("[${Thread.currentThread().name}] -> Tarea $id: Reanudando (Ya tengo el resultado)")
    }

    val tiempoInicial = System.currentTimeMillis()

    // Ejecución secuencial en una corrutina
    // Aunque sea secuencial, el hilo queda LIBRE durante los delays
    tareaConSuspension(1, 500)
    tareaConSuspension(2, 500)

    val tiempoTotal = System.currentTimeMillis() - tiempoInicial
    println("Tiempo total: $tiempoTotal ms")
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 2: Paralelismo con Async/Await
// ----------------------------------------------------------------------------------------
/**
 * EXPLICACIÓN PROFUNDA DE 'async' Y 'await':
 *
 * 1. EL CONCEPTO DE 'PROMETIDO' (Deferred):
 *    Cuando usas 'async', Kotlin no te devuelve el resultado inmediatamente. Te devuelve
 *    un objeto 'Deferred<T>'. 
 *    - Piensa en ello como un "ticket de recogida" en una cafetería. Ya has hecho el pedido
 *      (la tarea ha empezado), pero el café no está listo. El ticket es la promesa de que
 *      tendrás el café en el futuro.
 *
 * 2. 'await()' COMO PUNTO DE SUSPENSIÓN:
 *    Cuando necesitas el valor real, llamas a '.await()'. 
 *    - Si el valor ya está listo: lo recibes al instante.
 *    - Si NO está listo: la corrutina se PAUSA (se suspende) en esa línea hasta que
 *      el valor llega. No bloquea el hilo, simplemente espera pacientemente.
 *
 * 3. PARALELISMO REAL:
 *    La clave es lanzar varios 'async' ANTES de llamar a ningún '.await()'. 
 *    Esto permite que todas las tareas se ejecuten al mismo tiempo en diferentes hilos
 *    (o aprovechando los huecos de suspensión).
 */
suspend fun ejercicio2_ParalelismoAsync() {
    println("\n--- 2. Paralelismo con 'async' y 'await' ---")

    suspend fun obtenerPrecio(producto: String): Int {
        delay(1000)
        println("  [${Thread.currentThread().name}] -> Precio de $producto obtenido.")
        return (10..100).random()
    }

    /**
     * DIFERENCIA ENTRE 'launch' y 'async':
     * 1. launch: "Lanzar y olvidar". Devuelve un 'Job'. Se usa para tareas que no 
     *    necesitan devolver un dato (ej. guardar en DB, mostrar un log).
     * 2. async: "Lanzar y esperar". Devuelve un 'Deferred<T>'. Se usa cuando el
     *    resultado de la tarea es necesario para el siguiente paso.
     */

    println("CASO A: Ejecución Secuencial (Lento)")
    val inicioSec = System.currentTimeMillis()
    coroutineScope {
        // Aquí NO usamos async, por lo que una espera a la otra
        val p1 = obtenerPrecio("Silla")
        val p2 = obtenerPrecio("Mesa")
        println("  Total: ${p1 + p2}€ | Tiempo: ${System.currentTimeMillis() - inicioSec}ms")
    }

    println("\nCASO B: Paralelismo Correcto (Rápido)")
    val inicioPar = System.currentTimeMillis()
    coroutineScope {
        // 1. Lanzamos las tareas (El "pedido" está en cocina)
        // Obtenemos 'Deferred<Int>' (los tickets)
        val ticket1 = async { obtenerPrecio("Silla") }
        val ticket2 = async { obtenerPrecio("Mesa") }

        println("  (Mientras se calculan los precios, puedo hacer otras cosas...)")
        delay(200) 

        // 2. Recogemos los resultados (Esperamos al ticket si hace falta)
        val resultado1 = ticket1.await() 
        val resultado2 = ticket2.await()

        val total = resultado1 + resultado2
        println("  Total: ${total}€ | Tiempo: ${System.currentTimeMillis() - inicioPar}ms")
    }

    println("\nCASO C: El ERROR COMÚN (Secuencial encubierto)")
    /**
     * Muchos alumnos cometen el error de hacer: 
     * val p1 = async { ... }.await()
     * val p2 = async { ... }.await()
     * Esto NO es paralelo, porque el primer .await() pausa la corrutina 
     * ANTES de que el segundo async llegue a ejecutarse.
     */
}

// ----------------------------------------------------------------------------------------
// EXTRA: Comparativa Visual (Suspensión vs Bloqueo)
// ----------------------------------------------------------------------------------------
/**
 * REGLA DE ORO DE LAS SUSPEND FUNCTIONS:
 *
 * "Suspending is NOT blocking".
 *
 * Si llamas a Thread.sleep(1000) dentro de una corrutina en el hilo Main,
 * la APP SE CONGELA por 1 segundo (Bloqueo).
 *
 * Si llamas a delay(1000) dentro de una corrutina en el hilo Main,
 * la APP SIGUE FUNCIONANDO (puedes hacer scroll, pulsar botones) mientras
 * la corrutina espera (Suspensión).
 */

// ----------------------------------------------------------------------------------------
// EJERCICIO 3: Dispatchers (¿Dónde se ejecuta el código?)
// ----------------------------------------------------------------------------------------
suspend fun ejercicio3_ContextosYDispatchers() {
    println("\n--- 3. Dispatchers y Contextos ---")

    /**
     * TIPOS DE DISPATCHERS:
     * 1. Dispatchers.Main: Para la UI (Android/Compose). No disponible en proyectos JVM puros sin librerías de UI.
     * 2. Dispatchers.IO: Optimizado para Entrada/Salida (Red, Base de datos, Archivos).
     * 3. Dispatchers.Default: Optimizado para uso intensivo de CPU (Algoritmos, filtros de imagen).
     * 4. Dispatchers.Unconfined: No confina la corrutina a ningún hilo específico.
     */

    withContext(Dispatchers.Default) {
        println("[${Thread.currentThread().name}] Trabajando en CPU (Default)")
    }

    withContext(Dispatchers.IO) {
        println("[${Thread.currentThread().name}] Haciendo petición a API (IO)")
    }
    
    // Podemos crear nuestros propios pools de hilos
    val miContexto = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    withContext(miContexto) {
        println("[${Thread.currentThread().name}] Trabajando en mi hilo personalizado")
    }
    miContexto.close()
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 4: Ciclo de Vida (Scopes) y Cancelación
// ----------------------------------------------------------------------------------------
suspend fun ejercicio4_CicloDeVidaYCancion() {
    println("\n--- 4. Ciclo de Vida y Cancelación ---")

    /**
     * ESTRUCTURA DE UNA CORRUTINA:
     * El 'CoroutineScope' controla el ciclo de vida. Si el Scope se cancela,
     * todas las corrutinas hijas se cancelan.
     */

    val miScope = CoroutineScope(Dispatchers.Default + Job())

    val job = miScope.launch {
        try {
            repeat(100) { i ->
                println("Trabajando en segundo plano $i...")
                delay(200)
            }
        } catch (e: CancellationException) {
            println("La corrutina ha detectado la cancelación.")
        } finally {
            println("Limpieza de recursos tras cancelación.")
        }
    }

    delay(700) // Dejamos que trabaje un poco
    println("Cancelando el Job...")
    job.cancelAndJoin() // Cancelamos y esperamos a que termine su bloque 'finally'
    println("Corrutina finalizada correctamente.")
}

// ----------------------------------------------------------------------------------------
// SIMULACIÓN EN COMPOSE (Concepto Teórico)
// ----------------------------------------------------------------------------------------
/**
 * EXPLICACIÓN PARA ALUMNOS (SIMULACIÓN JETPACK COMPOSE):
 *
 * En Android/Compose, no usamos 'runBlocking'. Usamos:
 *
 * 1. viewModelScope.launch { ... }: Se cancela automáticamente si el ViewModel muere.
 * 2. rememberCoroutineScope(): Para lanzar corrutinas desde la UI (ej. al pulsar un botón).
 * 3. LaunchedEffect(key) { ... }: Para efectos secundarios que dependen del estado.
 *
 * REGLA DE ORO:
 * NUNCA bloquees el hilo Main. Usa Dispatchers.IO para red/disco y Dispatchers.Main
 * para actualizar variables de estado que la UI esté observando.
 */
