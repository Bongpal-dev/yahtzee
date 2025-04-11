package com.bongpal.yatzee.core.model

enum class ScoreCategory(val displayName: String, val description: String, val section: Int) {
    ACES("에이스", "1이 나온 주사위 눈을 모두 더한 값", UPPER),
    TWOS("투", "2가 나온 주사위 눈을 모두 더한 값", UPPER),
    THREES("쓰리", "3이 나온 주사위 눈을 모두 더한 값", UPPER),
    FOURS("포", "4가 나온 주사위 눈을 모두 더한 값", UPPER),
    FIVES("파이브", "5가 나온 주사위 눈을 모두 더한 값", UPPER),
    SIXES("식스", "6이 나온 주사위 눈을 모두 더한 값", UPPER),
    FULL_HOUSE("풀 하우스", "같은 숫자 2개와 3개가 모두 있는 경우 25점", LOWER),
    THREE_OF_A_KIND("쓰리 카인드", "같은 숫자가 3개 이상일 경우 모든 주사위 숫자를 더한 값", LOWER),
    SMALL_STRAIGHT("스몰 스트레이트", "연속된 숫자가 4개가 있는 경우 30점", LOWER),
    FOUR_OF_A_KIND("포 카인드", "같은 숫자가 4개 이상일 경우 모든 주사위 숫자를 더한 값", LOWER),
    LARGE_STRAIGHT("라지 스트레이트", "연속된 숫자가 5개가 있는 경우 40점", LOWER),
    CHANCE("찬스", "모든 주사위 눈의 합", LOWER),
    YAHTZEE("야찌", "주사위 눈이 모두 같은 경우 50점", LOWER);
}

const val UPPER = 0
const val LOWER = 1