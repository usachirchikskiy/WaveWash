package com.example.wavewash.presentation.account.account_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun JournalActions(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .clip(Shapes.medium)
            .background(Color.White)
            .clickable {
                onClick.invoke()
            }
            .padding(24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.document),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            text = ComposeString.resource(R.string.journal_actions).value(),
            modifier = Modifier.padding(start = 16.dp),
            fontFamily = nunitoSans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = TextColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.toggle_next),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}