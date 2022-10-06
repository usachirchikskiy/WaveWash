package com.example.wavewash.presentation.orders.orders_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Order
import com.example.wavewash.ui.theme.QuantityOfServices
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun OrderItem(
    order: Order,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .defaultMinSize(minHeight = 56.dp)
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .weight(0.23f),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            CoilImage(
//                imageModel = order.workerImageUrl,
//                contentScale = ContentScale.Crop,// crop the image if it's not a square
//                modifier = Modifier
//                    .size(24.dp)
//                    .clip(CircleShape),
//
//                shimmerParams = ShimmerParams(
//                    baseColor = Color.White,
//                    highlightColor = Color.LightGray,
//                    durationMillis = 350,
//                    dropOff = 0.65f,
//                    tilt = 20f
//                ),
//                // shows an error text message when request failed.
//                failure = {
//                    Text(text = "image request failed.")
//                })

            Text(
               // modifier = Modifier.padding(start = 8.dp),
                text = order.workerName,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                color = TextColor
            )
        }

        Row(
            modifier = Modifier
                .weight(0.23f),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            CoilImage(
//                imageModel = order.carImageUrl,
//                contentScale = ContentScale.Crop,// crop the image if it's not a square
//                modifier = Modifier
//                    .size(24.dp)
//                    .clip(CircleShape),
//
//                shimmerParams = ShimmerParams(
//                    baseColor = Color.White,
//                    highlightColor = Color.LightGray,
//                    durationMillis = 350,
//                    dropOff = 0.65f,
//                    tilt = 20f
//                ),
//                // shows an error text message when request failed.
//                failure = {
//                    Text(text = "image request failed.")
//                })

            Text(
                //modifier = Modifier.padding(start = 8.dp),
                text = order.carName,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = TextColor
            )
        }

        Text(
            modifier = Modifier
                .weight(0.18f),
            textAlign = TextAlign.Start,
            text = order.carNumber,
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Box(
            modifier = Modifier.weight(0.13f)
        ) {
            Text(
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(QuantityOfServices)
                    .padding(10.dp, 5.dp),
                text = order.quantityOfServices.toString() + " " + ComposeString.resource(R.string.quantity)
                    .value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor
            )
        }

        Text(
            modifier = Modifier
                .weight(0.20f),
            text = order.time,
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

    }

}

