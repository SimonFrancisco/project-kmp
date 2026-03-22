package francisco.simon.projectkmp.auth.data

interface AuthRepository {
    suspend fun exchangeCodeForToken(code: String): Result<StepikToken>
}
