package com.bongpal.play.component

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.bongpal.play.Dice
import com.bongpal.yatzee.feature.play.R
import kotlinx.coroutines.delay

@Composable
internal fun DiceSection(
    dices: List<Dice> = emptyList(),
    isRolling: Boolean = false, // 주사위 굴림 중 여부
    holdDice: (Int) -> Unit = {},
) {
    val diceResources = getDiceImages()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        dices.forEachIndexed { i, dice ->
            var width by remember { mutableIntStateOf(1) }
            var height by remember { mutableIntStateOf(1) }
            val offset by animateFloatAsState(
                targetValue = if (dice.isHeld) height.toFloat() else 0f,
                animationSpec = tween(durationMillis = 200)
            )

            var animatedIndex by remember { mutableIntStateOf(dice.value - 1) }

            LaunchedEffect(isRolling, dice.isHeld) {
                if (isRolling && dice.isHeld.not()) {
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
                    .weight(1f)
                    .aspectRatio(1f)
                    .onSizeChanged {
                        width = it.width
                        height = it.height
                    }
            ) {
                val resizedImage = diceImage.resize(width, height)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { translationY = offset }
                        .drawBehind { drawImage(resizedImage) }
                        .clickable { if (isRolling.not()) holdDice(i) }
                )
            }
        }
    }
}

@Composable
private fun getDiceImages(): List<ImageBitmap> {
    return listOf(
        ImageBitmap.imageResource(R.drawable.img_dice_default_1),
        ImageBitmap.imageResource(R.drawable.img_dice_default_2),
        ImageBitmap.imageResource(R.drawable.img_dice_default_3),
        ImageBitmap.imageResource(R.drawable.img_dice_default_4),
        ImageBitmap.imageResource(R.drawable.img_dice_default_5),
        ImageBitmap.imageResource(R.drawable.img_dice_default_6)
    )
}

private fun ImageBitmap.resize(newWidth: Int, newHeight: Int): ImageBitmap {
    val prev = Bitmap.createBitmap(this.asAndroidBitmap())
    this.prepareToDraw()
    return Bitmap.createScaledBitmap(prev, newWidth, newHeight, true).asImageBitmap()
}