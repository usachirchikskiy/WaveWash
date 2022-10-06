package com.example.wavewash.presentation.orders.new_order.components

import androidx.compose.foundation.*
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
import com.example.wavewash.utils.ComposeString

@Composable
fun CarModelNumber() {

    var value by remember { mutableStateOf("Hello World") }
    var carColor by remember { mutableStateOf(Color.Black) }
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(top = 33.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = ComposeString.resource(R.string.car_model).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )
            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(width = 310.dp),
                value = value,
                onValueChange = { value = it },
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC)  // цвет при отсутствии фокуса
                ),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .border(
                                1.dp,
                                Color(0xFFD3DDEC),
                                RoundedCornerShape(8.dp)
                            )
                            .background(
                                carColor
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.car_color),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            )
        }

        Spacer(Modifier.weight(0.1f))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = ComposeString.resource(R.string.car_number).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0XFF303972)
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(width = 310.dp),
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