package com.pmdm.example.projects.kotlinapuntes

fun main() {
    println("--- REPASO DE LISTAS Y ESTRUCTURAS DE DATOS EN KOTLIN ---")
    println("¡Hola, futuros desarrolladores de Compose! Hoy repasamos las colecciones.")
    println("\n---------------------------------------------------------")

    ejercicio1CreacionListasInmutables()
    println("\n---------------------------------------------------------")

    ejercicio2CreacionListasMutables()
    println("\n---------------------------------------------------------")

    ejercicio3AccesoYEliminacionElementos()
    println("\n---------------------------------------------------------")

    ejercicio4RecorridoListas()
    println("\n---------------------------------------------------------")

    ejercicio5FiltradoYTransformacion()
    println("\n---------------------------------------------------------")

    ejercicio6SetYMap()
    println("\n---------------------------------------------------------")

    ejercicio7ListaDeObjetosPersonalizados()
    println("\n---------------------------------------------------------")

    ejercicio8OperacionesAvanzadasConListasDeObjetos()
    println("\n---------------------------------------------------------")

    println("\n--- FIN DEL REPASO ---")
    println("¡Espero que hayáis aprendido mucho! A practicar para vuestras apps con Compose.")
}

data class TareaJesus(val id: Int);

/**
 * Ejercicio 1: Creación de Listas Inmutables
 *
 * Objetivo: Entender cómo crear listas de solo lectura y las diferentes formas de inicializarlas.
 * Concepto clave: Las listas inmutables no se pueden modificar una vez creadas. Son seguras para compartir.
 */
fun ejercicio1CreacionListasInmutables() {
    println("EJERCICIO 1: Creación de Listas Inmutables (solo lectura)")
    println("-------------------------------------------------------")

    var listaTareas = listOf<TareaJesus>(
        TareaJesus(1),
        TareaJesus(id=2),
        TareaJesus(id=3)
    )

    // Forma 1: Usando listOf() - La más común y recomendada
    // Aquí inferimos el tipo de la lista (List<String>)
    val diasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    println("Lista de días de la semana (String): $diasSemana")
    println("Tipo de la lista: ${diasSemana::class.simpleName} (de ${diasSemana[0]::class.simpleName}s)")

    // Forma 2: Especificando el tipo explícitamente
    val numerosPrimos: List<Int> = listOf(2, 3, 5, 7, 11, 13)
    println("Lista de números primos (Int): $numerosPrimos")
    println("Tipo de la lista: ${numerosPrimos::class.simpleName} (de ${numerosPrimos[0]::class.simpleName}s)")

    // Forma 3: Lista vacía (¡es importante especificar el tipo si está vacía!)
    val listaVacia: List<Double> = emptyList()
    println("Lista vacía (Double): $listaVacia")
    println("¿Está vacía? ${listaVacia.isEmpty()}")

    // Intentar modificar una lista inmutable provocará un error de compilación o de ejecución.
    // Descomenta la siguiente línea para ver el error:
    // diasSemana.add("Nuevo Día") // Error de compilación: "Unsupported operation" o "Val cannot be reassigned"

    println("\nRecordatorio: Las listas inmutables son ideales cuando los datos no van a cambiar y se busca seguridad.")
}

/**
 * Ejercicio 2: Creación de Listas Mutables
 *
 * Objetivo: Entender cómo crear listas que se pueden modificar (añadir, eliminar, actualizar elementos).
 * Concepto clave: Las listas mutables son flexibles y se usan cuando la colección de elementos va a cambiar.
 */
fun ejercicio2CreacionListasMutables() {
    println("EJERCICIO 2: Creación de Listas Mutables (lectura y escritura)")
    println("-----------------------------------------------------------")

    // Forma 1: Usando mutableListOf() - La más común y recomendada para listas mutables
    val tareasPendientes = mutableListOf("Comprar leche", "Estudiar Kotlin", "Hacer ejercicio")
    println("Tareas iniciales: $tareasPendientes")

    // Añadir elementos
    tareasPendientes.add("Llamar al médico")
    tareasPendientes.add(0, "Preparar desayuno") // Añadir en una posición específica
    println("Tareas después de añadir: $tareasPendientes")

    // Eliminar elementos
    tareasPendientes.remove("Comprar leche") // Eliminar por valor
    println("Tareas después de eliminar 'Comprar leche': $tareasPendientes")

    tareasPendientes.removeAt(0) // Eliminar por índice (el primer elemento)
    println("Tareas después de eliminar el primer elemento: $tareasPendientes")

    // Actualizar elementos (por índice)
    if (tareasPendientes.size > 1) {
        tareasPendientes[1] = "Repasar Compose UI" // Cambiar "Estudiar Kotlin" por "Repasar Compose UI"
    }
    println("Tareas después de actualizar: $tareasPendientes")

    // Forma 2: Usando ArrayList (herencia de Java, menos idiomático en Kotlin si no es necesario)
    val nombresAmigos = ArrayList<TareaJesus>() // Es mutable por defecto
    nombresAmigos.add(TareaJesus(id=1))
    nombresAmigos.add(TareaJesus(id=2))
    println("Nombres de amigos (ArrayList): $nombresAmigos")

    // Otras operaciones útiles con listas mutables
    tareasPendientes.clear() // Vaciar la lista
    println("Tareas después de limpiar: $tareasPendientes")
    println("¿Está vacía la lista de tareas? ${tareasPendientes.isEmpty()}")

    println("\nRecordatorio: mutableListOf es tu amigo para colecciones que cambian dinámicamente.")
}

/**
 * Ejercicio 3: Acceso a Elementos y Eliminación
 *
 * Objetivo: Practicar las diferentes formas de acceder a elementos de una lista y eliminarlos.
 */
fun ejercicio3AccesoYEliminacionElementos() {
    println("EJERCICIO 3: Acceso y Eliminación de Elementos")
    println("----------------------------------------------")

    val ciudades = mutableListOf("Madrid", "Barcelona", "Valencia", "Sevilla", "Bilbao")
    println("Ciudades: $ciudades")

    // Acceso por índice
    println("La primera ciudad es: ${ciudades[0]}")
    println("La tercera ciudad es: ${ciudades[2]}")

    // Acceso seguro: getOrNull() o .getOrElse()
    // Si el índice no existe, getOrNull devuelve null
    println("Ciudad en índice 10 (seguro con getOrNull): ${ciudades.getOrNull(10)}")

    // getOrElse permite especificar un valor por defecto si el índice no existe
    println("Ciudad en índice 10 (seguro con getOrElse): ${ciudades.getOrElse(10) { "Ciudad no encontrada" }}")

    // Obtener el primer y último elemento
    println("Primer elemento: ${ciudades.first()}")
    println("Último elemento: ${ciudades.last()}")

    // Si la lista puede estar vacía, firstOrNull() y lastOrNull() son más seguros
    val listaNumeros = mutableListOf(10, 20, 30)
    println("Primer número: ${listaNumeros.firstOrNull()}")
    val listaVacia = mutableListOf<Int>()
    println("Primer número de lista vacía: ${listaVacia.firstOrNull()}") // Devuelve null

    // Eliminación por valor
    ciudades.remove("Valencia")
    println("Ciudades después de eliminar 'Valencia': $ciudades")

    // Eliminación por condición (removeIf - para colecciones mutables)
    val puntuaciones = mutableListOf(85, 92, 78, 65, 95, 80)
    println("Puntuaciones iniciales: $puntuaciones")
    puntuaciones.removeIf { it < 70 } // Eliminar todas las puntuaciones menores de 70
    println("Puntuaciones después de eliminar las menores de 70: $puntuaciones")

    // Eliminación de un rango de elementos (no hay un método directo en List para rango por índice,
    // pero se puede hacer con bucles o iteradores. Es más común en ArrayList de Java.)
    // Para simplificar, si necesitas eliminar varios elementos por índice, podrías hacer:
    val colores = mutableListOf("Rojo", "Verde", "Azul", "Amarillo", "Blanco", "Negro")
    println("Colores iniciales: $colores")
    // Eliminar los elementos en el índice 1 y 2 (Verde y Azul)
    if (colores.size >= 3) { // Asegúrate de que los índices existan
        colores.removeAt(2) // Elimina "Azul" (ahora en índice 2)
        colores.removeAt(1) // Elimina "Verde" (ahora en índice 1)
    }
    println("Colores después de eliminar 'Verde' y 'Azul': $colores")

    println("\nTruco: Cuando eliminéis por índice, tened cuidado si la lista se encoge y los índices cambian.")
}

/**
 * Ejercicio 4: Recorrido de Listas
 *
 * Objetivo: Mostrar las diferentes formas de iterar sobre los elementos de una lista.
 */
fun ejercicio4RecorridoListas() {
    println("EJERCICIO 4: Recorrido de Listas")
    println("--------------------------------")

    val frutas = listOf("Manzana", "Pera", "Naranja", "Kiwi", "Fresa")
    println("Frutas: $frutas")

    // Forma 1: Bucle for-in (la más común y sencilla)
    println("\nRecorrido con for-in:")
    for (fruta in frutas) {
        println("  -> $fruta")
    }

    // Forma 2: Bucle for-in con índices (cuando necesitas el índice del elemento)
    println("\nRecorrido con for-in y índices (usando .indices):")
    for (i in frutas.indices) {
        println("  La fruta en el índice $i es ${frutas[i]}")
    }

    // Forma 3: Bucle for-in con índices y valor (usando .withIndex())
    println("\nRecorrido con for-in y índices/valor (usando .withIndex()):")
    for ((index, fruta) in frutas.withIndex()) {
        println("  En la posición $index tenemos: $fruta")
    }

    // Forma 4: Usando forEach (función de orden superior, muy idiomática en Kotlin)
    println("\nRecorrido con forEach:")
    frutas.forEach { fruta ->
        println("  Comiendo $fruta")
    }

    // forEach también puede acceder al índice, aunque es menos común
    println("\nRecorrido con forEachIndexed:")
    frutas.forEachIndexed { index, fruta ->
        println("  Fruta nº${index + 1}: $fruta")
    }

    println("\nConsejo: forEach es muy legible para operaciones simples por cada elemento. for-in para lógica más compleja.")
}

/**
 * Ejercicio 5: Filtrado y Transformación de Listas
 *
 * Objetivo: Aprender a crear nuevas listas a partir de una existente, filtrando o transformando sus elementos.
 * Concepto clave: Las funciones de colección (filter, map, etc.) son inmutables y devuelven nuevas listas.
 */
fun ejercicio5FiltradoYTransformacion() {
    println("EJERCICIO 5: Filtrado y Transformación de Listas")
    println("-----------------------------------------------")

    val numeros = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("Lista original de números: $numeros")

    // Filtrado: filter { condición }
    // Devuelve una nueva lista con los elementos que cumplen la condición.
    val numerosPares = numeros.filter { it % 2 == 0 } // 'it' es el elemento actual
    println("Números pares: $numerosPares")

    val numerosGrandes = numeros.filter { it > 5 }
    println("Números mayores que 5: $numerosGrandes")

    // Transformación: map { transformación }
    // Devuelve una nueva lista con los resultados de aplicar la transformación a cada elemento.
    val numerosDuplicados = numeros.map { it * 2 }
    println("Números duplicados: $numerosDuplicados")

    val numerosAStrings = numeros.map { "Número: $it" }
    println("Números transformados a Strings: $numerosAStrings")

    // Encadenamiento de operaciones (muy común y potente)
    // Queremos los cuadrados de los números impares
    val cuadradosImpares = numeros
        .filter { it % 2 != 0 } // Primero filtramos los impares
        .map { it * it }       // Luego los elevamos al cuadrado
    println("Cuadrados de los números impares: $cuadradosImpares")

    val nombres = listOf("alice", "BOB", "Charlie", "David")
    println("Nombres originales: $nombres")
    val nombresCapitalizados = nombres.map { it.capitalize() } // O it.replaceFirstChar { it.uppercase() } en versiones recientes
    println("Nombres capitalizados: $nombresCapitalizados")

    val nombresLargos = nombres.filter { it.length > 4 }
        .map { it.uppercase() }
    println("Nombres largos en mayúsculas: $nombresLargos")

    println("\nTip: Estas funciones son la base de muchas operaciones de datos en Compose y son muy eficientes.")
}

/**
 * Ejercicio 6: Estructuras de Datos Avanzadas - Set y Map
 *
 * Objetivo: Introducir Set (conjuntos) y Map (mapas o diccionarios) y sus usos principales.
 * Concepto clave: Set garantiza unicidad, Map almacena pares clave-valor.
 */
fun ejercicio6SetYMap() {
    println("EJERCICIO 6: Estructuras de Datos Avanzadas - Set y Map")
    println("-----------------------------------------------------")

    // SET (Conjunto): Colección de elementos únicos
    println("\n--- SET (Conjunto) ---")
    val vocales = mutableSetOf('a', 'e', 'i', 'o', 'u', 'a', 'e') // Se ignoran los duplicados
    println("Vocales (Set): $vocales") // Verás que 'a' y 'e' solo aparecen una vez

    vocales.add('o') // Añadir un elemento que ya existe no hace nada
    vocales.add('p') // Añadir un nuevo elemento
    println("Vocales después de añadir 'o' y 'p': $vocales")

    vocales.remove('e')
    println("Vocales después de eliminar 'e': $vocales")

    println("¿Contiene el set la vocal 'u'? ${vocales.contains('u')}")
    println("Número de elementos únicos: ${vocales.size}")

    // MAP (Mapa / Diccionario): Colección de pares clave-valor únicos
    println("\n--- MAP (Mapa / Diccionario) ---")

    // Creación de un Map inmutable
    val capitalesMundo = mapOf(
        "España" to "Madrid",
        "Francia" to "París",
        "Alemania" to "Berlín",
        "Italia" to "Roma"
    )
    println("Capitales del mundo (Map inmutable): $capitalesMundo")

    // Acceso a valores por clave
    println("Capital de España: ${capitalesMundo["España"]}")
    println("Capital de Italia: ${capitalesMundo.get("Italia")}")

    // Si la clave no existe, devuelve null
    println("Capital de Portugal: ${capitalesMundo["Portugal"]}")

    // Map mutable: mutableMapOf()
    val notasAlumnos = mutableMapOf<String, Int>()
    notasAlumnos["Ana"] = 8
    notasAlumnos["Pedro"] = 7
    notasAlumnos["María"] = 9
    println("Notas de alumnos (Map mutable): $notasAlumnos")

    // Actualizar un valor
    notasAlumnos["Ana"] = 9
    println("Notas después de actualizar la nota de Ana: $notasAlumnos")

    // Añadir un nuevo par clave-valor
    notasAlumnos["Luis"] = 6
    println("Notas después de añadir a Luis: $notasAlumnos")

    // Eliminar un par clave-valor
    notasAlumnos.remove("Pedro")
    println("Notas después de eliminar a Pedro: $notasAlumnos")

    println("¿Contiene el mapa la clave 'María'? ${notasAlumnos.containsKey("María")}")
    println("¿Contiene el mapa el valor '9'? ${notasAlumnos.containsValue(9)}")
    println("Todas las claves: ${notasAlumnos.keys}")
    println("Todos los valores: ${notasAlumnos.values}")

    println("\nReflexión: Los Mapas son muy útiles para almacenar configuraciones, traducciones o cualquier dato con un identificador único.")
}

/**
 * Clase de datos para el ejercicio 7 y 8
 */
data class Alumno(val id: Int, val nombre: String, val edad: Int, val notaMedia: Double)

/**
 * Ejercicio 7: Lista de Objetos Personalizados
 *
 * Objetivo: Trabajar con listas que contienen instancias de clases de datos personalizadas.
 */
fun ejercicio7ListaDeObjetosPersonalizados() {
    println("EJERCICIO 7: Lista de Objetos Personalizados")
    println("--------------------------------------------")

    // Creamos una lista mutable de objetos Alumno
    val listaAlumnos = mutableListOf(
        Alumno(1, "Ana García", 18, 8.5),
        Alumno(2, "Juan Pérez", 19, 7.2),
        Alumno(3, "María López", 18, 9.1),
        Alumno(4, "Carlos Ruiz", 20, 6.8),
        Alumno(5, "Elena Martín", 19, 8.9)
    )

    println("Lista inicial de alumnos:")
    listaAlumnos.forEach { println("  $it") }

    // Añadir un nuevo alumno
    listaAlumnos.add(Alumno(6, "Sofía Díaz", 18, 7.5))
    println("\nLista después de añadir a Sofía:")
    listaAlumnos.forEach { println("  $it") }

    // Eliminar un alumno (por valor, gracias al equals/hashCode de data class)
    listaAlumnos.remove(Alumno(4, "Carlos Ruiz", 20, 6.8)) // Ojo: deben coincidir todos los campos
    println("\nLista después de eliminar a Carlos:")
    listaAlumnos.forEach { println("  $it") }

    // Encontrar un alumno por ID
    val alumnoEncontrado = listaAlumnos.find { it.id == 3 }
    println("\nAlumno con ID 3: $alumnoEncontrado")

    // Filtrar alumnos mayores de 18 años
    val alumnosMayoresDe18 = listaAlumnos.filter { it.edad > 18 }
    println("\nAlumnos mayores de 18 años:")
    alumnosMayoresDe18.forEach { println("  ${it.nombre} (${it.edad} años)") }

    // Transformar la lista para obtener solo los nombres
    val nombresAlumnos = listaAlumnos.map { it.nombre }
    println("\nNombres de los alumnos: $nombresAlumnos")

    println("\nImportante: Las 'data class' son ideales para modelar datos, ya que Kotlin genera automáticamente equals, hashCode, toString y copy.")
}

/**
 * Ejercicio 8: Operaciones Avanzadas con Listas de Objetos
 *
 * Objetivo: Explorar funciones de ordenación, agregación y agrupación con listas de objetos.
 */
fun ejercicio8OperacionesAvanzadasConListasDeObjetos() {
    println("EJERCICIO 8: Operaciones Avanzadas con Listas de Objetos")
    println("------------------------------------------------------")

    val estudiantes = listOf(
        Alumno(101, "Ana Belén", 20, 7.8),
        Alumno(102, "Luis Torres", 22, 6.5),
        Alumno(103, "Carla Santos", 20, 9.2),
        Alumno(104, "Diego Rico", 21, 7.8),
        Alumno(105, "Eva Castro", 20, 8.5),
        Alumno(106, "Fran Mora", 22, 7.1)
    )

    println("Lista original de estudiantes:")
    estudiantes.forEach { println("  $it") }

    // Ordenar la lista (sortedBy, sortedByDescending)
    // Ordenar por nota media, de mayor a menor
    val estudiantesOrdenadosPorNota = estudiantes.sortedByDescending { it.notaMedia }
    println("\nEstudiantes ordenados por nota media (descendente):")
    estudiantesOrdenadosPorNota.forEach { println("  ${it.nombre}: ${it.notaMedia}") }

    // Ordenar por edad y luego por nombre
    val estudiantesOrdenadosPorEdadYNombre = estudiantes
        .sortedWith(compareBy({ it.edad }, { it.nombre }))
    println("\nEstudiantes ordenados por edad y luego por nombre:")
    estudiantesOrdenadosPorEdadYNombre.forEach { println("  ${it.nombre} (${it.edad} años)") }

    // Agregación (sumOf, average, count)
    val sumaNotas = estudiantes.sumOf { it.notaMedia }
    println("\nSuma total de notas medias: $sumaNotas")

    val mediaNotas = estudiantes.averageBy { it.notaMedia }
    println("Nota media general de los estudiantes: %.2f".format(mediaNotas))

    val numeroAlumnosConAprobado = estudiantes.count { it.notaMedia >= 5.0 }
    println("Número de alumnos con nota aprobada: $numeroAlumnosConAprobado")

    // Agrupación (groupBy)
    // Agrupar estudiantes por edad
    val estudiantesPorEdad = estudiantes.groupBy { it.edad }
    println("\nEstudiantes agrupados por edad:")
    estudiantesPorEdad.forEach { (edad, listaAlumnosDeEdad) ->
        println("  Edad $edad:")
        listaAlumnosDeEdad.forEach { alumno -> println("    - ${alumno.nombre}") }
    }

    // Obtener la nota media por edad
    println("\nNota media por edad:")
    estudiantesPorEdad.forEach { (edad, listaAlumnosDeEdad) ->
        val media = listaAlumnosDeEdad.averageBy { it.notaMedia }
        println("  Edad $edad: %.2f".format(media))
    }

    // Funciones adicionales: any, all, none
    println("\n--- Funciones booleanas (any, all, none) ---")
    val hayAlumnosConSobresaliente = estudiantes.any { it.notaMedia >= 9.0 }
    println("¿Hay algún alumno con sobresaliente? $hayAlumnosConSobresaliente")

    val todosAlumnosAprobados = estudiantes.all { it.notaMedia >= 5.0 }
    println("¿Todos los alumnos han aprobado? $todosAlumnosAprobados")

    val ningunAlumnoSuspendido = estudiantes.none { it.notaMedia < 5.0 }
    println("¿Ningún alumno ha suspendido? $ningunAlumnoSuspendido")

    println("\nEstas operaciones son extremadamente potentes para manipular datos de forma declarativa y funcional.")
    println("En Compose, a menudo trabajaréis con listas de estados o de elementos UI, y estas herramientas serán vuestras mejores aliadas.")
}

// Función de extensión para calcular la media de una propiedad de una lista
fun <T> List<T>.averageBy(selector: (T) -> Double): Double {
    return this.map(selector).average()
}