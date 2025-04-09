package com.bongpal.yatzee.feature.play.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.LightGray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import com.bongpal.yatzee.feature.play.util.getScoreImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ScoreButton(
    scoreUiModel: ScoreUiModel,
    defaultImage: Bitmap,
    handleScoreClick: (ScoreUiModel) -> Unit = {},
    showPopup: (Offset) -> Unit = {},
    hidePopup: () -> Unit = {},
    rollingState: Boolean,
    modifier: Modifier = Modifier,
) {
    val pointColor = when {
        scoreUiModel.isPicked -> Color.Black
        scoreUiModel.isSelected -> ActivePink
        else -> LightGray
    }
    val point = when {
        scoreUiModel.isPicked -> scoreUiModel.point.toString()
        scoreUiModel.isSelected -> scoreUiModel.point.toString()
        scoreUiModel.point < 1 -> ""
        else -> scoreUiModel.point.toString()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val scope = rememberCoroutineScope()
        var buttonCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .onGloballyPositioned { coordinates ->
                    buttonCoordinates = coordinates
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        var isLongPress = false
                        var longPressJob: Job? = null

                        while (true) {
                            val event = awaitPointerEvent()
                            val localPos = event.changes.first().position
                            val globalOffset = buttonCoordinates?.localToWindow(localPos)

                            if (event.type == PointerEventType.Press) {
                                longPressJob = scope.launch {
                                    delay(200L)
                                    isLongPress = true
                                    globalOffset?.let { showPopup(it) }  // 전역 위치 기준
                                }
                            }

                            if (event.type == PointerEventType.Release) {
                                if (isLongPress) {
                                    isLongPress = false
                                    hidePopup()
                                } else {
                                    handleScoreClick(scoreUiModel)
                                }
                                longPressJob?.cancel()
                                longPressJob = null
                            }
                        }
                    }
                }
        ) {
            if (scoreUiModel.isPicked.not() && scoreUiModel.isSelected.not()) {
                Image(
                    bitmap = defaultImage.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    imageVector = scoreUiModel.getScoreImage(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Box {
            Text(
                text = "001",
                style = Typography.headlineMedium,
                color = Color.Transparent,
            )
            Text(
                text = if (rollingState.not() || scoreUiModel.isPicked) point else "",
                style = Typography.headlineMedium,
                color = pointColor,
            )
        }
    }
}

