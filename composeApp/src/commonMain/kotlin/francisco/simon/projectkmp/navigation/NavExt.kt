package francisco.simon.projectkmp.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlin.reflect.KClass

fun NavDestination.matches(route: KClass<out Any>): Boolean {
    return this.hierarchy.any { it.hasRoute(route) }
}
