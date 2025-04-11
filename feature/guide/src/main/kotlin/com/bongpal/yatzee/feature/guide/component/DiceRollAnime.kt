package com.bongpal.yatzee.feature.guide.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.resource.R
import kotlinx.coroutines.delay

@Composable
fun DiceRollAnime(
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
    val rollCountIcons = mapOf(
        1 to R.drawable.img_aces,
        2 to R.drawable.img_twos,
        3 to R.drawable.img_threes
    )

    val animatedIndices = remember { List(5) { mutableIntStateOf(0) } }
    var rollCount by remember { mutableStateOf(3) }
    var isRolling by remember { mutableStateOf(false) }
    var isDiceVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            if (rollCount == 3) {
                isDiceVisible = false
                delay(800)
            }

            isRolling = true
            isDiceVisible = true
            rollCount--

            repeat(10) {
                animatedIndices.forEach { it.intValue = (0..5).random() }
                delay(70)
            }

            isRolling = false

            delay(if (rollCount == 0) 1500 else 600)

            if (rollCount == 0) rollCount = 3
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            if (isDiceVisible) {
                animatedIndices.forEach { indexState ->
                    val imageRes = diceImages[indexState.intValue]
                    Image(
                        imageVector = ImageVector.vectorResource(imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .height(32.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..rollCount).forEach {
                val iconRes = rollCountIcons[it] ?: R.drawable.img_aces
                Image(
                    bitmap = ImageBitmap.imageResource(iconRes),
                    contentDescription = "Roll $it",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}