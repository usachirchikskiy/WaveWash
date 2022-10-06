package com.example.wavewash.presentation.helpers.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ScreenHeader

@Composable
fun ScreenHeaders(
    headers: List<ScreenHeader>,
    selectedOption: String,
    onClick:(Int)->Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 24.dp)
    ) {
        headers.forEachIndexed { index, btn ->
            val selected = selectedOption == btn.buttonText.invoke()
            Box(
                modifier = Modifier
                    .clip(
                        if (selected) Shapes.medium
                        else {
                            Shapes.small
                        }
                    )
                    .background(
                        if (selected) ActiveButtonBackground
                        else {
                            Color.White
                        }
                    )
                    .border(
                        if (selected) {
                            BorderStroke(
                                0.dp,
                                Color.Transparent,
                            )
                        } else {
                            BorderStroke(
                                1.dp,
                                HeaderButtonStroke
                            )
                        },
                        if (selected) Shapes.medium
                        else {
                            Shapes.small
                        }
                    )
                    .clickable {
                        onClick.invoke(index)
                    },

                ) {
                Row(
                    modifier = Modifier.height(36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.padding(start = 18.dp),
                        painter = painterResource(btn.icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 18.dp
                        ),
                        text = btn.buttonText.invoke(),
                        style = if (selected) {
                            tabStyle
                        } else {
                            screenHeadersInActive
                        },
                        color = if (selected) {
                            Color.White
                        } else {
                            HeaderButtonColor
                        },
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
        }
    }
}