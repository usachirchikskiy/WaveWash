package com.example.wavewash.presentation.helpers.popups

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.janitors.janitors_screen.components.JanitorItem
import com.example.wavewash.presentation.helpers.popups.components.NewAddPopup
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.ui.theme.HeaderButtonStroke
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.nunitoSans
import com.example.wavewash.utils.ComposeString


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseJanitorDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        CustomJanitorDialogUI(openDialogCustom = openDialogCustom)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomJanitorDialogUI(openDialogCustom: MutableState<Boolean>) {
    val data = mutableListOf<Washer>()
    for (i in 0..3) {
        data.add(
            Washer(
                1,
                "Ariev Артур",
                "+998917788098",
                true,
                "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                15
            )
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .fillMaxHeight()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ComposeString.resource(R.string.choose_janitor).value(),
                fontFamily = nunitoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            PopupExit(onClose = {
                openDialogCustom.value = false
            })
        }
        Divider(
            modifier = Modifier.padding(vertical = 24.dp),
            color = HeaderButtonStroke
        )
//        SearchBar()
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            cells = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(data.size) { index ->
                if (index == 0) {
                    NewAddPopup(isJanitor = true)
                } else {
//                    JanitorItem(
//                        washer = data[index],
//                        onJanitorClicked = {
//
                }
//                    )
            }
        }
    }
}
//}
