package com.bongpal.play.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bongpal.designsystem.theme.Typography
import com.bongpal.play.Score
import com.bongpal.play.util.getScoreImage

@Composable
internal fun ScoreButton(
    score: Score,
    selectable: Boolean = false,
    selectScore: (Score) -> Unit,
    rollingState: Boolean,
    modifier: Modifier = Modifier,
) {
    val pointColor = when {
        score.isPicked -> Color.Black
        score.isSelected -> Color.Red
        else -> Color.LightGray
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
        AutoResizeImage(
            bitmap = score.category.getScoreImage(),
            modifier = Modifier
                .fillMaxHeight(),
            onClick = { if (selectable) selectScore(score) }
        )

        Text(
            text = if (rollingState.not() || score.isPicked) point else "",
            style = Typography.headlineMedium,
            color = pointColor,
            modifier = Modifier.weight(1f)
        )
    }
}

