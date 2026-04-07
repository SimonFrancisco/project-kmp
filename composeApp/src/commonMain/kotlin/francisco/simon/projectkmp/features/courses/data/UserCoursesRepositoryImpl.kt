package francisco.simon.projectkmp.features.courses.data

import francisco.simon.projectkmp.features.courses.data.dto.UserResponseDto
import francisco.simon.projectkmp.features.courses.data.dto.toListId
import francisco.simon.projectkmp.features.courses.domain.repository.UserCoursesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry

@OptIn(ExperimentalCoroutinesApi::class)
class UserCoursesRepositoryImpl(
    private val httpClient: HttpClient
) : UserCoursesRepository {

    private companion object {
        const val RETRY_TIMES = 3L
    }

    override fun getCoursesId(): Flow<List<Int>> = flow {
        val response = httpClient.get(
            urlString = "https://stepik.org/api/user-courses"
        ).body<UserResponseDto>()

        emit(response.toListId())
    }
        .retry(RETRY_TIMES)
        .flowOn(Dispatchers.IO)
}
