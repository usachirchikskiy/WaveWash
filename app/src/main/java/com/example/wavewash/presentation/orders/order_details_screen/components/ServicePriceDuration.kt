package com.example.wavewash.presentation.orders.order_details_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun ServicePriceDuration(
    serviceName: String,
    price: String,
    duration: String
) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1/3f)) {
            Text(
                text = ComposeString.resource(R.string.service).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0XFF303972)
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = serviceName,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0XFF383C40),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(modifier = Modifier.weight(1/3f)) {
            Text(
                text = ComposeString.resource(R.string.price).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0XFF303972)
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = price +" cум",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0XFF383C40)
            )
        }

        Column(modifier = Modifier.weight(1/3f)) {
            Text(
                text = ComposeString.resource(R.string.time).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = Color(0XFF303972)
            )

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = "$duration мин",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0XFF383C40)
            )
        }
    }
}