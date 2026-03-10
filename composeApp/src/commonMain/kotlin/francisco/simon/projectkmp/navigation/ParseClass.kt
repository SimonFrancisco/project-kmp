package francisco.simon.projectkmp.navigation

import kotlin.reflect.KClass

expect fun tryParseClass(className:String): KClass<*>?
