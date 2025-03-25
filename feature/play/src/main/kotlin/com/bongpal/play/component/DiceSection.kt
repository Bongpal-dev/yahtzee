package com.bongpal.play.component

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
import com.bongpal.play.model.Dice
import com.bongpal.yatzee.feature.play.R
import kotlinx.coroutines.delay

@Composable
internal fun DiceSection(
    dices: List<Dice> = emptyList(),
    isRolling: Boolean = false,
    holdDice: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val diceResources = listOf(
        ImageVector.vectorResource(R.drawable.img_dice_1),
        ImageVector.vectorResource(R.drawable.img_dice_2),
        ImageVector.vectorResource(R.drawable.img_dice_3),
        ImageVector.vectorResource(R.drawable.img_dice_4),
        ImageVector.vectorResource(R.drawable.img_dice_5),
        ImageVector.vectorResource(R.drawable.img_dice_6)
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
            var animatedIndex by remember { mutableIntStateOf(dice.value - 1) }
            val offset by animateFloatAsState(
                targetValue = if (dice.isHeld) with(LocalDensity.current) { 32.dp.toPx() } else 0f,
                animationSpec = tween(durationMillis = 200)
            )

            LaunchedEffect(isRolling, dice.isHeld) {
                if (isRolling && !dice.isHeld) {
                    while (true) {
                        animatedIndex = (animatedIndex + 1) % diceResources.size
                        delay(70)
                    }
                }
            }

            val diceImage = if (isRolling && !dice.isHeld) {
                diceResources[animatedIndex]
            } else {
                diceResources[dice.value - 1]
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
                        onClick = { if (isRolling.not()) holdDice(i) },
                        indication = null,
                        interactionSource = null
                    )
            )
        }
    }

//    BoxWithConstraints(
//        modifier = modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(horizontal = 20.dp)
//    ) {
//        val containerWidth = maxWidth
//        val containerHeight = maxHeight
//        val gap = 8.dp
//        val diceCount = if (dices.isNotEmpty()) dices.size else 1
//        val totalSpacing = if (diceCount > 1) (diceCount - 1) * gap else 0.dp
//        val availableWidth = containerWidth - totalSpacing
//        val diceSide = if (containerHeight * diceCount > availableWidth) {
//            availableWidth / diceCount
//        } else {
//            containerHeight
//        }
//
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.spacedBy(gap, Alignment.CenterHorizontally),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            dices.forEachIndexed { i, dice ->
//                var localHeight by remember { mutableIntStateOf(1) }
//                val offset by animateFloatAsState(
//                    targetValue = if (dice.isHeld) localHeight.toFloat() else 0f,
//                    animationSpec = tween(durationMillis = 200)
//                )
//
//                var animatedIndex by remember { mutableIntStateOf(dice.value - 1) }
//
//                LaunchedEffect(isRolling, dice.isHeld) {
//                    if (isRolling && !dice.isHeld) {
//                        while (true) {
//                            animatedIndex = (animatedIndex + 1) % diceResources.size
//                            delay(70)
//                        }
//                    }
//                }
//
//                val diceImage = if (isRolling && !dice.isHeld) {
//                    diceResources[animatedIndex]
//                } else {
//                    diceResources[dice.value - 1]
//                }
//
//                Box(
//                    modifier = Modifier
//                        .size(diceSide)
//                        .onSizeChanged {
//                            localHeight = it.height
//                        }
//                ) {
//
//                    Image(
//                        imageVector = diceImage,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .graphicsLayer {
//                                translationY = offset
//                            }
//                            .clickable { if (isRolling.not()) holdDice(i) }
//                    )
//                }
//            }
//        }
//    }
}