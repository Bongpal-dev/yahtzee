package com.bongpal.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.designsystem.theme.Typography

@Composable
internal fun ResultRoute(
    navigateToHome: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: ResultViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResultScreen(
        navigateToHome = navigateToHome,
        paddingValues = paddingValues,
        finalScore = uiState.finalScore
    )
}

@Composable
private fun ResultScreen(
    navigateToHome: () -> Unit = {},
    finalScore: Int = 0,
    paddingValues: PaddingValues = PaddingValues()
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Your final score is $finalScore"
        )

        TextButton(
            onClick = navigateToHome
        ) {
            Text(
                text = "메인으로",
                style = Typography.headlineMedium
            )
        }
    }
}