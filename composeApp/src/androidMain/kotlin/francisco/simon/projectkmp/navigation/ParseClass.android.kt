package francisco.simon.projectkmp.navigation

import kotlin.reflect.KClass

actual fun tryParseClass(className: String): KClass<*>? {
    return runCatching { Class.forName(className).kotlin }.getOrNull()
}
