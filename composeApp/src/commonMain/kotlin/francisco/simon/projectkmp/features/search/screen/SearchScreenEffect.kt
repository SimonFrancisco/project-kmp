package francisco.simon.projectkmp.features.search.screen

sealed interface SearchScreenEffect {
    data class NavigateToCourseDetail(val courseId: Int) : SearchScreenEffect
}
