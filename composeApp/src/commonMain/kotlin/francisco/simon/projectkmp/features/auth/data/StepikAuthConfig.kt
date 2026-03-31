package francisco.simon.projectkmp.features.auth.data

import francisco.simon.projectkmp.StepikAppSecrets

object StepikAuthConfig {
    const val REDIRECT_URI: String = "https://francisco.com/redirect"
    private const val AUTHORIZE_URL: String = "https://stepik.org/oauth2/authorize/"
    const val TOKEN_URL: String = "https://stepik.org/oauth2/token/"

    fun buildAuthorizeUrl(): String =
        "${AUTHORIZE_URL}?response_type=code&client_id=${StepikAppSecrets.STEPIK_CLIENT_ID}&redirect_uri=${REDIRECT_URI}"
}
