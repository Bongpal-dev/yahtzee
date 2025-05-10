package com.bongpal.yatzee.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.ui.model.ImageResource

@Composable
fun ScoreContent(
    point: Int?,
    color: Color,
    image: ImageResource?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ScoreImage(image = image)
        PointText(point = point, color = color)
    }
}

// ============= Internal Components =============

@Composable
private fun RowScope.ScoreImage(image: ImageResource?) {
    when (image) {
        is ImageResource.Bitmap -> Image(
            bitmap = image.bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier.weight(1f, false)
        )

        is ImageResource.Drawable -> Image(
            painter = painterResource(id = image.drawableId),
            contentDescription = null,
            modifier = Modifier.weight(1f, false)
        )

        is ImageResource.Vector -> Image(
            imageVector = image.vector,
            contentDescription = null,
            modifier = Modifier.weight(1f, false)
        )

        is ImageResource.None, null -> {}
    }
}

@Composable
private fun PointText(
    point: Int?,
    color: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = "000",
            style = Typography.headlineMedium,
            color = Color.Transparent,
        )
        Text(
            text = point?.toString() ?: "",
            style = Typography.headlineMedium,
            color = color,
        )
    }
}