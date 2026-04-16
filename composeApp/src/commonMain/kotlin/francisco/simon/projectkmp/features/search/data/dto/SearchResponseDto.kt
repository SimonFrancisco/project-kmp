package francisco.simon.projectkmp.features.search.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("meta")
    val metaDto: MetaDto,
    @SerialName("search-results")
    val courses: List<SearchCourseDto>
)

fun SearchResponseDto.toListId(): List<Int> {
    return courses.mapNotNull {
        it.id
    }
}

