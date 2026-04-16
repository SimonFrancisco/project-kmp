package francisco.simon.projectkmp.features.courses.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserCoursesRepository {
    fun getCoursesId(): Flow<List<Int>>
}
