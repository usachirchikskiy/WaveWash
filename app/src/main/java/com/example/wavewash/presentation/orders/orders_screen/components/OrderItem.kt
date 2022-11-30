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
import com.example.wavewash.data.remote.dto.order.OrderDto
import com.example.wavewash.domain.model.Order
import com.example.wavewash.ui.theme.QuantityOfServices
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.convert
import com.example.wavewash.utils.durationOfServices

@Composable
fun OrderItem(
    order: Order,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            }
            .padding(start = 16.dp, end = 16.dp)
            .defaultMinSize(minHeight = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .weight(0.23f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = if (order.washers.size > 1) {
                    Modifier
                        .clip(Shapes.medium)
                        .background(QuantityOfServices)
                        .padding(10.dp, 5.dp)
                } else {
                    Modifier
                },
                text = if (order.washers.size > 1) {
                    order.washers.size.toString() + " " + ComposeString.resource(R.string.quantity)
                        .value()
                } else {
                    order.washers[0].name
                },
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = TextColor,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .weight(0.23f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = order.carModel.ifEmpty {
                    ComposeString.resource(R.string.undefined).value()
                },
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
                text = order.services.size.toString() + " " + ComposeString.resource(R.string.quantity)
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
            text = convert(order.date, durationOfServices(order.services)),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

    }

}

