package com.bongpal.yatzee.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.collection.LruCache
import com.bongpal.yatzee.core.domain.repository.ImageCacheRepository
import com.bongpal.yatzee.core.model.ImageSize
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.model.Tier
import com.bongpal.yatzee.core.resource.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BitmapLruCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {
    override fun sizeOf(key: String, value: Bitmap) = value.byteCount / 1024
}

class DefaultImageCacheRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageCacheRepository {
    private val density = context.resources.displayMetrics.density
    private val screenWidth = context.resources.displayMetrics.widthPixels
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 8
    private val bitmapCache = BitmapLruCache(cacheSize)
    private val tierBitmapResId = listOf(
        R.drawable.img_tier_stone,
        R.drawable.img_tier_iron,
        R.drawable.img_tier_bronze,
        R.drawable.img_tier_silver,
        R.drawable.img_tier_gold,
        R.drawable.img_tier_platinum,
        R.drawable.img_tier_diamond,
        R.drawable.img_tier_master,
        R.drawable.img_tier_grand_master,
        R.drawable.img_tier_challenger,
    )
    private val scoreBitmapResId = listOf(
        R.drawable.img_aces,
        R.drawable.img_twos,
        R.drawable.img_threes,
        R.drawable.img_fours,
        R.drawable.img_fives,
        R.drawable.img_sixes,
        R.drawable.img_three_kind,
        R.drawable.img_four_kind,
        R.drawable.img_full_house,
        R.drawable.img_small_straight,
        R.drawable.img_large_straight,
        R.drawable.img_yahtzee,
        R.drawable.img_chance,
    )


    override fun getTierImage(tier: Tier, size: ImageSize): Bitmap {
        val resId = tier.getResId()
        val key = "${resId}${size.name}"

        bitmapCache[key]?.let { return it }

        return when (size) {
            ImageSize.FIXED_48DP -> cacheImage(resId, key, (80 * density).toInt())
            ImageSize.SCREEN_65PERCENT -> cacheImage(resId, key, (screenWidth * 0.65).toInt())
        }
    }

    override fun getTierImages(): Map<Tier, Bitmap> {
        return Tier.entries.associateWith { tier ->
            val resId = tier.getResId()
            val key = "${resId}${ImageSize.FIXED_48DP.name}"
            val bitmap = bitmapCache[key] ?: cacheImage(resId, key, (80 * density).toInt())

            bitmap
        }
    }

    override fun getScoreImages(): Map<ScoreCategory, Bitmap> {
        return ScoreCategory.entries.associateWith { category ->
            val resId = category.getResId()
            val key = "$resId"
            return@associateWith bitmapCache[key] ?: cacheImage(resId, key, (80 * density).toInt())
        }
    }

    override suspend fun preLoadImage() {
        val size48Dp = (80 * density).toInt()
        val size65Percent = (screenWidth * 0.65).toInt()

        withContext(Dispatchers.IO) {
            tierBitmapResId.forEach { resId ->
                val key48Dp = "${resId}${ImageSize.FIXED_48DP.name}"
                val key65Percent = "${resId}${ImageSize.SCREEN_65PERCENT.name}"

                if (bitmapCache[key48Dp] == null) {
                    cacheImage(resId, key48Dp, size48Dp)
                }

                if (bitmapCache[key65Percent] == null) {
                    cacheImage(resId, key65Percent, size65Percent)
                }
            }

            scoreBitmapResId.forEach { resId ->
                val key = "$resId"

                if (bitmapCache[key] == null) {
                    cacheImage(resId, key, (80 * density).toInt())
                }
            }
        }
    }

    private fun cacheImage(
        @DrawableRes resId: Int,
        key: String,
        size: Int,
    ): Bitmap {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val nWidth = (size * ratio).toInt()
        val nBitmap = Bitmap.createScaledBitmap(bitmap, nWidth, size, true)

        bitmapCache.put(key, nBitmap)

        return nBitmap
    }

    private fun Tier.getResId(): Int {
        return when (this) {
            Tier.STONE -> R.drawable.img_tier_stone
            Tier.IRON -> R.drawable.img_tier_iron
            Tier.BRONZE -> R.drawable.img_tier_bronze
            Tier.SILVER -> R.drawable.img_tier_silver
            Tier.GOLD -> R.drawable.img_tier_gold
            Tier.PLATINUM -> R.drawable.img_tier_platinum
            Tier.DIAMOND -> R.drawable.img_tier_diamond
            Tier.MASTER -> R.drawable.img_tier_master
            Tier.GRANDMASTER -> R.drawable.img_tier_grand_master
            Tier.CHALLENGER -> R.drawable.img_tier_challenger
        }
    }

    private fun ScoreCategory.getResId(): Int {
        return when (this) {
            ScoreCategory.ACES -> R.drawable.img_aces
            ScoreCategory.TWOS -> R.drawable.img_twos
            ScoreCategory.THREES -> R.drawable.img_threes
            ScoreCategory.FOURS -> R.drawable.img_fours
            ScoreCategory.FIVES -> R.drawable.img_fives
            ScoreCategory.SIXES -> R.drawable.img_sixes
            ScoreCategory.THREE_OF_A_KIND -> R.drawable.img_three_kind
            ScoreCategory.FOUR_OF_A_KIND -> R.drawable.img_four_kind
            ScoreCategory.FULL_HOUSE -> R.drawable.img_full_house
            ScoreCategory.SMALL_STRAIGHT -> R.drawable.img_small_straight
            ScoreCategory.LARGE_STRAIGHT -> R.drawable.img_large_straight
            ScoreCategory.YAHTZEE -> R.drawable.img_yahtzee
            ScoreCategory.CHANCE -> R.drawable.img_chance
        }
    }
}