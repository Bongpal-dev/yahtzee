package com.bongpal.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.bongpal.designsystem.component.AutoResizeImage
import com.bongpal.designsystem.theme.Typography
import com.bongpal.yatzee.feature.home.R

@Composable
internal fun HomeRoute(
    navigateToPlay: () -> Unit,
    paddingValues: PaddingValues,
) {
    HomeScreen(
        navigateToPlay = navigateToPlay,
        paddingValues = paddingValues
    )
}

@Composable
private fun HomeScreen(
    navigateToPlay: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
    ) {
        AutoResizeImage(
            bitmap = ImageBitmap.imageResource(R.drawable.img_title),
            modifier = Modifier.fillMaxWidth(0.65f),
        )

        Spacer(modifier = Modifier.size(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(
                onClick = navigateToPlay
            ) {
                Text(
                    text = "게임 시작",
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "게임 설명",
                    style = Typography.headlineMedium
                )
            }

            TextButton(
                onClick = {}
            ) {
                Text(
                    text = "스코어 보드",
                    style = Typography.headlineMedium
                )
            }
        }

    }
}