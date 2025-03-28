package com.bongpal.yatzee.feature.scoreboard

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bongpal.yatzee.core.designsystem.component.DefaultTopAppBar
import com.bongpal.yatzee.core.designsystem.theme.ActivePink
import com.bongpal.yatzee.core.designsystem.theme.DarkGray
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.Tier
import com.bongpal.yatzee.core.ui.model.toUiModel

@Composable
internal fun ScoreBoardRoute(
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: ScoreBoardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gameRecords = viewModel.gameRecords.collectAsLazyPagingItems()

    ScoreBoardScreen(
        currentScore = uiState.currentScore,
        popBackStack = popBackStack,
        gameRecords = gameRecords,
        tierImages = uiState.tierImages,
        paddingValues = paddingValues
    )
}

@Composable
private fun ScoreBoardScreen(
    currentScore: Int? = null,
    popBackStack: () -> Unit = {},
    gameRecords: LazyPagingItems<GameRecord>,
    tierImages: Map<Tier, Bitmap> = emptyMap(),
    paddingValues: PaddingValues = PaddingValues()
) {
    val currentRecordIndex =
        gameRecords.itemSnapshotList.indexOfFirst { it?.totalScore == currentScore }
    val scrollState = rememberLazyListState()

    LaunchedEffect(currentRecordIndex) {
        if (currentRecordIndex > -1) scrollState.scrollToItem(currentRecordIndex)
    }

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "스코어 보드",
                style = Typography.headlineLarge
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = scrollState
            ) {
                items(gameRecords.itemCount) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                color = if (i == currentRecordIndex) ActivePink else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = if (i == currentRecordIndex) Color.White else DarkGray,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${i + 1}",
                                style = Typography.titleSmall,
                                color = if (i == currentRecordIndex) ActivePink else Color.White,
                            )
                        }

                        Text(
                            text = "${gameRecords[i]?.totalScore} 점",
                            style = Typography.headlineMedium,
                            color = if (i == currentRecordIndex) Color.White else DarkGray,
                            modifier = Modifier.weight(1f)
                        )

                        Image(
                            bitmap = tierImages[gameRecords[i]?.tier]?.asImageBitmap()
                                ?: return@Row,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )

                        Box {
                            Text(
                                text = "그랜드 마스터",
                                color = Color.Transparent,
                                style = Typography.labelSmall
                            )

                            Text(
                                text = gameRecords[i]?.tier?.toUiModel()?.displayName ?: "",
                                color = if (i == currentRecordIndex) Color.White else gameRecords[i]?.tier?.toUiModel()?.color
                                    ?: Color.White,
                                style = Typography.labelSmall
                            )
                        }
                    }
                }

                item {
                    Box(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}