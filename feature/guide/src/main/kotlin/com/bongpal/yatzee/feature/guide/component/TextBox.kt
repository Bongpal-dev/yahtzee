package com.bongpal.yatzee.feature.guide.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongpal.yatzee.core.designsystem.theme.Gray
import com.bongpal.yatzee.core.designsystem.theme.Typography

@Composable
fun TextBox(
    text: String = "",
    modifier: Modifier = Modifier,
    onNext: (() -> Unit)? = null,
    onPrev: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.95f)
            .aspectRatio(7f / 3f)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(com.bongpal.yatzee.core.resource.R.drawable.img_popup_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f / 3f)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            text = text,
            style = Typography.bodyLarge
        )

        onPrev?.let {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomStart)
                    .clickable(
                        onClick = it,
                        indication = null,
                        interactionSource = null
                    )
            ) {
                Text(
                    text = "◀ 이전으로",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp),
                    style = Typography.labelMedium,
                    color = Gray
                )
            }
        }

        onNext?.let {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.BottomEnd)
                    .clickable(
                        onClick = it,
                        indication = null,
                        interactionSource = null
                    )
            ) {
                Text(
                    text = "다음으로 ▶",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
                    style = Typography.labelMedium,
                    color = Gray
                )
            }
        }
    }
}