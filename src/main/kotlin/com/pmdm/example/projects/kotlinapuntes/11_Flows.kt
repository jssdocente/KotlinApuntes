package com.pmdm.example.projects.kotlinapuntes

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * APUNTES SOBRE KOTLIN FLOWS
 *
 * Un Flow es un flujo de datos asíncrono que emite múltiples valores secuencialmente.
 * A diferencia de las Listas, los Flows son "fríos" (no se ejecutan hasta que se recolectan)
 * y no bloquean el hilo principal.
 */

fun main() = runBlocking {
    println("=== EJERCICIOS DE KOTLIN FLOWS ===\n")

    ejercicio1_ListasVsFlows()
    ejercicio2_OperadoresFlow()
    ejercicio3_FlowsEnCompose()
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 1: Listas (Eager) vs Flows (Lazy)
// ----------------------------------------------------------------------------------------
suspend fun ejercicio1_ListasVsFlows() {
    println("--- 1. Comparativa: Listas vs Flows ---")

    // Escenario Listas: Bloqueante y consume memoria de golpe
    fun obtenerLista(): List<Int> {
        println("Generando lista completa...")
        return listOf(1, 2, 3).map {
            Thread.sleep(500) // Simula computación costosa bloqueante
            it * 10
        }
    }

    println("Llamando a obtenerLista()...")
    val lista = obtenerLista()
    println("Lista recibida. Procesando elementos:")
    lista.forEach { println("Procesando $it") }

    println("\n---")

    // Escenario Flows: Asíncrono, reactivo y no bloqueante
    fun obtenerFlow(): Flow<Int> = flow {
        println("Empezando a emitir en el Flow...")
        for (i in 1..3) {
            delay(500) // Simula suspensión (no bloquea el hilo)
            emit(i * 10)
        }
    }

    println("Llamando a obtenerFlow() (No hace nada hasta 'collect')...")
    val elFlow = obtenerFlow()
    
    /**
     * ¿QUÉ ES EL 'COLLECT'? (Concepto Crítico)
     *
     * 1. ACTIVACIÓN: Un Flow es "frío". 'obtenerFlow()' no ejecuta el código dentro del bloque 'flow { ... }'.
     *    Solo define el plan de trabajo. 'collect' es el "grifo" que abre el flujo.
     * 2. SUSPENSIÓN: 'collect' es una suspend function. Bloquea la ejecución de esta corrutina
     *    (pero no el hilo) hasta que el Flow termine o sea cancelado.
     * 3. CONSUMO: Por cada 'emit' en el Flow, se ejecuta el bloque de código dentro de 'collect'.
     */
    println("Flow configurado. Empezando a recolectar:")
    elFlow.collect { valor ->
        println("Recibido del Flow: $valor")
    }
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 2: Operadores y Transformaciones
// ----------------------------------------------------------------------------------------
suspend fun ejercicio2_OperadoresFlow() {
    println("\n--- 2. Operadores de Flow ---")

    val numerosFlow = (1..5).asFlow()

    /**
     * OPERADORES INTERMEDIOS vs TERMINALES:
     *
     * 1. INTERMEDIOS (.filter, .map): Son como las piezas de una tubería. Transforman el dato
     *    pero NO activan el flujo. Devuelven un nuevo Flow.
     * 2. TERMINALES (.collect, .first): Son los que activan la ejecución. Sin un operador
     *    terminal, no pasa nada.
     */
    numerosFlow
        .filter { it % 2 == 0 } // Solo deja pasar los pares (2, 4)
        .map { "Número transformado: $it" } // Transforma el Int en String
        .collect { println(it) } // TERMINAL: Aquí es donde realmente se ejecutan el filter y el map

    println("\n--- Transformación compleja (transform) ---")
    (1..3).asFlow()
        .transform { request ->
            emit("Enviando petición $request")
            delay(200)
            emit("Respuesta recibida para $request")
        }
        .collect { println(it) }
}

// ----------------------------------------------------------------------------------------
// EJERCICIO 3: Simulación de Flows en Jetpack Compose
// ----------------------------------------------------------------------------------------

/**
 * En Compose, los Flows se suelen convertir a 'State' para que la UI se recomponga
 * automáticamente cuando el valor cambia. El estándar es usar StateFlow.
 */
data class UiState(
    val nombreUsuario: String = "Anónimo",
    val saldo: Double = 0.0,
    val cargando: Boolean = false
)

class FakeViewModel {
    // StateFlow privado para mutación, expuesto como Flow público inmutable
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * ¿QUÉ ES EL '.update'? (Diferenciación con Scope Functions)
     *
     * El método '.update' NO es una Scope Function estándar (como let, run, apply).
     * Es una función de extensión específica para 'MutableStateFlow'.
     *
     * 1. INMUTABILIDAD vs MUTACIÓN:
     *    - NO cambia los valores "dentro" del objeto antiguo.
     *    - GENERA un nuevo objeto (normalmente con .copy()) y REEMPLAZA el valor
     *      completo en el StateFlow.
     *    - Esto es vital: en Compose/Reactivo trabajamos con objetos inmutables. Si
     *      modificáramos una propiedad de un objeto existente (ej. it.saldo = 10),
     *      el Flow podría no detectar el cambio y la UI no se actualizaría.
     *
     * 2. SIMILITUD CON SCOPE FUNCTIONS: Al igual que 'let', recibe una lambda donde 'it'
     *    representa el estado actual, facilitando la creación de esa copia modificada.
     *
     * 3. ATOMICIDAD: A diferencia de una actualización manual (_uiState.value = ...),
     *    '.update' es atómica y thread-safe. Si dos hilos llaman a .update a la vez,
     *    ambos cambios se procesarán correctamente sin perderse.
     */
    suspend fun cargarDatosUsuario() {

        _uiState.update { it.copy(cargando = true) }

        /*
        //El método .update es equivalente a realizar esto.
        // 1. Obtener el valor actual del StateFlow
        val estadoActual = _uiState.value
        // 2. Crear una nueva instancia modificada usando 'copy'
        val nuevoEstado = estadoActual.copy(cargando = true)
        // 3. Asignar el nuevo estado al StateFlow
        _uiState.value = nuevoEstado
        */
        
        delay(1500) // Simula red
        
        _uiState.update { 
            // it.copy() devuelve una INSTANCIA NUEVA del objeto UiState
            it.copy(
                nombreUsuario = "Juan Pérez",
                saldo = 1250.50,
                cargando = false
            )
        }
    }

    fun recargarSaldo() {
        _uiState.update { it.copy(saldo = it.saldo + 100) }
    }
}

suspend fun ejercicio3_FlowsEnCompose() {
    println("\n--- 3. Simulación de Flows en Jetpack Compose ---")
    
    val viewModel = FakeViewModel()
    
    // Simulación de un Composable que se suscribe al StateFlow
    // En Compose real usaríamos: val state by viewModel.uiState.collectAsState()
    
    /**
     * EXPLICACIÓN DEL COLLECT EN CONTEXTO DE UI (SIMULACIÓN COMPOSE):
     *
     * En una app real, 'collect' (o 'collectAsState') es el mecanismo que mantiene
     * la pantalla actualizada.
     *
     * 1. SUSCRIPCIÓN PERPETUA: A diferencia del ejercicio 1, un StateFlow nunca termina.
     *    Siempre tiene un valor (el 'estado actual'). El 'collect' se queda "escuchando"
     *    eternamente hasta que la pantalla desaparece (o cancelamos el Job).
     *
     * 2. RECOMPOSICIÓN: Cada vez que el ViewModel hace un '_uiState.update', el 'collect'
     *    se activa, recibe el nuevo objeto UiState y "redibuja" la UI (aquí lo simulamos con un println).
     *
     * 3. SCOPE (Alcance): Lanzamos el collect en un nuevo CoroutineScope para que no bloquee
     *    el hilo principal y podamos simular acciones del usuario simultáneamente.
     */
    val job = CoroutineScope(Dispatchers.Default).launch {
        viewModel.uiState.collect { state ->
            println("[UI RECOMPOSE] Renderizando: $state (Hash: ${System.identityHashCode(state)})")
        }
    }

    println("Acción: Cargar datos...")
    viewModel.cargarDatosUsuario()
    
    delay(500)
    println("Acción: El usuario añade saldo...")
    viewModel.recargarSaldo()
    
    delay(500)
    job.cancel() // Detenemos la suscripción de la "UI"
}
