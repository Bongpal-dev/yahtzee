package com.bongpal.yatzee.feature.play

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.yatzee.core.designsystem.component.ImageButton
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.DefaultBlack
import com.bongpal.yatzee.core.designsystem.theme.LightGray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.model.LOWER
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.UPPER
import com.bongpal.yatzee.feature.play.component.DiceSection
import com.bongpal.yatzee.feature.play.component.ScoreButton
import com.bongpal.yatzee.feature.play.model.Dice
import com.bongpal.yatzee.feature.play.model.ScoreUiModel

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
        scoreImages = uiState.scoreInitialImages,
        rollingState = uiState.isRolling,
        rollCount = uiState.rollCount,
        upperScoreUiModels = uiState.scoreUiModels.filter { it.category.section == UPPER },
        lowerScoreUiModels = uiState.scoreUiModels.filter { it.category.section == LOWER },
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
    dices: List<Dice> = List(5) { Dice() },
    scoreImages: Map<ScoreCategory, Bitmap> = emptyMap(),
    rollingState: Boolean = false,
    rollCount: Int = 0,
    upperScoreUiModels: List<ScoreUiModel> = ScoreCategory.entries.map {
        ScoreUiModel(
            category = it,
            point = 26
        )
    }
        .filter { it.isUpper() },
    lowerScoreUiModels: List<ScoreUiModel> = ScoreCategory.entries.map {
        ScoreUiModel(
            category = it,
            point = 26
        )
    }
        .filter { it.isUpper().not() },
    upperScore: Int = 0,
    finalScore: Int = 0,
    rollDice: () -> Unit = {},
    holdDice: (Int) -> Unit = {},
    selectScore: (ScoreUiModel) -> Unit = {},
    pickScore: (ScoreUiModel) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 12.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "상단 점수",
                    style = Typography.titleLarge,
                    color = Color.White,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 4.dp, top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                upperScoreUiModels.chunked(3).forEach { uppers ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uppers.forEach { score ->
                            ScoreButton(
                                scoreUiModel = score,
                                defaultImage = scoreImages.getValue(score.category),
                                selectScore = selectScore,
                                pickScore = pickScore,
                                selectable = dices.isNotEmpty(),
                                rollingState = rollingState,
                                modifier = Modifier
                                    .height(40.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
            ) {
                Text(
                    text = "상단 보너스",
                    style = Typography.labelSmall,
                    color = LightGray
                )

                Text(
                    text = "$upperScore / 63",
                    style = Typography.labelSmall,
                    color = LightGray
                )

                Text(
                    text = if (upperScore >= 63) "+35" else "+0",
                    style = Typography.headlineMedium,
                    color = if (upperScore >= 63) ActivePink else DefaultBlack
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "하단 점수",
                    style = Typography.titleLarge,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                lowerScoreUiModels.chunked(2).forEach { lower ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        lower.forEach { score ->
                            ScoreButton(
                                scoreUiModel = score,
                                defaultImage = scoreImages.getValue(score.category),
                                selectScore = selectScore,
                                pickScore = pickScore,
                                selectable = dices.isNotEmpty(),
                                rollingState = rollingState,
                                modifier = Modifier
                                    .height(40.dp)
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
                    text = "$finalScore 점",
                    color = DefaultBlack,
                    style = Typography.headlineMedium,
                )
            }
        }


        DiceSection(
            dices = dices,
            isRolling = rollingState,
            holdDice = holdDice,
        )

        ImageButton(
            imageVector = ImageVector.vectorResource(R.drawable.img_roll_button_enable),
            pressedImage = ImageVector.vectorResource(R.drawable.img_roll_button_pressed_enable),
            disabledImage = ImageVector.vectorResource(R.drawable.img_roll_button_disable),
            disabledPressedImage = ImageVector.vectorResource(R.drawable.img_roll_button_pressed_disable),
            enabled = rollCount < 3,
            onClick = {
                if (dices.any { it.isHeld.not() } || dices.isEmpty()) rollDice()
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PlayScreenPreview() {
    PlayScreen()
}
