package com.example.wavewash.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wavewash.R

// Set of Material typography styles to start with
val nunitoSans = FontFamily(
    listOf(
        Font(R.font.nunitosans_bold, FontWeight.Bold),
        Font(R.font.nunitosans_light, FontWeight.Light) ,
        Font(R.font.nunitosans_black, FontWeight.Black),
        Font(R.font.nunitosans_extra_bold, FontWeight.ExtraBold),
        Font(R.font.nunitosans_semi_bold, FontWeight.SemiBold),
        Font(R.font.nunitosans_regular)
    )
)

val appNameStyle = TextStyle(
    color =  TextColor,
    fontFamily = nunitoSans,
    fontSize = 24.sp,
    fontWeight = FontWeight.Black
)


val tabStyle = TextStyle(
    color =  TextColor,
    fontFamily = nunitoSans,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold
)

val ordersTabActive = TextStyle(
    color =  TextColor,
    fontFamily = nunitoSans,
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold
)

val ordersTabInActive = TextStyle(
    color =  HeaderButtonColor,
    fontFamily = nunitoSans,
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal
)

val screenHeadersInActive = TextStyle(
    color = HeaderButtonColor,
    fontFamily = nunitoSans,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp
)

val Typography = Typography(
    body1 = TextStyle(
        color = TextColor,
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        color = GreyTextColor,
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
        color = TextColor,
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        color = HeaderButtonColor,
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)