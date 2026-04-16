package francisco.simon.projectkmp.features.profile.domain.entity

data class User(
    val id: Long,
    val fullName: String,
    val image: String,
    val bio: String,
    val registrationDate: String
)
