package com.bongpal.play.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.bongpal.play.model.Dice
import com.bongpal.play.util.getDiceImages
import com.bongpal.play.util.resize
import kotlinx.coroutines.delay

@Composable
internal fun DiceSection(
    dices: List<Dice> = emptyList(),
    isRolling: Boolean = false,
    holdDice: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // 주사위 이미지 리소스들을 가져옵니다.
    val diceResources = getDiceImages()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
    ) {
        val containerWidth = maxWidth
        val containerHeight = maxHeight
        val gap = 12.dp
        val diceCount = if (dices.isNotEmpty()) dices.size else 1
        val totalSpacing = if (diceCount > 1) (diceCount - 1) * gap else 0.dp
        val availableWidth = containerWidth - totalSpacing
        val diceSide = if (containerHeight * diceCount > availableWidth) {
            availableWidth / diceCount
        } else {
            containerHeight
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(gap, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            dices.forEachIndexed { i, dice ->
                var localWidth by remember { mutableIntStateOf(1) }
                var localHeight by remember { mutableIntStateOf(1) }
                val offset by animateFloatAsState(
                    targetValue = if (dice.isHeld) localHeight.toFloat() else 0f,
                    animationSpec = tween(durationMillis = 200)
                )

                var animatedIndex by remember { mutableIntStateOf(dice.value - 1) }

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

                Box(
                    modifier = Modifier
                        .size(diceSide) // 정사각형으로, 계산된 diceSide 적용
                        .onSizeChanged {
                            localWidth = it.width
                            localHeight = it.height
                        }
                ) {
                    val resizedImage = remember(localWidth, localHeight, diceImage) { diceImage.resize(localWidth, localHeight) }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { translationY = offset }
                            .drawBehind { drawImage(resizedImage) }
                            .clickable { if (!isRolling) holdDice(i) }
                    )
                }
            }
        }
    }
}

//@Composable
//internal fun DiceSection(
//    dices: List<Dice> = emptyList(),
//    isRolling: Boolean = false,
//    holdDice: (Int) -> Unit = {},
//    modifier: Modifier = Modifier,
//) {
//    val diceResources = getDiceImages()
//
//    BoxWithConstraints(
//        modifier = modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(horizontal = 20.dp)
//    ) {
//        val containerWidth = maxWidth
//        val containerHeight = maxHeight
//        val space = 12.dp
//        val totalSpacing = if (dices.size > 1) (dices.size - 1) * space else 0.dp
//    }
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(horizontal = 20.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        dices.forEachIndexed { i, dice ->
//            var width by remember { mutableIntStateOf(1) }
//            var height by remember { mutableIntStateOf(1) }
//            val offset by animateFloatAsState(
//                targetValue = if (dice.isHeld) height.toFloat() else 0f,
//                animationSpec = tween(durationMillis = 200)
//            )
//
//            var animatedIndex by remember { mutableIntStateOf(dice.value - 1) }
//
//            LaunchedEffect(isRolling, dice.isHeld) {
//                if (isRolling && dice.isHeld.not()) {
//                    while (true) {
//                        animatedIndex = (animatedIndex + 1) % diceResources.size
//                        delay(70)
//                    }
//                }
//            }
//
//            val diceImage = if (isRolling && !dice.isHeld) {
//                diceResources[animatedIndex]
//            } else {
//                diceResources[dice.value - 1]
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .aspectRatio(1f)
//                    .onSizeChanged {
//                        width = it.width
//                        height = it.height
//                    }
//            ) {
//                val resizedImage = remember(width, height, diceImage) { diceImage.resize(width, height) }
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .graphicsLayer { translationY = offset }
//                        .drawBehind { drawImage(resizedImage) }
//                        .clickable { if (isRolling.not()) holdDice(i) }
//                )
//            }
//        }
//    }
//}