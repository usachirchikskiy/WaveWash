package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.nunitoSans

@Composable
fun Calendar(
    modifier: Modifier,
    onCalendarPopup: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onCalendarPopup.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {

            },
        ) {
            Icon(
                painter = painterResource(R.drawable.prev),
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Row {
            Text(
                text = "7 марта, ",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF242533)
            )
            Text(
                text = "2022",
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF86898C)
            )
        }

        IconButton(
            onClick = {

            },
        ) {
            Icon(
                painter = painterResource(R.drawable.next),
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}