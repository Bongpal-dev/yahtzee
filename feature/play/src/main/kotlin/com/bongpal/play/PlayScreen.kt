package com.bongpal.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.designsystem.component.ImageButton
import com.bongpal.designsystem.theme.Typography
import com.bongpal.play.component.DiceSection
import com.bongpal.play.component.ScoreButton
import com.bongpal.play.model.Dice
import com.bongpal.play.model.LOWER
import com.bongpal.play.model.Score
import com.bongpal.play.model.UPPER
import com.bongpal.yatzee.feature.play.R

@Composable
internal fun PlayRoute(
    navigateToResult: (Int) -> Unit,
    paddingValues: PaddingValues,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isEnd) {
        if (uiState.isEnd) {
            navigateToResult(uiState.finalScore)
        }
    }

    PlayScreen(
        dices = uiState.dices,
        rollingState = uiState.isRolling,
        rollCount = uiState.rollCount,
        upperScores = uiState.scores.filter { it.category.section == UPPER },
        lowerScores = uiState.scores.filter { it.category.section == LOWER },
        upperScore = uiState.upperSectionScore,
        finalScore = uiState.finalScore,
        rollDice = viewModel::rollDice,
        holdDice = viewModel::holdDice,
        selectScore = viewModel::selectScore,
        pickScore = viewModel::pickScore,
        paddingValues = paddingValues
    )
}

@Composable
private fun PlayScreen(
    dices: List<Dice> = emptyList(),
    rollingState: Boolean = false,
    rollCount: Int = 0,
    upperScores: List<Score> = emptyList(),
    lowerScores: List<Score> = emptyList(),
    upperScore: Int = 0,
    finalScore: Int = 0,
    rollDice: () -> Unit = {},
    holdDice: (Int) -> Unit = {},
    selectScore: (Score) -> Unit = {},
    pickScore: (Score) -> Unit = {},
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "스코어",
            style = Typography.titleLarge,
            modifier = Modifier.align(Alignment.Start),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.14f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            upperScores.chunked(3).forEach { uppers ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uppers.forEach { score ->
                        ScoreButton(
                            score = score,
                            selectScore = selectScore,
                            pickScore = pickScore,
                            selectable = dices.isNotEmpty(),
                            rollingState = rollingState,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "보너스",
                style = MaterialTheme.typography.labelSmall,
            )

            Text(
                text = "$upperScore / 63",
                color = Color.Gray,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "+35",
                style = MaterialTheme.typography.headlineMedium,
                color = if (upperScore >= 63) Color.Red else Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            lowerScores.chunked(2).forEach { lower ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    lower.forEach { score ->
                        ScoreButton(
                            score = score,
                            selectScore = selectScore,
                            pickScore = pickScore,
                            selectable = dices.isNotEmpty(),
                            rollingState = rollingState,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }

                    if (lower.size < 2) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.12f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
        ) {
            Text(
                text = "최종 스코어",
                style = MaterialTheme.typography.labelSmall,
            )

            Text(
                text = "$finalScore 점",
                color = Color.Gray,
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        DiceSection(
            dices = dices,
            isRolling = rollingState,
            holdDice = holdDice,
            modifier = Modifier.fillMaxHeight(0.3f)
        )

        Spacer(modifier = Modifier.size(20.dp))

        ImageButton(
            bitmap = ImageBitmap.imageResource(R.drawable.btn_roll_default),
            pressedBitmap = ImageBitmap.imageResource(R.drawable.btn_roll_pressed),
            disabledBitmap = ImageBitmap.imageResource(R.drawable.btn_roll_default_disable),
            disabledPressedBitmap = ImageBitmap.imageResource(R.drawable.btn_roll_pressed_disable),
            enabled = rollCount < 3,
            modifier = Modifier.weight(1f),
            onClick = {
                if (dices.any { it.isHeld.not() } || dices.isEmpty()) rollDice()
            }
        )
    }
}

