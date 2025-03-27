package com.bongpal.yatzee.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class GameRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val player: String,
    val totalScore: Int,
    val aces: Int,
    val twos: Int,
    val threes: Int,
    val fours: Int,
    val fives: Int,
    val sixes: Int,
    val threeOfAKind: Int,
    val fourOfAKind: Int,
    val fullHouse: Int,
    val smallStraight: Int,
    val largeStraight: Int,
    val chance: Int,
    val yahtzee: Int
)
