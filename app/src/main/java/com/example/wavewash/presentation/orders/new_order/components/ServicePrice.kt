package com.example.wavewash.presentation.orders.new_order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposableString
import com.example.wavewash.utils.ComposeString

@Composable
fun ServicePrice(
    onClick:()->Unit
) {

    var quantityServices by remember {
        mutableStateOf(1)
    }

    var value by remember { mutableStateOf("Hello World") }

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(top = 33.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.service).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = Color(0XFFD3DDEC),
                        shape = Shapes.small
                    )
                    .padding(start = 14.dp, end = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (quantityServices == 1) {
                    Text(
                        text = "Some text", //TODO service name
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = TextColor
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .clip(Shapes.medium)
                            .background(QuantityOfServices)
                            .padding(10.dp, 5.dp),
                        text = quantityServices.toString() + ComposeString.resource(R.string.quantity).value(),
                        color = TextColor,

                        )
                }

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(30.dp)
                        .background(ActiveButtonBackground)
                        .clickable {
                            onClick.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_order),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        } // service block

        Spacer(Modifier.weight(0.1f))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ComposeString.resource(R.string.janitor_stake).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                value = value,
                onValueChange = { value = it },
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.ExtraBold
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC)  // цвет при отсутствии фокуса
                )
            )
        }
    }
}