package com.example.wavewash.presentation.orders.orders_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString

@Composable
fun HeadersOfList() {
    Row(
        modifier = Modifier
            //.padding(top = 24.dp)
            .height(40.dp)
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(BackOfOrdersList)
            .padding(start = 16.dp,end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.name).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.car).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.18f),
            text = ComposeString.resource(R.string.number).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.13f),
            text = ComposeString.resource(R.string.service).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.2f),
            text = ComposeString.resource(R.string.time).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
    }
}


@Composable
fun OverallHeadersOfList() {
    Row(
        modifier = Modifier
            //.padding(top = 24.dp)
            .height(40.dp)
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(BackOfOrdersList)
            .padding(start = 16.dp,end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.name).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.car).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.18f),
            text = ComposeString.resource(R.string.number).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.13f),
            text = ComposeString.resource(R.string.service).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.2f),
            text = ComposeString.resource(R.string.price).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
    }
}

@Composable
fun StakeHeadersOfList() {
    Row(
        modifier = Modifier
            //.padding(top = 24.dp)
            .height(40.dp)
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(BackOfOrdersList)
            .padding(start = 16.dp,end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.name).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.23f),
            text = ComposeString.resource(R.string.car).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )

        Text(
            modifier = Modifier
                .weight(0.18f),
            text = ComposeString.resource(R.string.number).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.13f),
            text = ComposeString.resource(R.string.service).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
//
        Text(
            modifier = Modifier
                .weight(0.2f),
            text = ComposeString.resource(R.string.stake).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor
        )
    }
}