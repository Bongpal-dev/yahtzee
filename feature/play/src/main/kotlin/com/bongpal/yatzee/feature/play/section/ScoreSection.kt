package com.bongpal.yatzee.feature.play.section

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.ui.component.ScoreContent
import com.bongpal.yatzee.core.ui.component.ScoreSectionHeader
import com.bongpal.yatzee.feature.play.model.ScoreUiModel
import com.bongpal.yatzee.feature.play.model.getDisplayImage
import com.bongpal.yatzee.feature.play.model.getScoreColor
import com.bongpal.yatzee.feature.play.model.getScorePoint
import com.bongpal.yatzee.feature.play.util.scoreInputHandler

@Composable
internal fun ScoreSection(
    title: String,
    scores: List<ScoreUiModel>,
    chunkSize: Int,
    scoreImages: Map<ScoreCategory, Bitmap>,
    onClick: (ScoreCategory) -> Unit,
    showPopup: (ScoreUiModel, Offset) -> Unit,
    hidePopup: () -> Unit,
    isRolling: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ScoreSectionHeader(
            title = title,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            scores.chunked(chunkSize).forEach { chunk ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    chunk.forEach { score ->
                        ScoreContent(
                            point = score.getScorePoint(isRolling),
                            color = score.getScoreColor(),
                            image = score.getDisplayImage(scoreImages),
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                                .scoreInputHandler(
                                    score = score,
                                    scope = rememberCoroutineScope(),
                                    showPopup = showPopup,
                                    hidePopup = hidePopup,
                                    onClick = onClick
                                )
                        )
                    }

                    repeat(chunkSize - chunk.size) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}