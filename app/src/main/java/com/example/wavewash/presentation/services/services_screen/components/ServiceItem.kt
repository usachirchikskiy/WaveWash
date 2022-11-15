package com.example.wavewash.presentation.services.services_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.domain.model.Service
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString

@Composable
fun ServiceItem(
    service: Service,
    change: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = Shapes.medium,
        elevation = 0.dp,
        border = BorderStroke(1.dp, HeaderButtonStroke),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 18.dp,
                )
        ) {

            Text(
                modifier = Modifier.height(66.dp),
                text = service.name,
                maxLines = 2,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = service.duration.toString() + " " + ComposeString.resource(R.string.minutes)
                    .value(),
                style = MaterialTheme.typography.h2,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = service.price.toString() + " " + ComposeString.resource(R.string.sum)
                    .value(),
                style = TextStyle(
                    color = GreyTextColor,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(
                        shape = Shapes.medium
                    )
                    .border(
                        width = 1.dp,
                        color = HeaderButtonStroke,
                        shape = Shapes.medium
                    )
                    .clickable {
                        change.invoke(service.id)
                    }
                    .background(
                        color = Color.White
                    )
            ) {

                Text(
                    modifier = Modifier
                        .padding(horizontal = 34.dp, vertical = 10.dp),
                    color = Color.Black,
                    text = ComposeString.resource(R.string.change).value(),
                    style = TextStyle(
                        fontFamily = nunitoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )
            }


        }

    }
}