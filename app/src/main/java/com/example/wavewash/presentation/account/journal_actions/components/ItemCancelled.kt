package com.example.wavewash.presentation.account.journal_actions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.GreyTextColor
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun ItemCancelled (text:String){
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = TextColor,
            text = ComposeString.resource(R.string.cancelled).value(),
        )
        Text(
            fontFamily = nunitoSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = GreyTextColor,
            text = text
        )

    }
}