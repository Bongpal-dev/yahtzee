package com.bongpal.yatzee.feature.play.section

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.feature.play.component.ScoreButton
import com.bongpal.yatzee.feature.play.model.ScoreUiModel

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray, shape = RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                style = Typography.titleLarge,
                color = Color.White,
            )
        }

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
                        ScoreButton(
                            scoreUiModel = score,
                            defaultImage = scoreImages.getValue(score.category),
                            handleScoreClick = onClick,
                            showPopup = { showPopup(score, it) },
                            hidePopup = hidePopup,
                            rollingState = isRolling,
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
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