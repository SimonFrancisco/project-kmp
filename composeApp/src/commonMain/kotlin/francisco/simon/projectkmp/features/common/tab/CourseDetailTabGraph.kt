package francisco.simon.projectkmp.features.common.tab

import kotlinx.serialization.Serializable

@Serializable
data object CourseDetailTabGraph {

    @Serializable
    data object Information

    @Serializable
    data object Review

    @Serializable
    data object Units
}
