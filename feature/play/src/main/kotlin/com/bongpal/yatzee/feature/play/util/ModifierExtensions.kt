package com.bongpal.yatzee.feature.play.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun Modifier.scoreInputHandler(
    score: ScoreUiModel,
    scope: CoroutineScope,
    showPopup: (ScoreUiModel, Offset) -> Unit,
    hidePopup: () -> Unit,
    onClick: (ScoreCategory) -> Unit
): Modifier = composed {
    var buttonCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    this
        .onGloballyPositioned { buttonCoordinates = it }
        .pointerInput(Unit) {
            awaitPointerEventScope {
                var isLongPress = false
                var longPressJob: Job? = null

                while (true) {
                    val event = awaitPointerEvent()
                    val localPos = event.changes.first().position
                    val globalOffset = buttonCoordinates?.localToWindow(localPos)

                    when (event.type) {
                        PointerEventType.Press -> {
                            longPressJob = scope.launch {
                                delay(200L)
                                isLongPress = true
                                globalOffset?.let { showPopup(score, it) }
                            }
                        }

                        PointerEventType.Release -> {
                            if (isLongPress) hidePopup() else onClick(score.category)
                            isLongPress = false
                            longPressJob?.cancel()
                        }

                        else -> {}
                    }
                }
            }
        }
}