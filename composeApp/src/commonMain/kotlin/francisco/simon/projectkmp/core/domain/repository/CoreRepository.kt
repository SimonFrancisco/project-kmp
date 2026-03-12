package francisco.simon.projectkmp.core.domain.repository

import francisco.simon.projectkmp.core.domain.entity.Course

interface CoreRepository {
    suspend fun getCourses(listInt: List<Int>): List<Course>
    suspend fun getCourse(courseId:Int): Course
}
