package com.bongpal.yatzee.feature.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.ui.component.TierIcon
import com.bongpal.yatzee.core.ui.model.toUiModel

@Composable
internal fun ResultRoute(
    navigateToHome: () -> Unit,
    navigateToScoreBoard: (Int) -> Unit,
    paddingValues: PaddingValues,
    viewModel: ResultViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResultScreen(
        navigateToHome = navigateToHome,
        navigateToScoreBoard = navigateToScoreBoard,
        paddingValues = paddingValues,
        finalScore = uiState.finalScore
    )
}

@Composable
private fun ResultScreen(
    navigateToHome: () -> Unit = {},
    navigateToScoreBoard: (Int) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
    finalScore: Int = 120,
) {
    val tier = finalScore.toUiModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(vertical = 48.dp),
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

        TierIcon(
            score = finalScore,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(vertical = 12.dp)
        )

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
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(
                onClick = navigateToHome
            ) {
                Text(
                    text = "메인으로",
                    style = Typography.headlineMedium
                )
            }

            TextButton(
                onClick = { navigateToScoreBoard(finalScore) }
            ) {
                Text(
                    text = "스코어 보드",
                    style = Typography.headlineMedium
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ResultScreenPreview() {
    ResultScreen()
}