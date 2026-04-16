package francisco.simon.projectkmp.core.domain.repository

import francisco.simon.projectkmp.core.domain.entity.Course
import francisco.simon.projectkmp.core.domain.entity.Review
import francisco.simon.projectkmp.core.domain.entity.UserCore

interface CoreRepository {
    suspend fun getCourses(listInt: List<Int>): List<Course>
    suspend fun getCourse(courseId: Int): Course
    suspend fun getReviews(courseId: Int): List<Review>
    suspend fun getUser(userId: Long): UserCore
}
