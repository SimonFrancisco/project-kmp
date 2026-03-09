package francisco.simon.projectkmp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun <T> EventConsumer(flow: SharedFlow<T>, block: (T) -> Unit) {
    val blockState by rememberUpdatedState(block)
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner){
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
            flow.collect { event->
                blockState(event)
            }
        }
    }
}
