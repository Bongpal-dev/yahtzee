package com.bongpal.yatzee.feature.guide.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.resource.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HoldDiceAnime(
    isDicesRoll: Boolean = false,
    modifier: Modifier = Modifier
) {
    val diceImages = listOf(
        R.drawable.img_dice_1,
        R.drawable.img_dice_2,
        R.drawable.img_dice_3,
        R.drawable.img_dice_4,
        R.drawable.img_dice_5,
        R.drawable.img_dice_6
    )
    val disableDiceImages = listOf(
        R.drawable.img_dice_1_disable,
        R.drawable.img_dice_2_disable,
        R.drawable.img_dice_3_disable,
        R.drawable.img_dice_4_disable,
        R.drawable.img_dice_5_disable,
        R.drawable.img_dice_6_disable
    )
    val buttonImages = listOf(
        R.drawable.img_roll_button_enable,
        R.drawable.img_roll_button_pressed_enable
    )


    val animatedIndices = remember { List(5) { mutableIntStateOf(Random.nextInt(6)) } }
    val isHeldStates = remember { List(5) { mutableStateOf(false) } }

    var holdPhase by remember { mutableStateOf(true) }
    var holdIndexes by remember { mutableStateOf(listOf(0, 2)) }
    var isButtonPressed = remember { mutableStateOf(false) }

    LaunchedEffect(isDicesRoll) {
        while (true) {
            delay(400)

            if (isDicesRoll) {
                isButtonPressed.value = true
                delay(100)
                isButtonPressed.value = false

                repeat(10) {
                    animatedIndices.forEachIndexed { index, state ->
                        if (!isHeldStates[index].value) {
                            state.intValue = (0..5).random()
                        }
                    }
                    delay(70)
                }

                delay(400)
            }

            if (holdPhase) {
                holdIndexes = (0..4).shuffled().take((1..4).random()).sorted()
                holdIndexes.forEach {
                    isHeldStates[it].value = true
                    delay(100)
                }
            } else {
                isHeldStates.forEach {
                    it.value = false
                    delay(100)
                }
            }

            delay(400)

            holdPhase = !holdPhase
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(0.85f)
                .height(80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            animatedIndices.forEachIndexed { i, indexState ->
                val isHeld = isHeldStates[i].value
                val offset by animateFloatAsState(
                    targetValue = if (isHeld) with(LocalDensity.current) { 32.dp.toPx() } else 0f,
                    animationSpec = tween(300)
                )

                val imageRes = if (isHeld) {
                    disableDiceImages[indexState.intValue]
                } else {
                    diceImages[indexState.intValue]
                }

                Image(
                    imageVector = ImageVector.vectorResource(imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .graphicsLayer {
                            translationY = offset
                        }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            val buttonImageRes = if (isButtonPressed.value) buttonImages[1] else buttonImages[0]

            if (isDicesRoll) {
                Image(
                    imageVector = ImageVector.vectorResource(buttonImageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxHeight()
                )
            }
        }
    }
}