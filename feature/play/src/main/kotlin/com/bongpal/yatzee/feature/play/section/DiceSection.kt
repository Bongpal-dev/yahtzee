package com.bongpal.yatzee.feature.play.section

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.resource.R.drawable
import com.bongpal.yatzee.feature.play.model.Dice
import com.bongpal.yatzee.feature.play.model.PlayIntent
import kotlinx.coroutines.delay

@Composable
internal fun DiceSection(
    dices: List<Dice> = emptyList(),
    isRolling: Boolean = false,
    holdDice: (PlayIntent) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val diceResources = listOf(
        ImageVector.vectorResource(drawable.img_dice_1),
        ImageVector.vectorResource(drawable.img_dice_2),
        ImageVector.vectorResource(drawable.img_dice_3),
        ImageVector.vectorResource(drawable.img_dice_4),
        ImageVector.vectorResource(drawable.img_dice_5),
        ImageVector.vectorResource(drawable.img_dice_6)
    )
    val disableDiceResources = listOf(
        ImageVector.vectorResource(drawable.img_dice_1_disable),
        ImageVector.vectorResource(drawable.img_dice_2_disable),
        ImageVector.vectorResource(drawable.img_dice_3_disable),
        ImageVector.vectorResource(drawable.img_dice_4_disable),
        ImageVector.vectorResource(drawable.img_dice_5_disable),
        ImageVector.vectorResource(drawable.img_dice_6_disable)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        dices.forEachIndexed { i, dice ->
            var animatedIndex by remember(isRolling, dice) { mutableIntStateOf(dice.value - 1) }
            val offset by animateFloatAsState(
                targetValue = if (dice.isHeld) with(LocalDensity.current) { 32.dp.toPx() } else 0f,
                animationSpec = tween(durationMillis = 200)
            )

            LaunchedEffect(isRolling, dice) {
                while (isRolling && !dice.isHeld) {
                    animatedIndex = (animatedIndex + 1) % diceResources.size
                    delay(70)
                }
            }

            val diceImage = when {
                dice.isHeld -> disableDiceResources[dice.value - 1]
                isRolling && !dice.isHeld -> diceResources[animatedIndex]
                else -> diceResources[dice.value - 1]
            }

            Image(
                imageVector = diceImage,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .graphicsLayer {
                        translationY = offset
                    }
                    .clickable(
                        onClick = { if (isRolling.not()) holdDice(PlayIntent.HoldDice(i)) },
                        indication = null,
                        interactionSource = null
                    )
            )
        }
    }
}