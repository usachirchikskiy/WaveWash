package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.appNameStyle
import com.example.wavewash.utils.ComposeString

@Composable
fun LogoCalendar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(16.dp))
        Text(
            style = appNameStyle,
            text = ComposeString.resource(R.string.app_name).value()
        )
        Spacer(modifier = Modifier.weight(1f))
        Calendar(
            modifier = Modifier
                .size(280.dp, 40.dp)
                .clip(Shapes.small)
                .background(Color.White),
            onCalendarPopup = {

            }
        )
    }


}