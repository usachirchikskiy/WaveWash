package com.example.wavewash.presentation.account.chat_support

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.account.chat_support.components.SupportAnswer
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposableString
import com.example.wavewash.utils.ComposeString

@Composable
fun ChatSupportScreen(navController: NavController) {
    //var value by remember { mutableStateOf("Hello World") }
    var number by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }
    Column {
        BackButton(
            onBackClicked = {
                navController.popBackStack()
            }
        )
        SupportAnswer()
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 37.dp),
        ) {
            Text(
                text = ComposeString.resource(R.string.chat_support).value(),
                fontFamily = nunitoSans,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = ComposeString.resource(R.string.contact).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = Color(0XFF303972),
                fontSize = 14.sp
            )


            OutlinedTextField(
                value = number,
                onValueChange = {
                    number = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                ),
                placeholder = {
                    Text(
                        text = ComposeString.resource(R.string.enter_the_number).value(),
                        color = SwitchBackground,
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC),
                    cursorColor = Color.Black// цвет при отсутствии фокуса
                ),
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
            )

            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = ComposeString.resource(R.string.describe_message).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = Color(0XFF303972),
                fontSize = 14.sp
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 96.dp),
                value = message,
                onValueChange = { message = it },
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC),
                    cursorColor = Color.Black// цвет при отсутствии фокуса
                )
            )

            Column(
                modifier = Modifier
                    .padding(top = 36.dp)
                    .fillMaxWidth()
                    .clip(Shapes.medium)
                    .background(SupportAnswerBorder)
                    .padding(vertical = 14.dp)
                    .clickable {

                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = ComposeString.resource(R.string.send).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}