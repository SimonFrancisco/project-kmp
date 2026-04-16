package francisco.simon.projectkmp.features.profile.screen

interface ProfileScreenIntent {
    data object TryAgain : ProfileScreenIntent
    data object Refresh : ProfileScreenIntent
    data object Logout : ProfileScreenIntent
}
