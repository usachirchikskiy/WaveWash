package com.example.wavewash.presentation.account.journal_actions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.account.journal_actions.components.Action
import com.example.wavewash.presentation.account.journal_actions.components.ItemCancelled
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString

data class Journal(val id: Int, val admin: String, val action: String)

@Composable
fun JournalActionsScreen(navController: NavController) {
    val list = listOf(
        Journal(123, "asdsad", "asdsadsad"),
        Journal(123, "asdsad", "asdsadsad")
    )
    val checked = remember { mutableStateOf(false) }
    Column {
//        DetailAppBar(navController)
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxHeight()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(24.dp),
        ) {
            Text(
                text = ComposeString.resource(R.string.journal_actions).value(),
                fontFamily = nunitoSans,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            Row(
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = ComposeString.resource(R.string.id).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Normal,
                    color = Color(0XFF303972),
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = ComposeString.resource(R.string.admin).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Normal,
                    color = Color(0XFF303972),
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = ComposeString.resource(R.string.time).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Normal,
                    color = Color(0XFF303972),
                    fontSize = 14.sp
                )
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = ComposeString.resource(R.string.action).value(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.Normal,
                    color = Color(0XFF303972),
                    fontSize = 14.sp
                )
            }

            Divider(
                modifier = Modifier.padding(top = 8.dp),
                color = HeaderButtonStroke,
                thickness = 1.dp
            )

            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(list.size) { index ->
                    Action(
                        journal = list[index],
                        onOpened = { checkedItem ->
                            checked.value = checkedItem
                        }
                    )
                    if(checked.value){
                        //ItemChanged(itemBefore = "", itemAfter = "")
                        ItemCancelled(text = "Ebla sad sad asd sad asd n")
                        Divider(
                            modifier = Modifier.padding(top = 8.dp),
                            color = HeaderButtonStroke,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}
