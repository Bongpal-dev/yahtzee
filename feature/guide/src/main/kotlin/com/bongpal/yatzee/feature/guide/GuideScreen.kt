package com.bongpal.yatzee.feature.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.component.DefaultTextButton
import com.bongpal.yatzee.core.designsystem.component.DefaultTopAppBar
import com.bongpal.yatzee.core.resource.R
import com.bongpal.yatzee.feature.guide.component.DiceRollAnime
import com.bongpal.yatzee.feature.guide.component.HoldDiceAnime
import com.bongpal.yatzee.feature.guide.component.TextBox

@Composable
internal fun GuideRoute(
    popBackStack: () -> Unit,
    padding: PaddingValues
) {
    GuideScreen(
        popBackStack = popBackStack,
        padding = padding
    )
}

@Composable
private fun GuideScreen(
    popBackStack: () -> Unit = {},
    padding: PaddingValues = PaddingValues()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        var currentPage by remember { mutableStateOf(0) }
        val pagerState = rememberPagerState { guidePages.size }

        LaunchedEffect(currentPage) {
            pagerState.animateScrollToPage(currentPage)
        }

        DefaultTopAppBar(
            trailingIcon = {
                DefaultTextButton(
                    text = "나가기",
                    onClick = popBackStack
                )
            }
        )

        HorizontalPager(
            modifier = Modifier
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) {
            guidePages[it](
                { currentPage = (currentPage - 1).coerceAtLeast(0) },
                { currentPage++ }
            )
        }
    }
}

val guidePages: List<@Composable (() -> Unit, () -> Unit) -> Unit> = listOf(
    { _, onNext -> GuideContent1(onNext) },
    { onPrev, onNext -> GuideContent2(onPrev, onNext) },
    { onPrev, onNext -> GuideContent3(onPrev, onNext) },
    { onPrev, onNext -> GuideContent4(onPrev, onNext) },
    { onPrev, onNext -> GuideContent5(onPrev, onNext) },
    { onPrev, onNext -> GuideContent6(onPrev, onNext) },
    { onPrev, onNext -> GuideContent7(onPrev, onNext) },
    { onPrev, onNext -> GuideContent8(onPrev, onNext) },
    { onPrev, _ -> GuideContent9(onPrev) },
)

@Composable
fun GuideContent1(
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val diceImages = listOf(
            R.drawable.img_dice_1,
            R.drawable.img_dice_2,
            R.drawable.img_dice_3,
            R.drawable.img_dice_4,
            R.drawable.img_dice_5,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.Center)
                .offset(y = (-32).dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
        ) {
            diceImages.forEach {
                Image(
                    imageVector = ImageVector.vectorResource(it),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }

        TextBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "야찌는 5개의 주사위를 굴려 다양한 조합으로 점수를 얻는 게임입니다.",
            onNext = onNext
        )
    }
}

@Composable
fun GuideContent2(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DiceRollAnime(
            modifier = Modifier
                .align(Alignment.Center)
        )
        TextBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "한 턴에는 최대 3번까지 주사위를 굴릴 수 있습니다.",
            onPrev = onPrev,
            onNext = onNext
        )
    }
}

@Composable
fun GuideContent3(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var step by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        HoldDiceAnime(
            isDicesRoll = step > 1,
            modifier = Modifier
                .align(Alignment.Center)
        )

        when (step) {
            0 -> {
                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "원하는 주사위는 고정해서 굴리지 않을 수도 있습니다.",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            1 -> {
                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "고정 할 주사위를 선택하면 주사위가 아래로 이동하며 고정됩니다.",
                    onPrev = { step-- },
                    onNext = { step++ }
                )
            }

            2 -> {
                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "굴리기 버튼을 누르면 고정되지 않은 주사위들을 다시 굴립니다.",
                    onPrev = { step-- },
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun GuideContent4(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var step by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (step) {
            0 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_01),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "주사위를 굴린 뒤에는 가능한 점수 조합이 표시되며, 턴을 마치기 위해서는 이 중 하나를 반드시 선택해야 합니다.",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            1 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_02),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "점수 아이콘을 한 번 누르면 해당 점수가 하이라이트됩니다.",
                    onPrev = { step-- },
                    onNext = { step++ }
                )
            }

            2 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_03),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "하이라이트된 상태에서 다시 한 번 누르면, 해당 점수를 선택해 기록합니다.",
                    onPrev = { step-- },
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun GuideContent5(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_score_explain_04),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        TextBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "만들 수 있는 점수 조합이 없다면, 비어 있는 항목을 선택해 0점으로 기록해야 합니다.",
            onPrev = onPrev,
            onNext = onNext
        )
    }
}

@Composable
fun GuideContent6(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var step by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (step) {
            0 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_05),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "점수는 상단 부분과 하단 부분으로 나뉩니다.",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            1 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_06),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "상단 점수는 1~6 눈 중 같은 숫자의 주사위를 모두 더한 값으로 계산됩니다.",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            2 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_07),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "상단 점수의 총합이 63점을 넘기면, 추가로 35점의 보너스를 얻습니다.",
                    onPrev = onPrev,
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun GuideContent7(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    var step by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (step) {
            0 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_08),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "하단 점수는 조합이 완성되면 정해진 점수를 얻는 왼쪽 영역과",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            1 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_09),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "모든 주사위 값을 더하는 오른쪽 영역으로 나뉩니다.",
                    onPrev = onPrev,
                    onNext = { step++ }
                )
            }

            2 -> {
                Image(
                    painter = painterResource(R.drawable.img_score_explain_10),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )

                TextBox(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "왼쪽 영역은 조건을 만족하면 고정된 점수를 획득하고, 오른쪽 영역은 조합 조건을 만족하면 주사위 눈의 합이 점수가 됩니다.",
                    onPrev = onPrev,
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun GuideContent8(
    onPrev: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_score_explain_11),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        TextBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "각 족보별 점수는 다음과 같습니다.",
            onPrev = onPrev,
            onNext = onNext
        )
    }
}

@Composable
fun GuideContent9(
    onPrev: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.img_score_explain_12),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        TextBox(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "게임 도중 족보를 확인하고 싶다면, 해당 조합 아이콘을 꾹 눌러 설명을 확인할 수 있습니다.",
            onPrev = onPrev,
        )
    }
}