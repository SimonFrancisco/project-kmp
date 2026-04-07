package francisco.simon.projectkmp.features.auth.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph {

    @Serializable
    data object AuthRoute

    @Serializable
    data object WebViewAuthRoute
}
