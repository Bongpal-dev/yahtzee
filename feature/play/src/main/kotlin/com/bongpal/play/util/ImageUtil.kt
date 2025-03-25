package com.bongpal.play.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.bongpal.play.model.Score
import com.bongpal.play.model.ScoreCategory
import com.bongpal.yatzee.feature.play.R

@Composable
fun Score.getScoreImage(): ImageVector {
    return when (category) {
        ScoreCategory.ACES -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_ace,
                selectedResId = R.drawable.img_ace_selected,
                pickedResId = R.drawable.img_ace_picked
            )
        }

        ScoreCategory.TWOS -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_twos,
                selectedResId = R.drawable.img_twos_selected,
                pickedResId = R.drawable.img_twos_picked
            )
        }

        ScoreCategory.THREES -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_threes,
                selectedResId = R.drawable.img_threes_selected,
                pickedResId = R.drawable.img_threes_picked
            )
        }

        ScoreCategory.FOURS -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_fours,
                selectedResId = R.drawable.img_fours_selected,
                pickedResId = R.drawable.img_fours_picked
            )
        }

        ScoreCategory.FIVES -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_fives,
                selectedResId = R.drawable.img_fives_selected,
                pickedResId = R.drawable.img_fives_picked
            )
        }

        ScoreCategory.SIXES -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_sixes,
                selectedResId = R.drawable.img_sixes_selected,
                pickedResId = R.drawable.img_sixes_picked
            )
        }

        ScoreCategory.THREE_OF_A_KIND -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_three_kind,
                selectedResId = R.drawable.img_three_kind_selected,
                pickedResId = R.drawable.img_three_kind_picked
            )
        }

        ScoreCategory.FOUR_OF_A_KIND -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_four_kind,
                selectedResId = R.drawable.img_four_kind_selected,
                pickedResId = R.drawable.img_four_kind_picked
            )
        }

        ScoreCategory.FULL_HOUSE -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_full_house,
                selectedResId = R.drawable.img_full_house_selected,
                pickedResId = R.drawable.img_full_house_picked
            )
        }

        ScoreCategory.SMALL_STRAIGHT -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_small_straight,
                selectedResId = R.drawable.img_small_straight_selected,
                pickedResId = R.drawable.img_small_straight_picked
            )
        }

        ScoreCategory.LARGE_STRAIGHT -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_large_straight,
                selectedResId = R.drawable.img_large_straight_selected,
                pickedResId = R.drawable.img_large_straight_picked
            )
        }

        ScoreCategory.CHANCE -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_chance,
                selectedResId = R.drawable.img_chance_selected,
                pickedResId = R.drawable.img_chance_picked
            )
        }

        ScoreCategory.YAHTZEE -> {
            this.getImageWithState(
                defaultResId = R.drawable.img_yahtzee,
                selectedResId = R.drawable.img_yahtzee_selected,
                pickedResId = R.drawable.img_yahtzee_picked
            )
        }
    }
}

@Composable
private fun Score.getImageWithState(
    @DrawableRes defaultResId: Int,
    @DrawableRes selectedResId: Int,
    @DrawableRes pickedResId: Int,
): ImageVector {
    return when {
        isPicked -> ImageVector.vectorResource(pickedResId)
        isSelected -> ImageVector.vectorResource(selectedResId)
        else -> ImageVector.vectorResource(defaultResId)
    }
}