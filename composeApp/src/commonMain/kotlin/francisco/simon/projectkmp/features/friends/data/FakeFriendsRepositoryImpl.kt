package francisco.simon.projectkmp.features.friends.data

import francisco.simon.projectkmp.features.friends.domain.entity.Friend
import francisco.simon.projectkmp.features.friends.domain.repository.FriendsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeFriendsRepositoryImpl : FriendsRepository {

    companion object {
        private const val MIMIC_DELAY = 2000L
    }

    override suspend fun getFriends(): List<Friend> {
        return withContext(Dispatchers.IO) {
            delay(MIMIC_DELAY)
            friends
        }
    }

    private val friends = listOf(
        Friend(1, "Alex", "Johnson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(2, "Maria", "Smith", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(3, "Daniel", "Brown", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(4, "Sophia", "Taylor", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(5, "Michael", "Anderson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(6, "Emma", "Thomas", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(7, "James", "Jackson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(8, "Olivia", "White", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(9, "William", "Harris", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(10, "Ava", "Martin", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),

        Friend(11, "Benjamin", "Thompson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(12, "Isabella", "Garcia", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(13, "Lucas", "Martinez", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(14, "Mia", "Robinson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(15, "Henry", "Clark", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(16, "Charlotte", "Rodriguez", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(17, "Alexander", "Lewis", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(18, "Amelia", "Lee", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(19, "Ethan", "Walker", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(20, "Harper", "Hall", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),

        Friend(21, "Noah", "Allen", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(22, "Evelyn", "Young", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(23, "Logan", "King", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(24, "Abigail", "Wright", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(25, "Jacob", "Scott", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(26, "Emily", "Green", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(27, "Matthew", "Baker", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(28, "Ella", "Adams", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(29, "David", "Nelson", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(30, "Grace", "Carter", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),

        Friend(31, "Joseph", "Mitchell", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(32, "Lily", "Perez", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(33, "Samuel", "Roberts", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(34, "Chloe", "Turner", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(35, "Andrew", "Phillips", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(36, "Zoe", "Campbell", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(37, "Ryan", "Parker", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(38, "Hannah", "Evans", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(39, "Nathan", "Edwards", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(40, "Victoria", "Collins", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),

        Friend(41, "Christopher", "Stewart", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(42, "Aria", "Sanchez", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(43, "Jonathan", "Morris", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(44, "Scarlett", "Rogers", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(45, "Christian", "Reed", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(46, "Penelope", "Cook", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(47, "Aaron", "Morgan", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(48, "Nora", "Bell", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(49, "Charles", "Murphy", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13"),
        Friend(50, "Lucy", "Bailey", "https://avatars.mds.yandex.net/i?id=2300c5d4ce60491b986778e05ed11179_l-4835259-images-thumbs&n=13")
    )
}
