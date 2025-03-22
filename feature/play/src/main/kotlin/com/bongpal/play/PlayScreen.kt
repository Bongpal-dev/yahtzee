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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.designsystem.theme.Typography
import com.bongpal.play.component.DiceSection
import com.bongpal.play.component.ScoreButton
import com.bongpal.play.model.Dice
import com.bongpal.play.model.LOWER
import com.bongpal.play.model.UPPER

@Composable
internal fun PlayRoute(
    paddingValues: PaddingValues,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val diceState = uiState.dices
    val rollingState = uiState.isRolling
    val upperScores = uiState.scores.filter { it.category.section == UPPER }
    val lowerScores = uiState.scores.filter { it.category.section == LOWER }
    val upperScore = uiState.upperScore
    val finalScore = uiState.finalScore

    PlayScreen(
        dices = diceState,
        rollingState = rollingState,
        upperScores = upperScores,
        lowerScores = lowerScores,
        upperScore = upperScore,
        finalScore = finalScore,
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
            .padding(top = 40.dp, start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
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
                modifier = Modifier.weight(1f),
            )
        }

        DiceSection(
            dices = dices,
            isRolling = rollingState,
            holdDice = holdDice,
            modifier = Modifier.fillMaxHeight(0.3f)
        )

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    if (dices.any { it.isHeld.not() } || dices.isEmpty()) rollDice()
                }
            ) {
                Text(
                    text = "Roll",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }

            TextButton(
                onClick = {
                    (lowerScores + upperScores).let { scores ->
                        if (scores.any { it.isSelected }) {
                            pickScore(scores.find { it.isSelected } ?: return@let)
                        }
                    }
                }
            ) {
                Text(
                    text = "Pick",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}

