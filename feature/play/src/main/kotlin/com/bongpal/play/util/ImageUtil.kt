package com.bongpal.play.util

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import com.bongpal.play.model.ScoreCategory
import com.bongpal.yatzee.feature.play.R

@Composable
internal fun getDiceImages(): List<ImageBitmap> {
    return listOf(
        ImageBitmap.imageResource(R.drawable.img_dice_default_1),
        ImageBitmap.imageResource(R.drawable.img_dice_default_2),
        ImageBitmap.imageResource(R.drawable.img_dice_default_3),
        ImageBitmap.imageResource(R.drawable.img_dice_default_4),
        ImageBitmap.imageResource(R.drawable.img_dice_default_5),
        ImageBitmap.imageResource(R.drawable.img_dice_default_6)
    )
}

@Composable
internal fun Int.getDiceImage(): ImageBitmap {
    return when (this) {
        1 -> ImageBitmap.imageResource(R.drawable.img_dice_default_1)
        2 -> ImageBitmap.imageResource(R.drawable.img_dice_default_2)
        3 -> ImageBitmap.imageResource(R.drawable.img_dice_default_3)
        4 -> ImageBitmap.imageResource(R.drawable.img_dice_default_4)
        5 -> ImageBitmap.imageResource(R.drawable.img_dice_default_5)
        6 -> ImageBitmap.imageResource(R.drawable.img_dice_default_6)
        else -> throw IllegalArgumentException("Invalid dice value: $this")
    }
}

@Composable fun ScoreCategory.getScoreImage(): ImageBitmap {
    return when (this) {
        ScoreCategory.ACES -> ImageBitmap.imageResource(R.drawable.img_dice_default_1)
        ScoreCategory.TWOS -> ImageBitmap.imageResource(R.drawable.img_dice_default_2)
        ScoreCategory.THREES -> ImageBitmap.imageResource(R.drawable.img_dice_default_3)
        ScoreCategory.FOURS -> ImageBitmap.imageResource(R.drawable.img_dice_default_4)
        ScoreCategory.FIVES -> ImageBitmap.imageResource(R.drawable.img_dice_default_5)
        ScoreCategory.SIXES -> ImageBitmap.imageResource(R.drawable.img_dice_default_6)
        ScoreCategory.THREE_OF_A_KIND -> ImageBitmap.imageResource(R.drawable.img_three_of_a_kind)
        ScoreCategory.FOUR_OF_A_KIND -> ImageBitmap.imageResource(R.drawable.img_four_of_a_kind)
        ScoreCategory.FULL_HOUSE -> ImageBitmap.imageResource(R.drawable.img_full_house)
        ScoreCategory.SMALL_STRAIGHT -> ImageBitmap.imageResource(R.drawable.img_small_straight)
        ScoreCategory.LARGE_STRAIGHT -> ImageBitmap.imageResource(R.drawable.img_large_straight)
        ScoreCategory.CHANCE -> ImageBitmap.imageResource(R.drawable.img_chance)
        ScoreCategory.YAHTZEE -> ImageBitmap.imageResource(R.drawable.img_yahtzee)
    }
}

internal fun ImageBitmap.resize(newWidth: Int, newHeight: Int): ImageBitmap {
    val prev = Bitmap.createBitmap(this.asAndroidBitmap())
    this.prepareToDraw()
    return Bitmap.createScaledBitmap(prev, newWidth, newHeight, true).asImageBitmap()
}