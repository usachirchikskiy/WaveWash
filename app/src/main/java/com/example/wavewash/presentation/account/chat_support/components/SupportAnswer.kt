package com.example.wavewash.presentation.account.chat_support.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.SupportAnswerBorder
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun SupportAnswer (){
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .clip(Shapes.medium)
            .background(color = com.example.wavewash.ui.theme.SupportAnswer)
            .border(
                BorderStroke(
                    1.dp,
                    SupportAnswerBorder
                ),
                shape = Shapes.medium
            )
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = ComposeString.resource(R.string.thanks).value(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = nunitoSans
        )
    }
}