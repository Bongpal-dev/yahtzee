package com.bongpal.yatzee.feature.play.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.LightGray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import com.bongpal.yatzee.feature.play.util.getScoreImage

@Composable
internal fun ScoreButton(
    scoreUiModel: ScoreUiModel,
    defaultImage: Bitmap,
    selectable: Boolean = false,
    selectScore: (ScoreUiModel) -> Unit,
    pickScore: (ScoreUiModel) -> Unit,
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
        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        if (selectable && scoreUiModel.isSelected.not()) selectScore(scoreUiModel)
                        if (scoreUiModel.isPicked.not() && scoreUiModel.isSelected) pickScore(
                            scoreUiModel
                        )
                    },
                )
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

