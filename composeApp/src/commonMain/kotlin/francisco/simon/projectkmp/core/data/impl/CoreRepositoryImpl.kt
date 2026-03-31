package francisco.simon.projectkmp.core.data.impl

import francisco.simon.projectkmp.core.data.dto.CourseResponseDto
import francisco.simon.projectkmp.core.data.dto.toDomain
import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.repository.CoreRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CoreRepositoryImpl(private val httpClient: HttpClient) : CoreRepository {

    override suspend fun getCourses(listInt: List<Int>): List<Course> {
        return withContext(Dispatchers.IO) {
            val httpResponse = httpClient.get(
                urlString = "https://stepik.org/api/courses"
            ) {
                listInt.forEach { courseId ->
                    parameter(key = "ids[]", value = courseId)
                }
            }
            httpResponse.body<CourseResponseDto>().toDomain()
        }
    }

    override suspend fun getCourse(courseId: Int): Course {
        return withContext(Dispatchers.IO) {
            val httpResponse = httpClient.get(
                urlString = "https://stepik.org/api/courses/$courseId"
            )
            httpResponse.body<CourseResponseDto>().courses.first().toDomain()
        }
    }
}
