package com.example.wavewash.presentation.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.Screen

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val state = viewModel.state
//    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        if (state.changeScreen) {
            viewModel.onTriggerEvent(LoginEvents.ChangeChangeScreenValue)
            navController.popBackStack()
            navController.navigate(Screen.MainScreenRoute.route)
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ComposeString.resource(R.string.enter_system).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.support),
                    modifier = Modifier.clip(CircleShape).clickable {
                        //TODO ChatSupport
                    },
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.enter_login).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = TextColor
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),// height = 48.dp
                value = state.email,
                onValueChange = { text ->
                    viewModel.onTriggerEvent(LoginEvents.ChangeEmailValue(text))
                },
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(

                    cursorColor = ActiveButtonBackground,
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC),  // цвет при отсутствии фокуса
                ),

                )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.enter_password).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = TextColor
            )

            OutlinedTextField(
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),//height = 50.dp
                value = state.password,
                onValueChange = { text ->
                    viewModel.onTriggerEvent(LoginEvents.ChangePasswordValue(text))
                },
                textStyle = TextStyle(
                    fontFamily = nunitoSans,
                    fontSize = 14.sp,
                    color = TextColor,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = ActiveButtonBackground,
                    focusedBorderColor = Color(0XFFD3DDEC), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0XFFD3DDEC)  // цвет при отсутствии фокуса
                ),
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (state.isPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description =
                        if (state.isPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {
                        viewModel.onTriggerEvent(LoginEvents.ChangePasswordVisibility)
                    }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

            Box(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(Shapes.medium)
                    .clickable {
                        viewModel.onTriggerEvent(LoginEvents.Login(state.email, state.password))
                    }
                    .background(color = ActiveButtonBackground),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    text = ComposeString.resource(R.string.enter).value(),
                    color = Color.White
                )
            }
        }
    }
}//Box

//        Row(
//            modifier = Modifier.constrainAs(chatSupportLayout){
//                bottom.linkTo(loginlayout.top,margin = 48.dp)
//                centerHorizontallyTo(parent)
//            },
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Image(
//                painter = painterResource(id = R.drawable.support),
//                contentDescription = ""
//            )
//            Text(
//                modifier = Modifier.padding(start = 8.dp),
//                text = ComposeString.resource(R.string.chat_support).value(),
//                fontFamily = nunitoSans,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.Black
//            )
//        }
