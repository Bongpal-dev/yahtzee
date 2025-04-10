package com.bongpal.yatzee.feature.play.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.bongpal.yatzee.core.designsystem.theme.Typography
import com.bongpal.yatzee.core.model.ScoreCategory
import com.bongpal.yatzee.core.resource.R.drawable

@Composable
internal fun ScoreInfoPopup(
    score: ScoreCategory = ScoreCategory.ACES,
    scoreIcon: Bitmap,
    offset: Offset = Offset(0f, 0f),
    hideDialog: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var popupHeight by remember { mutableStateOf(0) }

    Popup(
        offset = IntOffset(
            offset.x.toInt(),
            y = (offset.y - popupHeight - (popupHeight / 3)).toInt().coerceAtLeast(0)
        ),
        onDismissRequest = hideDialog,
    ) {
        Box(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    popupHeight = coordinates.size.height
                },
        ) {
            Image(
                imageVector = ImageVector.vectorResource(drawable.img_popup_bg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(7f / 3f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        bitmap = scoreIcon.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.height(24.dp)
                    )
                    Text(
                        text = score.displayName,
                        style = Typography.titleLarge
                    )
                }

                Text(
                    text = score.description,
                    style = Typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ScoreInfoDialogPreview() {
    ScoreInfoPopup(
        scoreIcon = ImageBitmap.imageResource(drawable.img_ace_dummy).asAndroidBitmap()
    )
}