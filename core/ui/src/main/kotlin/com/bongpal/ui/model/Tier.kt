package com.bongpal.ui.model

import androidx.compose.ui.graphics.Color
import com.bongpal.designsystem.theme.Bronze
import com.bongpal.designsystem.theme.Challenger
import com.bongpal.designsystem.theme.Diamond
import com.bongpal.designsystem.theme.Gold
import com.bongpal.designsystem.theme.GrandMaster
import com.bongpal.designsystem.theme.Iron
import com.bongpal.designsystem.theme.Master
import com.bongpal.designsystem.theme.Platinum
import com.bongpal.designsystem.theme.Silver
import com.bongpal.designsystem.theme.Stone
import com.bongpal.yatzee.core.ui.R

enum class Tier(
    val displayName: String,
    val description: String,
    val color: Color,
    val resId: Int
) {
    STONE("스톤", "주사위 굴리는 시뮬레이터 아니에요...\n할 줄 아시는 거 맞죠?", Stone, R.drawable.img_tier_stone),
    IRON("아이언", "다시 한번 설명을 보고 오는 건 어때요?", Iron, R.drawable.img_tier_iron),
    BRONZE("브론즈", "채운다고 능사가 아니다.\n잘 생각해 보고 고득점을 노려보자!", Bronze, R.drawable.img_tier_bronze),
    SILVER("실버", "오늘따라 운이 나쁘네,\n다음 판은 괜찮을 거 같은데 한판 더?", Silver, R.drawable.img_tier_silver),
    GOLD("골드", "괜찮아, 이 정도면 잘한 거야. \n다음에 더 잘하면 돼!", Gold, R.drawable.img_tier_gold),
    PLATINUM("플래티넘", "이 정도면 어디 가서 야찌 좀 한다고 할 수 있지!", Platinum, R.drawable.img_tier_platinum),
    DIAMOND("다이아", "고수라는 말이 손색이 없는 당신! \n고수로 인정합니다!", Diamond, R.drawable.img_tier_diamond),
    MASTER("마스터", "해결사 등장!\n실수마저 실력으로 해결한다!", Master, R.drawable.img_tier_master),
    GRANDMASTER(
        "그랜드 마스터",
        "실수마저 전략이었다.\n완벽 그 자체, 야찌의 정점.",
        GrandMaster,
        R.drawable.img_tier_grand_master
    ),
    CHALLENGER("챌린저", "모든 선택이 곧 정답이다!\n야찌의 신!", Challenger, R.drawable.img_tier_challenger),
}

fun Int.toTier(): Tier {
    return when (this) {
        in 0..59 -> Tier.STONE
        in 60..99 -> Tier.IRON
        in 100..129 -> Tier.BRONZE
        in 130..159 -> Tier.SILVER
        in 160..189 -> Tier.GOLD
        in 190..219 -> Tier.PLATINUM
        in 220..249 -> Tier.DIAMOND
        in 250..289 -> Tier.MASTER
        in 290..329 -> Tier.GRANDMASTER
        in 330..375 -> Tier.CHALLENGER
        else -> throw IllegalArgumentException("Invalid score: $this")
    }
}