package com.example.wavewash.presentation.janitors.janitor_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.ui.theme.GreyTextColor
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun CardsJanitorAnalytics(
    modifier: Modifier,
    earnedMoney:String,
    earnedStake:String
) {
    Column(
        modifier = modifier
            .padding(top = 28.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ComposeString.resource(R.string.earned).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextColor
        )
        Text(
            text = earnedMoney,
            color = GreyTextColor,
            fontFamily = nunitoSans,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Divider()
        Text(
            text = ComposeString.resource(R.string.janitor_stake).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextColor
        )
        Text(
            text = earnedStake,
            color = GreyTextColor,
            fontFamily = nunitoSans,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

    }
}