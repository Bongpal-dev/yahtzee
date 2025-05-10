package com.bongpal.yatzee.feature.play.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.resource.R
import com.bongpal.yatzee.feature.play.model.ScoreUiModel

@Composable
internal fun ScoreUiModel.getScoreImage(): ImageVector {
    return when (category) {
        ScoreCategory.ACES -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_ace_selected,
                pickedResId = R.drawable.img_ace_picked
            )
        }

        ScoreCategory.TWOS -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_twos_selected,
                pickedResId = R.drawable.img_twos_picked
            )
        }

        ScoreCategory.THREES -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_threes_selected,
                pickedResId = R.drawable.img_threes_picked
            )
        }

        ScoreCategory.FOURS -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_fours_selected,
                pickedResId = R.drawable.img_fours_picked
            )
        }

        ScoreCategory.FIVES -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_fives_selected,
                pickedResId = R.drawable.img_fives_picked
            )
        }

        ScoreCategory.SIXES -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_sixes_selected,
                pickedResId = R.drawable.img_sixes_picked
            )
        }

        ScoreCategory.THREE_OF_A_KIND -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_three_kind_selected,
                pickedResId = R.drawable.img_three_kind_picked
            )
        }

        ScoreCategory.FOUR_OF_A_KIND -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_four_kind_selected,
                pickedResId = R.drawable.img_four_kind_picked
            )
        }

        ScoreCategory.FULL_HOUSE -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_full_house_selected,
                pickedResId = R.drawable.img_full_house_picked
            )
        }

        ScoreCategory.SMALL_STRAIGHT -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_small_straight_selected,
                pickedResId = R.drawable.img_small_straight_picked
            )
        }

        ScoreCategory.LARGE_STRAIGHT -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_large_straight_selected,
                pickedResId = R.drawable.img_large_straight_picked
            )
        }

        ScoreCategory.CHANCE -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_chance_selected,
                pickedResId = R.drawable.img_chance_picked
            )
        }

        ScoreCategory.YAHTZEE -> {
            this.getImageWithState(
                selectedResId = R.drawable.img_yahtzee_selected,
                pickedResId = R.drawable.img_yahtzee_picked
            )
        }
    }
}

@Composable
private fun ScoreUiModel.getImageWithState(
    @DrawableRes selectedResId: Int,
    @DrawableRes pickedResId: Int,
): ImageVector {
    return when {
        isPicked -> ImageVector.vectorResource(pickedResId)
        else -> ImageVector.vectorResource(selectedResId)
    }
}