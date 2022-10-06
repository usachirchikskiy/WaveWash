package com.example.wavewash.presentation.account.journal_actions.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wavewash.R
import com.example.wavewash.presentation.account.journal_actions.Journal
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.SupportAnswerBorder
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

@Composable
fun Action(
    journal: Journal,
    onOpened:(checked:Boolean)->Unit
) {
    val clicked = remember { mutableStateOf(false) }
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.25f),
                text = journal.id.toString(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = TextColor,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.weight(0.25f),
                text = journal.admin,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = TextColor,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.weight(0.25f),
                text = journal.action,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Normal,
                color = TextColor,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier.weight(0.25f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = journal.action,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Bold,
                    color = SupportAnswerBorder,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        clicked.value = !clicked.value
                        onOpened.invoke(clicked.value)
                    },
                ) {
                    Icon(
                        painter =
                        if (clicked.value) {
                            painterResource(R.drawable.updown)
                        } else {
                            painterResource(R.drawable.dropdown)
                        },
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.padding(top = 8.dp),
            color = HeaderButtonStroke,
            thickness = 1.dp
        )
    }

}