package com.bongpal.yatzee.feature.scoreboard.scoredetail

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongpal.yatzee.core.designsystem.component.DefaultTopAppBar
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.feature.scoreboard.scoredetail.component.ScoreDetailContent
import com.bongpal.yatzee.feature.scoreboard.scoredetail.component.ScoreResultContent
import kotlinx.coroutines.launch

@Composable
internal fun ScoreDetailRoute(
    popBackStack: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: ScoreDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ScoreDetailScreen(
        gameRecords = uiState.value.gameRecords,
        tierImages = uiState.value.tierImages[uiState.value.gameRecords?.tier],
        scoreImages = uiState.value.scoreImages,
        popBackStack = popBackStack,
        paddingValues = paddingValues
    )
}

@Composable
private fun ScoreDetailScreen(
    gameRecords: GameRecord? = null,
    tierImages: Bitmap? = null,
    scoreImages: Map<ScoreCategory, Bitmap> = emptyMap(),
    popBackStack: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues()
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        DefaultTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = popBackStack
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(com.bongpal.yatzee.core.designsystem.R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            if (page == 0) {
                ScoreResultContent(
                    tierImage = tierImages,
                    finalScore = gameRecords?.totalScore ?: 0,
                    onChangePage = { coroutineScope.launch { pagerState.animateScrollToPage(1) } }
                )
            } else {
                ScoreDetailContent(
                    gameRecords = gameRecords,
                    scoreImages = scoreImages,
                    onChangePage = { coroutineScope.launch { pagerState.animateScrollToPage(0) } }
                )
            }
        }
    }
}

