package com.bongpal.play.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bongpal.designsystem.theme.ActivePink
import com.bongpal.designsystem.theme.LightGray
import com.bongpal.designsystem.theme.Typography
import com.bongpal.play.model.Score
import com.bongpal.play.util.getScoreImage

@Composable
internal fun ScoreButton(
    score: Score,
    selectable: Boolean = false,
    selectScore: (Score) -> Unit,
    pickScore: (Score) -> Unit,
    rollingState: Boolean,
    modifier: Modifier = Modifier,
) {
    val pointColor = when {
        score.isPicked -> Color.Black
        score.isSelected -> ActivePink
        else -> LightGray
    }
    val point = when {
        score.isPicked -> score.point.toString()
        score.isSelected -> score.point.toString()
        score.point < 1 -> ""
        else -> score.point.toString()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            imageVector = score.getScoreImage(),
            contentDescription = null,
            modifier = Modifier
                .weight(1f, fill = false)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        if (selectable && score.isSelected.not()) selectScore(score)
                        if (score.isPicked.not() && score.isSelected) pickScore(score)
                    },
                )
        )

        Box {
            Text(
                text = "001",
                style = Typography.headlineMedium,
                color = Color.Transparent,
            )
            Text(
                text = if (rollingState.not() || score.isPicked) point else "",
                style = Typography.headlineMedium,
                color = pointColor,
            )
        }
    }
}

