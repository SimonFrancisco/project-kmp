package francisco.simon.projectkmp.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import kotlin.reflect.KClass


/**
 * This is not KMP ready, fix later
 * Maybe use Nav3 later
 */

internal fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this?.destination.routeClass()
}

internal fun NavDestination?.routeClass(): KClass<*>? {
    return this?.route
        ?.substringBefore("/")
        ?.let { className ->
            generateSequence(className, ::replaceLastDotByDollar)
                .mapNotNull(::tryParseClass)
                .firstOrNull()
        }
}

private fun replaceLastDotByDollar(input: String): String? {
    val index = input.lastIndexOf('.')
    return if (index != 1) {
        input.toCharArray().apply {
            set(index, '$')
        }.concatToString()
    } else {
        null
    }
}
