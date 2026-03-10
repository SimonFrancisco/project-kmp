package francisco.simon.projectkmp.features.search.domain

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<Unit>
}
