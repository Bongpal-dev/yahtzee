package com.bongpal.yatzee.feature.scoreboard.scoredetail.component

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.DefaultBlack
import com.bongpal.yatzee.core.designsystem.theme.Gray
import com.bongpal.yatzee.core.designsystem.theme.LightGray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.isLower
import com.bongpal.yatzee.core.model.isUpper
import com.bongpal.yatzee.core.ui.component.ScoreContent
import com.bongpal.yatzee.core.ui.component.ScoreSectionHeader
import com.bongpal.yatzee.core.ui.model.ImageResource

@Composable
internal fun ScoreDetailContent(
    gameRecords: GameRecord? = null,
    scoreImages: Map<ScoreCategory, Bitmap> = emptyMap(),
    onChangePage: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        ScoreSection(
            title = "상단 점수",
            scores = gameRecords?.scores?.filter { it.category.isUpper() } ?: emptyList(),
            chunkSize = 3,
            scoreImages = scoreImages,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        UpperSectionBonus(
            gameRecords = gameRecords,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        ScoreSection(
            title = "하단 점수",
            scores = gameRecords?.scores?.filter { it.category.isLower() } ?: emptyList(),
            chunkSize = 2,
            scoreImages = scoreImages,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        FinalScore(gameRecords = gameRecords)

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onChangePage,
                    interactionSource = null,
                    indication = null
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "← 티어 확인",
                style = Typography.headlineMedium,
                color = Gray
            )
        }
    }
}

// ============= Internal Components =============

@Composable
private fun ScoreSection(
    title: String,
    scores: List<com.bongpal.yatzee.core.model.Score>,
    chunkSize: Int,
    scoreImages: Map<ScoreCategory, Bitmap>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        ScoreSectionHeader(title = title, modifier = Modifier.fillMaxWidth())

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
                            point = score.point,
                            color = DefaultBlack,
                            image = scoreImages[score.category]?.let { ImageResource.Bitmap(it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    fillEmptyCells(chunk.size, chunkSize)
                }
            }
        }
    }
}

@Composable
private fun RowScope.fillEmptyCells(currentSize: Int, chunkSize: Int) {
    repeat(chunkSize - currentSize) {
        Box(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun UpperSectionBonus(
    gameRecords: GameRecord? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
    ) {
        val upperBonus =
            gameRecords?.scores?.filter { it.category.isUpper() }?.sumOf { it.point } ?: 0
        Text(
            text = "상단 보너스",
            style = Typography.labelSmall,
            color = LightGray
        )

        Text(
            text = "$upperBonus / 63",
            style = Typography.labelSmall,
            color = LightGray
        )

        Text(
            text = if (upperBonus >= 63) "+35" else "+0",
            style = Typography.headlineMedium,
            color = DefaultBlack
        )
    }
}

@Composable
private fun FinalScore(
    gameRecords: GameRecord?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
    ) {
        Text(
            text = "최종 스코어",
            style = Typography.labelSmall,
            color = DefaultBlack
        )

        Text(
            text = "${gameRecords?.totalScore} 점",
            color = DefaultBlack,
            style = Typography.headlineMedium,
        )
    }
}