package francisco.simon.projectkmp.features.login.domain

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Unit>
}
