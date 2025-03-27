package com.bongpal.yatzee.core.model

enum class Tier {
    STONE,
    IRON,
    BRONZE,
    SILVER,
    GOLD,
    PLATINUM,
    DIAMOND,
    MASTER,
    GRANDMASTER,
    CHALLENGER
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