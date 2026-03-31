package francisco.simon.projectkmp.features.search.data.dto

import francisco.simon.projectkmp.features.search.domain.entity.SearchCourse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("meta")
    val metaDto: MetaDto,
    @SerialName("search-results")
    val courses: List<SearchCourseDto>
)

internal fun SearchResponseDto.toDomain():List<SearchCourse>{
    return courses.map {
        it.toDomain()
    }
}
