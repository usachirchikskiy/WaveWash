package com.example.wavewash.presentation.janitors.janitor_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.ui.theme.GreyTextColor
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString

@Composable
fun CardsJanitorAnalytics(modifier: Modifier,washer: Washer,scaleSize:Double,scaleRegulator:Double) {
    Column(
        modifier = modifier
            .padding(top = 28.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ComposeString.resource(R.string.earned).value(),
            style = MaterialTheme.typography.body1,
            fontSize = (20*scaleSize).sp
        )
        Text(
//            modifier = Modifier.padding(top = 16.dp),
            text = washer.status.toString(),
            style = MaterialTheme.typography.body1,
            fontSize = ((28-scaleRegulator)*scaleSize).sp,
            color = GreyTextColor
        )
        Divider()
        Text(
            text = ComposeString.resource(R.string.janitor_stake).value(),
            style = MaterialTheme.typography.body1,
            fontSize = ((20-scaleRegulator)*scaleSize).sp
        )
        Text(
//            modifier = Modifier.padding(top = 16.dp),
            text = "100000 sum",//washer.stake.toString(),
            style = MaterialTheme.typography.body1,
            fontSize = ((28-scaleRegulator)*scaleSize).sp,
            color = GreyTextColor,
            maxLines = 1
        )

    }
}