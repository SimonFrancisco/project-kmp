package francisco.simon.projectkmp.auth

object StepikAuthConfig {
    const val CLIENT_ID: String = "client_id"
    const val CLIENT_SECRET: String = "secret"
    const val REDIRECT_URI: String = "https://francisco.com/redirect"
    private const val AUTHORIZE_URL: String = "https://stepik.org/oauth2/authorize/"
    const val TOKEN_URL: String = "https://stepik.org/oauth2/token/"

    fun buildAuthorizeUrl(): String =
        "${AUTHORIZE_URL}?response_type=code&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}"
}
