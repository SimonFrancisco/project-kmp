package francisco.simon.projectkmp.features.catalog.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    @SerialName("page")
    val page: Int,
    @SerialName("has_next")
    val next: Boolean,
    @SerialName("has_previous")
    val previous: Boolean
)
