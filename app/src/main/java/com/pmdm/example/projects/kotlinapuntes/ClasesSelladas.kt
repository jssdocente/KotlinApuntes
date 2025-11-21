package com.pmdm.example.projects.kotlinapuntes

// SealedClassesEnKotlin.kt

fun main() {
    println("--- Ejercicio 1: Estado de Carga de Datos (Sealed Class Básica) ---")
    ejercicio1EstadoCargaDatos()

    println("\n--- Ejercicio 2: Eventos de UI (Sealed Class y Data Class) ---")
    ejercicio2EventosUI()

    println("\n--- Ejercicio 3: Resultado de Operación (Sealed Class y Generics) ---")
    ejercicio3ResultadoOperacion()

    println("\n--- Ejercicio 4: Navegación en Compose (Sealed Class para Rutas) ---")
    ejercicio4NavegacionCompose()

    println("\n--- Ejercicio 5: Sealed Interface ---")
    ejercicio5SealedInterface()
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 1: Estado de Carga de Datos (Sealed Class Básica)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Una `sealed class` se utiliza para representar un conjunto restringido o jerarquía finita de clases.
 * Todas las subclases de una `sealed class` deben estar definidas en el mismo archivo Kotlin (o en el mismo módulo
 * si es Kotlin 1.5+ y las subclases son interfaces o clases de datos). Esto garantiza que el compilador conozca
 * todas las posibles subclases.
 *
 * **Utilidad en Compose:** Es perfecta para modelar los diferentes estados de la UI (User Interface)
 * o de los datos. Por ejemplo, cuando se carga información de una red, el componente de UI podría
 * estar en estado "Cargando", "Éxito" o "Error".
 *
 * **Explicación:** En este ejemplo, `LoadState` puede ser uno de tres estados predefinidos.
 * `Loading` y `Success` son `object` porque no necesitan datos adicionales, solo representan un estado.
 * `Error` es una `data class` porque necesita llevar información sobre el error (un mensaje).
 *
 * Cuando usas un `when` expression con una `sealed class`, el compilador de Kotlin puede inferir que
 * has cubierto todos los casos posibles si no necesitas un `else` (esto se conoce como "exhaustividad"),
 * lo que reduce errores en tiempo de ejecución.
 */
sealed class LoadState {
    object Loading : LoadState() // Representa el estado de carga
    data class Success(val data: String) : LoadState() // Representa el éxito con datos
    data class Error(val message: String) : LoadState() // Representa un error con un mensaje
}

fun ejercicio1EstadoCargaDatos() {
    // Simulamos diferentes estados de carga
    val estado1: LoadState = LoadState.Loading
    val estado2: LoadState = LoadState.Success("Datos del servidor cargados correctamente.")
    val estado3: LoadState = LoadState.Error("Fallo de red: No se pudo conectar.")

    // Función que procesa el estado de carga
    fun procesarEstado(estado: LoadState) {
        when (estado) {
            is LoadState.Loading -> println("Mostrando indicador de carga...")
            is LoadState.Success -> println("Datos recibidos: ${estado.data}. Actualizando UI.")
            is LoadState.Error -> println("Error: ${estado.message}. Mostrar mensaje de error en UI.")
        }
    }

    println("Simulando carga de datos...")
    procesarEstado(estado1)
    procesarEstado(estado2)
    procesarEstado(estado3)

    // Un ejemplo de cómo Compose usaría esto:
    // @Composable
    // fun MyScreen(viewModel: MyViewModel) {
    //     val loadState by viewModel.loadState.collectAsState() // Observa el estado del ViewModel
    //
    //     when (loadState) {
    //         is LoadState.Loading -> {
    //             CircularProgressIndicator() // Muestra un spinner
    //         }
    //         is LoadState.Success -> {
    //             Text(text = (loadState as LoadState.Success).data) // Muestra los datos
    //         }
    //         is LoadState.Error -> {
    //             Text(text = "Error: ${(loadState as LoadState.Error).message}", color = Color.Red) // Muestra el error
    //         }
    //     }
    // }
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 2: Eventos de UI (Sealed Class y Data Class)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Las `sealed classes` son excelentes para modelar eventos que pueden ocurrir en la UI.
 * Cada subclase representa un tipo específico de evento.
 *
 * **Utilidad en Compose:** En Compose, a menudo pasarás "eventos" (acciones del usuario)
 * desde tus componentes Composables a un ViewModel o a una función de manejo de eventos.
 * Una `sealed class` para eventos hace que este flujo sea muy claro y seguro, ya que
 * sabes exactamente qué tipos de eventos puedes recibir.
 *
 * **Explicación:** `UiEvent` define los eventos que un usuario puede generar. Cada evento
 * es una `data class` si necesita llevar información (como el texto de un campo de entrada)
 * o un `object` si solo la ocurrencia del evento es suficiente (como un click).
 */
sealed class UiEvent {
    object ClickBotonGuardar : UiEvent() // Evento de click en el botón Guardar
    data class TextoIntroducido(val texto: String) : UiEvent() // Evento de texto modificado en un campo
    object ScrollAbajo : UiEvent() // Evento de scroll hacia abajo
    data class ItemSeleccionado(val id: Int) : UiEvent() // Evento de selección de un ítem con su ID
}

fun ejercicio2EventosUI() {
    // Simulamos la recepción de diferentes eventos de UI
    val evento1: UiEvent = UiEvent.ClickBotonGuardar
    val evento2: UiEvent = UiEvent.TextoIntroducido("Hola Kotlin")
    val evento3: UiEvent = UiEvent.ItemSeleccionado(123)

    // Función que procesa los eventos
    fun manejarEvento(evento: UiEvent) {
        when (evento) {
            is UiEvent.ClickBotonGuardar -> println("Evento: Botón Guardar clickeado. Iniciando guardado...")
            is UiEvent.TextoIntroducido -> println("Evento: Texto introducido: '${evento.texto}'. Actualizando previsualización...")
            is UiEvent.ScrollAbajo -> println("Evento: Scroll hacia abajo. Cargando más elementos...")
            is UiEvent.ItemSeleccionado -> println("Evento: Ítem con ID ${evento.id} seleccionado. Mostrando detalles...")
        }
    }

    println("Simulando eventos de interfaz de usuario...")
    manejarEvento(evento1)
    manejarEvento(evento2)
    manejarEvento(UiEvent.ScrollAbajo)
    manejarEvento(evento3)

    // Un ejemplo de cómo Compose usaría esto:
    // @Composable
    // fun MyInputScreen(onEvent: (UiEvent) -> Unit) {
    //     var text by remember { mutableStateOf("") }
    //
    //     TextField(
    //         value = text,
    //         onValueChange = {
    //             text = it
    //             onEvent(UiEvent.TextoIntroducido(it)) // Envía el evento al ViewModel/manager
    //         },
    //         label = { Text("Introduce texto") }
    //     )
    //     Button(onClick = { onEvent(UiEvent.ClickBotonGuardar) }) { // Envía el evento
    //         Text("Guardar")
    //     }
    // }
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 3: Resultado de Operación (Sealed Class y Generics)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Las `sealed classes` también pueden ser genéricas, lo que las hace increíblemente versátiles
 * para representar resultados de operaciones que pueden devolver diferentes tipos de datos.
 *
 * **Utilidad en Compose:** Cuando realizas operaciones asíncronas (como llamadas a API),
 * el resultado puede ser un éxito con datos o un error. Una `sealed class` genérica
 * `Result<T>` te permite tipificar el resultado esperado y manejar los errores de manera uniforme.
 *
 * **Explicación:** `Result<T>` puede envolver cualquier tipo `T` para el caso de éxito,
 * o un `String` para el mensaje de error. Esto proporciona seguridad de tipo y claridad.
 */
sealed class Result<out T> { // `out` hace que T sea covariante, útil en colecciones
    data class Success<out T>(val data: T) : Result<T>() // Resultado exitoso con datos de tipo T
    data class Error(val message: String) : Result<Nothing>() // Resultado de error con un mensaje
    object Loading : Result<Nothing>() // Estado de carga, sin datos
}

// Clase de ejemplo para simular datos
data class User(val id: Int, val name: String)

fun ejercicio3ResultadoOperacion() {
    // Simulamos una función que podría devolver un Result
    fun fetchData(shouldSucceed: Boolean): Result<List<User>> {
        return when (shouldSucceed) {
            true -> Result.Success(listOf(User(1, "Alice"), User(2, "Bob")))
            false -> Result.Error("No se pudieron cargar los usuarios.")
        }
    }

    // Procesamos los resultados
    fun procesarResultado(resultado: Result<List<User>>) {
        when (resultado) {
            is Result.Loading -> println("Cargando usuarios...")
            is Result.Success -> {
                println("Usuarios cargados exitosamente:")
                resultado.data.forEach { user -> println("- ${user.name} (ID: ${user.id})") }
            }
            is Result.Error -> println("Error al cargar usuarios: ${resultado.message}")
        }
    }

    println("Intentando cargar datos con éxito:")
    procesarResultado(Result.Loading)
    val exito = fetchData(true)
    procesarResultado(exito)

    println("\nIntentando cargar datos con error:")
    procesarResultado(Result.Loading)
    val error = fetchData(false)
    procesarResultado(error)

    // Un ejemplo de cómo Compose usaría esto:
    // @Composable
    // fun UserListScreen(viewModel: UserListViewModel) {
    //     val usersResult by viewModel.users.collectAsState() // Observa el LiveData/Flow
    //
    //     when (usersResult) {
    //         is Result.Loading -> CircularProgressIndicator()
    //         is Result.Success -> LazyColumn {
    //             items((usersResult as Result.Success).data) { user ->
    //                 Text(text = user.name)
    //             }
    //         }
    //         is Result.Error -> Text(text = (usersResult as Result.Error).message, color = Color.Red)
    //     }
    // }
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 4: Navegación en Compose (Sealed Class para Rutas)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** Las `sealed classes` son perfectas para definir las rutas o destinos de navegación
 * en aplicaciones Compose con `NavController`.
 *
 * **Utilidad en Compose:** Al usar una `sealed class` para representar todas las pantallas posibles
 * de tu aplicación, obtienes seguridad de tipo y claridad. Cada objeto o clase de datos dentro
 * de la `sealed class` puede representar una ruta específica, y si la ruta necesita argumentos,
 * la `data class` los encapsula.
 *
 * **Explicación:** `Screen` define los diferentes destinos de la aplicación. `Home` y `Settings`
 * son `object` porque no necesitan argumentos. `Detail` es una `data class` porque necesita
 * el `itemId` para saber qué detalle mostrar.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    data class Detail(val itemId: Int) : Screen("detail/{itemId}") {
        // Función para construir la ruta real con el argumento
        fun createRoute(itemId: Int) = "detail/$itemId"
    }
    // Podemos añadir más pantallas si es necesario, el compilador nos lo recordará en el 'when'
    // object Profile : Screen("profile")
}

fun ejercicio4NavegacionCompose() {
    // Simulamos la navegación a diferentes pantallas
    fun navegarA(pantalla: Screen) {
        when (pantalla) {
            is Screen.Home -> println("Navegando a la pantalla de Inicio: ${pantalla.route}")
            is Screen.Settings -> println("Navegando a la pantalla de Ajustes: ${pantalla.route}")
            is Screen.Detail -> println("Navegando a la pantalla de Detalle para el ítem ID: ${pantalla.itemId}. Ruta real: ${pantalla.createRoute(pantalla.itemId)}")
        }
    }

    println("Simulando navegación entre pantallas en Compose:")
    navegarA(Screen.Home)
    navegarA(Screen.Settings)
    navegarA(Screen.Detail(itemId = 42)) // Navegando al detalle de un ítem
    navegarA(Screen.Detail(itemId = 101))

    // Un ejemplo de cómo Compose Navigation usaría esto:
    // @Composable
    // fun MyApp() {
    //     val navController = rememberNavController()
    //     NavHost(navController = navController, startDestination = Screen.Home.route) {
    //         composable(Screen.Home.route) { HomeScreen(navController) }
    //         composable(Screen.Settings.route) { SettingsScreen(navController) }
    //         composable(Screen.Detail.route) { backStackEntry ->
    //             val itemId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull() ?: return@composable
    //             DetailScreen(navController, itemId)
    //         }
    //     }
    // }
    //
    // @Composable
    // fun HomeScreen(navController: NavController) {
    //     Button(onClick = { navController.navigate(Screen.Detail(123).createRoute(123)) }) {
    //         Text("Ir al detalle 123")
    //     }
    // }
}

// ----------------------------------------------------------------------------------------------------
// EJERCICIO 5: Sealed Interface (Kotlin 1.5+)
// ----------------------------------------------------------------------------------------------------

/**
 * **Concepto:** A partir de Kotlin 1.5, podemos tener `sealed interfaces`. Son muy similares a las `sealed classes`
 * en su propósito (restringir la jerarquía), pero con la ventaja de que una clase puede implementar múltiples
 * `sealed interfaces`, lo cual no es posible con `sealed classes` (una clase solo puede heredar de una superclase).
 *
 * **Utilidad en Compose:** Las `sealed interfaces` son útiles cuando quieres definir un conjunto restringido
 * de comportamientos o tipos que pueden ser implementados por clases que también necesitan heredar de otras
 * jerarquías. Por ejemplo, si tienes diferentes tipos de elementos de UI que también deben implementar
 * un `SealedEvent` o un `SealedState`.
 *
 * **Explicación:** `Action` es una `sealed interface`. `LoginAction` y `LogoutAction` son dos clases
 * que implementan esta interfaz. El `when` sigue siendo exhaustivo.
 */
sealed interface Action {
    fun execute() // Una interfaz puede tener métodos abstractos
}

data class LoginAction(val username: String) : Action {
    override fun execute() {
        println("Ejecutando acción de login para usuario: $username")
        // Aquí iría la lógica real de login
    }
}

object LogoutAction : Action {
    override fun execute() {
        println("Ejecutando acción de logout.")
        // Aquí iría la lógica real de logout
    }
}

fun ejercicio5SealedInterface() {
    val accion1: Action = LoginAction("juanito")
    val accion2: Action = LogoutAction

    fun procesarAccion(action: Action) {
        when (action) {
            is LoginAction -> action.execute()
            is LogoutAction -> action.execute()
        }
    }

    println("Simulando ejecución de acciones con Sealed Interface:")
    procesarAccion(accion1)
    procesarAccion(accion2)

    // Un ejemplo de cuándo podría ser útil:
    // interface ListItem // Una clase que representa un ítem en una lista
    // sealed interface UserInteraction // Los posibles eventos/interacciones de usuario
    //
    // data class ClickInteraction(val item: ListItem) : UserInteraction
    // data class SwipeInteraction(val direction: SwipeDirection) : UserInteraction
    //
    // class MyComposableItem : ListItem, Clickable, LongClickable { // MyComposableItem es una ListItem
    //    // ...
    // }
    //
    // En un ViewModel, podrías recibir y procesar las UserInteraction.
}