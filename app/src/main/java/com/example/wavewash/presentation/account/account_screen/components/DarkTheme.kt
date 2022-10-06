package com.example.wavewash.presentation.account.account_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString

@Composable
fun DarkTheme (){
    val checkedState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .clip(Shapes.medium)
            .background(Color.White)
            .padding(24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(R.drawable.dark_theme),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            text = ComposeString.resource(R.string.dark_theme).value(),
            modifier = Modifier.padding(start = 16.dp),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColor
        )
        Spacer(modifier= Modifier.weight(1f))
        Switch(
            modifier = Modifier.size(32.dp,20.dp),
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
//                if(checkedState.value) textColor.value = Color(0xFFC62828)
//                else textColor.value = Color.Unspecified
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ActiveButtonBackground,
                uncheckedTrackColor = SwitchBackground,
                uncheckedThumbColor = Color.White
            )
        )
    }

}