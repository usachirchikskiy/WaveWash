package com.example.wavewash.presentation.helpers.nothing_found

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans

@Composable
fun NothingFound(
    emptyText:String,
    buttonText:String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.empty),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(vertical = 36.dp),
            text = emptyText,
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = TextColor
        )
        Column(
            modifier= Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ActiveButtonBackground)
                .clickable {

                }
                .padding(48.dp,16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text= buttonText,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White
            )
        }

    }
}