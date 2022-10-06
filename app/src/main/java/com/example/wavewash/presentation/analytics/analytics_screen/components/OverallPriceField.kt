package com.example.wavewash.presentation.analytics.analytics_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.GreyTextColor
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun OverallPriceField(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(Color.White)
            .clickable {
                onClick.invoke()
            }
            .padding(24.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.sale),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = ComposeString.resource(R.string.overall_price).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF9EA2B1)
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = ComposeString.resource(R.string.overall_price).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            color = GreyTextColor,
            fontSize = 24.sp
        )
    }
}
