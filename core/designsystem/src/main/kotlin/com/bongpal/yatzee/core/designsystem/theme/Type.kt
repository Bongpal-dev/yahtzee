package com.bongpal.yatzee.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.bongpal.yatzee.core.designsystem.R

private val DoodleDefault = TextStyle.Default.copy(
    fontFamily = FontFamily(Font(R.font.man_seh)),
    fontWeight = FontWeight.Black,
    color = DefaultBlack,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = DoodleDefault.copy(
        fontSize = 36.sp,
    ),
    headlineMedium = DoodleDefault.copy(
        fontSize = 22.sp
    ),
    titleLarge = DoodleDefault.copy(
        fontSize = 16.sp,
    ),
    titleSmall = DoodleDefault.copy(
        fontSize = 14.sp
    ),
    labelSmall = DoodleDefault.copy(
        fontSize = 12.sp
    )
)