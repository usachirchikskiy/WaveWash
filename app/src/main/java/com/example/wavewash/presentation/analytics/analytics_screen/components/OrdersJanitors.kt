package com.example.wavewash.presentation.analytics.analytics_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun OrdersJanitors() {
    Row(
      modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(Shapes.large)
                .background(Color(0xFFEDF4F8))
                .padding(24.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.orders),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )

                Text(
                    modifier = Modifier.padding(start=14.dp),
                    text = ComposeString.resource(R.string.orders).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF9EA2B1),
                    fontSize = 14.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "125 sht",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF383C40),
                )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(Shapes.large)
                .background(Color(0xFFEDF4F8))
                .padding(24.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.janitor),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )

                Text(
                    modifier = Modifier.padding(start=14.dp),
                    text = ComposeString.resource(R.string.janitors).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF9EA2B1),
                    fontSize = 14.sp
                )
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "125 sht",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF383C40),
            )
        }

    }

}