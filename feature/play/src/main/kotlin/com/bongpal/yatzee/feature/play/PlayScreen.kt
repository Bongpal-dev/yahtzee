package com.bongpal.yatzee.feature.play

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.resource.R.drawable
import com.bongpal.yatzee.feature.play.component.ScoreInfoPopup
import com.bongpal.yatzee.feature.play.model.PlayIntent
import com.bongpal.yatzee.feature.play.section.DiceSection
import com.bongpal.yatzee.feature.play.section.ScoreSection
import com.bongpal.yatzee.feature.play.state.PlayUiState

@Composable
internal fun PlayRoute(
    navigateToResult: (Int) -> Unit,
    paddingValues: PaddingValues,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isShowScorePopup by remember { mutableStateOf(false) }
    var longPressedScore by remember { mutableStateOf<ScoreCategory?>(null) }
    var popUpOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    LaunchedEffect(uiState.isEnd) {
        if (uiState.isEnd) {
            navigateToResult(uiState.scoreState.finalScore)
        }
    }

    PlayScreen(
        onAction = viewModel::onAction,
        uiState = uiState,
        showScorePopup = { score, offset ->
            popUpOffset = offset
            longPressedScore = score
            isShowScorePopup = true
        },
        hideScorePopup = {
            longPressedScore = null
            isShowScorePopup = false
        },
        paddingValues = paddingValues
    )

    if (isShowScorePopup) {
        val score = longPressedScore ?: return

        ScoreInfoPopup(
            score = score,
            offset = popUpOffset,
            scoreIcon = uiState.scoreInitialImages.getValue(score),
            hideDialog = { isShowScorePopup = false },
            modifier = Modifier.fillMaxWidth(0.85f)
        )
    }
}

@Composable
private fun PlayScreen(
    onAction: (PlayIntent) -> Unit = {},
    uiState: PlayUiState = PlayUiState(),
    showScorePopup: (ScoreCategory, Offset) -> Unit = { _, _ -> },
    hideScorePopup: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 12.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ScoreSection(
                title = "상단 점수",
                scores = uiState.scoreState.upper,
                chunkSize = 3,
                scoreImages = uiState.scoreInitialImages,
                onClick = { onAction(PlayIntent.ClickScore(it)) },
                showPopup = { s, o -> showScorePopup(s.category, o) },
                hidePopup = hideScorePopup,
                isRolling = uiState.isRolling,
            )

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
                    text = "${uiState.scoreState.upperScore} / 63",
                    style = Typography.labelSmall,
                    color = LightGray
                )

                Text(
                    text = if (uiState.scoreState.upperBonus) "+35" else "+0",
                    style = Typography.headlineMedium,
                    color = if (uiState.scoreState.upperBonus) ActivePink else DefaultBlack
                )
            }

            ScoreSection(
                title = "하단 점수",
                scores = uiState.scoreState.lower,
                chunkSize = 2,
                scoreImages = uiState.scoreInitialImages,
                onClick = { onAction(PlayIntent.ClickScore(it)) },
                showPopup = { s, o -> showScorePopup(s.category, o) },
                hidePopup = hideScorePopup,
                isRolling = uiState.isRolling,
            )



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
                    text = "${uiState.scoreState.finalScore} 점",
                    color = DefaultBlack,
                    style = Typography.headlineMedium,
                )
            }
        }


        DiceSection(
            dices = uiState.dices,
            isRolling = uiState.isRolling,
            holdDice = onAction,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "다시?",
                    style = Typography.labelSmall,
                )

                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                ) {
                    (1..3 - uiState.rollCount).forEach {
                        val image = when (it) {
                            1 -> uiState.scoreInitialImages[ScoreCategory.ACES]
                            2 -> uiState.scoreInitialImages[ScoreCategory.TWOS]
                            3 -> uiState.scoreInitialImages[ScoreCategory.THREES]
                            else -> null
                        } ?: return@Row

                        Image(
                            bitmap = image.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            }
            val context = LocalContext.current
            ImageButton(
                imageVector = ImageVector.vectorResource(drawable.img_roll_button_enable),
                pressedImage = ImageVector.vectorResource(drawable.img_roll_button_pressed_enable),
                disabledImage = ImageVector.vectorResource(drawable.img_roll_button_disable),
                disabledPressedImage = ImageVector.vectorResource(drawable.img_roll_button_pressed_disable),
                enabled = uiState.rollCount < 3,
                onClick = {
                    if (uiState.dices.any { it.isHeld.not() } || uiState.dices.isEmpty()) {
                        val vibrator = context.getSystemService(Vibrator::class.java)

                        vibrator?.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )
                        onAction(PlayIntent.RollDice)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PlayScreenPreview() {
    PlayScreen()
}
