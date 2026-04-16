package francisco.simon.projectkmp.features.common.tab

import org.jetbrains.compose.resources.StringResource
import projectkmp.composeapp.generated.resources.Res
import projectkmp.composeapp.generated.resources.information_course_detail_tab_label
import projectkmp.composeapp.generated.resources.review_course_detail_tab_label
import projectkmp.composeapp.generated.resources.units_course_detail_tab_label

internal data class CourseDetailTab(
    val route: Any,
    val labelRes: StringResource,
)

internal val courseDetailTabs = listOf<CourseDetailTab>(
    CourseDetailTab(
        route = CourseDetailTabGraph.Information,
        labelRes = Res.string.information_course_detail_tab_label
    ),
    CourseDetailTab(
        route = CourseDetailTabGraph.Review,
        labelRes = Res.string.review_course_detail_tab_label
    ),
    CourseDetailTab(
        route = CourseDetailTabGraph.Units,
        labelRes = Res.string.units_course_detail_tab_label
    )
)
