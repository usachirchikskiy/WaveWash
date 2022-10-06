package com.example.wavewash.presentation.account.journal_actions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.GreyTextColor
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans

@Composable
fun ItemChanged(
    itemBefore: String,
    itemAfter: String
) {
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.weight(0.25f),
        ) {
            Text(
                fontFamily = nunitoSans,
                fontSize = 12.sp,
                color = TextColor,
                fontWeight = FontWeight.Normal,
                text = "Chto-to"//TODO Change Name
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = nunitoSans,
                fontSize = 16.sp,
                color = GreyTextColor,
                fontWeight = FontWeight.Bold,
                text = "Chto-to"
            )
        }
        Column(
            modifier = Modifier.weight(0.25f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.after_arrow),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.weight(0.25f),
        ) {
            Text(
                fontFamily = nunitoSans,
                fontSize = 12.sp,
                color = TextColor,
                fontWeight = FontWeight.Normal,
                text = "Chto-to"//TODO Change Name
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = nunitoSans,
                fontSize = 16.sp,
                color = GreyTextColor,
                fontWeight = FontWeight.Bold,
                text = "Chto-to"
            )
        }

        Column(modifier = Modifier.weight(0.25f)) {

        }
    }
}
