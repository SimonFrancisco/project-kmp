package francisco.simon.projectkmp.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

fun <T> Flow<T>.asResult(): Flow<Result<T>> =
    map<T, Result<T>> { value -> Result.success(value = value) }
        .catch { e ->
            if (e is CancellationException) {
                throw e
            }
            emit(Result.failure(e))
        }
