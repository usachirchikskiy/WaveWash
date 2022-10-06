package com.example.wavewash.presentation.account.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun NewPasswordScreen(navController: NavController) {
    var value by remember { mutableStateOf("Hello World") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        BackButton(
            onBackClicked = {
                navController.popBackStack()
            }
        )
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 37.dp),
        ) {
            Text(
                text = ComposeString.resource(R.string.create_new_password).value(),
                fontFamily = nunitoSans,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = ComposeString.resource(R.string.enter_old_password).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = Color(0XFF303972),
                fontSize = 14.sp
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.enter_new_password).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = Color(0XFF303972),
                fontSize = 14.sp
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

        }
    }
}
