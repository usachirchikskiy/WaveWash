package com.example.wavewash.presentation.orders.order_details_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun TextMinutes (){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = ComposeString.resource(R.string.order_detail).value(),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = TextColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(132.dp, 39.dp)
                .border(
                    width = 1.dp,
                    color = Color(0XFFD3DDEC),
                    shape = Shapes.small
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                text = "минут"
            )
        }
    }
}