package com.bongpal.yatzee.core.local.mapper

import com.bongpal.yatzee.core.local.entity.GameRecordEntity
import com.bongpal.yatzee.core.model.GameRecord
import com.bongpal.yatzee.core.model.Score
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.toTier

fun GameRecord.toEntity(): GameRecordEntity {
    val scoreList = this.scores.associate { it.category to it.point }

    return GameRecordEntity(
        player = this.player,
        totalScore = this.totalScore,
        aces = scoreList[ScoreCategory.ACES] ?: 0,
        twos = scoreList[ScoreCategory.TWOS] ?: 0,
        threes = scoreList[ScoreCategory.THREES] ?: 0,
        fours = scoreList[ScoreCategory.FOURS] ?: 0,
        fives = scoreList[ScoreCategory.FIVES] ?: 0,
        sixes = scoreList[ScoreCategory.SIXES] ?: 0,
        threeOfAKind = scoreList[ScoreCategory.THREE_OF_A_KIND] ?: 0,
        fourOfAKind = scoreList[ScoreCategory.FOUR_OF_A_KIND] ?: 0,
        fullHouse = scoreList[ScoreCategory.FULL_HOUSE] ?: 0,
        smallStraight = scoreList[ScoreCategory.SMALL_STRAIGHT] ?: 0,
        largeStraight = scoreList[ScoreCategory.LARGE_STRAIGHT] ?: 0,
        chance = scoreList[ScoreCategory.CHANCE] ?: 0,
        yahtzee = scoreList[ScoreCategory.YAHTZEE] ?: 0
    )
}

fun GameRecordEntity.toModel(): GameRecord {
    return GameRecord(
        player = this.player,
        totalScore = this.totalScore,
        tier = this.totalScore.toTier(),
        scores = listOf(
            Score(ScoreCategory.ACES, this.aces),
            Score(ScoreCategory.TWOS, this.twos),
            Score(ScoreCategory.THREES, this.threes),
            Score(ScoreCategory.FOURS, this.fours),
            Score(ScoreCategory.FIVES, this.fives),
            Score(ScoreCategory.SIXES, this.sixes),
            Score(ScoreCategory.THREE_OF_A_KIND, this.threeOfAKind),
            Score(ScoreCategory.FOUR_OF_A_KIND, this.fourOfAKind),
            Score(ScoreCategory.FULL_HOUSE, this.fullHouse),
            Score(ScoreCategory.SMALL_STRAIGHT, this.smallStraight),
            Score(ScoreCategory.LARGE_STRAIGHT, this.largeStraight),
            Score(ScoreCategory.CHANCE, this.chance),
            Score(ScoreCategory.YAHTZEE, this.yahtzee)
        )
    )
}