package com.bongpal.yatzee.core.ui.model

import androidx.compose.ui.graphics.Color
import com.bongpal.yatzee.core.designsystem.theme.Bronze
import com.bongpal.yatzee.core.designsystem.theme.Challenger
import com.bongpal.yatzee.core.designsystem.theme.Diamond
import com.bongpal.yatzee.core.designsystem.theme.Gold
import com.bongpal.yatzee.core.designsystem.theme.GrandMaster
import com.bongpal.yatzee.core.designsystem.theme.Iron
import com.bongpal.yatzee.core.designsystem.theme.Master
import com.bongpal.yatzee.core.designsystem.theme.Platinum
import com.bongpal.yatzee.core.designsystem.theme.Silver
import com.bongpal.yatzee.core.designsystem.theme.Stone
import com.bongpal.yatzee.core.model.Tier

data class TierUiModel(
    val displayName: String,
    val description: String,
    val color: Color,
)

fun Int.toUiModel(): TierUiModel = when (this) {
    in 0..59 -> Tier.STONE.toUiModel()
    in 60..99 -> Tier.IRON.toUiModel()
    in 100..129 -> Tier.BRONZE.toUiModel()
    in 130..159 -> Tier.SILVER.toUiModel()
    in 160..189 -> Tier.GOLD.toUiModel()
    in 190..219 -> Tier.PLATINUM.toUiModel()
    in 220..249 -> Tier.DIAMOND.toUiModel()
    in 250..289 -> Tier.MASTER.toUiModel()
    in 290..329 -> Tier.GRANDMASTER.toUiModel()
    in 330..375 -> Tier.CHALLENGER.toUiModel()
    else -> throw IllegalArgumentException("Invalid score: $this")
}

fun Tier.toUiModel(): TierUiModel = when (this) {
    Tier.STONE -> TierUiModel(
        "스톤",
        "주사위 굴리는 시뮬레이터 아니에요...\n할 줄 아시는 거 맞죠?",
        Stone,
    )

    Tier.IRON -> TierUiModel("아이언", "다시 한번 설명을 보고 오는 건 어때요?", Iron)
    Tier.BRONZE -> TierUiModel(
        "브론즈",
        "채운다고 능사가 아니다.\n잘 생각해 보고 고득점을 노려보자!",
        Bronze,
    )

    Tier.SILVER -> TierUiModel(
        "실버",
        "오늘따라 운이 나쁘네,\n다음 판은 괜찮을 거 같은데 한판 더?",
        Silver,
    )

    Tier.GOLD -> TierUiModel(
        "골드",
        "괜찮아, 이 정도면 잘한 거야. \n다음에 더 잘하면 돼!",
        Gold,
    )

    Tier.PLATINUM -> TierUiModel(
        "플래티넘",
        "이 정도면 어디 가서 야찌 좀 한다고 할 수 있지!",
        Platinum,
    )

    Tier.DIAMOND -> TierUiModel(
        "다이아",
        "고수라는 말이 손색이 없는 당신! \n고수로 인정합니다!",
        Diamond,
    )

    Tier.MASTER -> TierUiModel(
        "마스터",
        "해결사 등장!\n실수마저 실력으로 해결한다!",
        Master,
    )

    Tier.GRANDMASTER -> TierUiModel(
        "그랜드 마스터",
        "실수마저 전략이었다.\n완벽 그 자체, 야찌의 정점.",
        GrandMaster,
    )

    Tier.CHALLENGER -> TierUiModel(
        "챌린저",
        "모든 선택이 곧 정답이다!\n야찌의 신!",
        Challenger,
    )
}