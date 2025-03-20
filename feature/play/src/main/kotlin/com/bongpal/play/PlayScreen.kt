package com.bongpal.play

import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.play.component.DiceSection
import com.bongpal.yatzee.feature.play.R

@Composable
internal fun PlayRoute(
    paddingValues: PaddingValues,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    val diceState by viewModel.diceState.collectAsStateWithLifecycle()
    val rollingState by viewModel.rollingState.collectAsStateWithLifecycle()

    PlayScreen(
        dices = diceState,
        rollingState = rollingState,
        rollDice = viewModel::rollDice,
        holdDice = viewModel::holdDice,
        paddingValues = paddingValues
    )
}

@Composable
private fun PlayScreen(
    dices: List<Dice> = emptyList(),
    rollingState: Boolean = false,
    rollDice: () -> Unit = {},
    holdDice: (Int) -> Unit = {},
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DiceSection(
            dices = dices,
            isRolling = rollingState,
            holdDice = holdDice
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextButton(
            onClick = rollDice
        ) {
            Text(
                text = "Roll",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}



