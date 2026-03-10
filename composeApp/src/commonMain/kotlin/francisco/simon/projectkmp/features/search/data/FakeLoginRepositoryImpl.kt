package francisco.simon.projectkmp.features.search.data

import francisco.simon.projectkmp.features.search.domain.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FakeLoginRepositoryImpl : LoginRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit> {
        return withContext(Dispatchers.IO) {
            if (validate(username, password)) {
                Result.success(Unit)
            } else {
                Result.failure(IllegalStateException())
            }
        }
    }

    private fun validate(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}
