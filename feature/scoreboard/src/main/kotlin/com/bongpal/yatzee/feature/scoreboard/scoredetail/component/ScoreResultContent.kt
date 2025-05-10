package com.bongpal.yatzee.feature.scoreboard.scoredetail.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.Gray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.ui.model.toUiModel

@Composable
internal fun ScoreResultContent(
    onChangePage: () -> Unit = {},
    tierImage: Bitmap? = null,
    finalScore: Int = 120,
) {
    val tier = finalScore.toUiModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "스코어",
            style = Typography.headlineMedium
        )

        Text(
            text = "$finalScore 점",
            style = Typography.headlineLarge
        )

        tierImage?.let {
            Image(
                bitmap = tierImage.asImageBitmap(),
                contentDescription = "티어 이미지",
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .aspectRatio(1f)
                    .padding(vertical = 12.dp)
            )
        }

        Text(
            text = tier.displayName,
            color = tier.color,
            style = Typography.headlineMedium,
        )

        Text(
            text = tier.description,
            style = Typography.labelSmall,
            textAlign = TextAlign.Center
        )

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
                text = "상세 점수 확인 →",
                style = Typography.headlineMedium,
                color = Gray
            )
        }
    }
}