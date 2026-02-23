package francisco.simon.projectkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform