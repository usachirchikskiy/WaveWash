package com.example.wavewash.presentation.orders.order_details_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun Price (){
    Column(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text(
            text = ComposeString.resource(R.string.overall_price).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            color = Color(0XFF303972)
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = "Some Text",
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0XFF383C40)
        )
    }
}