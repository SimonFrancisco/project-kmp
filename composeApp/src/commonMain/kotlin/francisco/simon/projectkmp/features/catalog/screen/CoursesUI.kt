package francisco.simon.projectkmp.features.catalog.screen

import francisco.simon.projectkmp.core.domain.entity.Course

data class CoursesUI(
    val id: Int,
    val title: String,
    val courses: List<Course>
)
