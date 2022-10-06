package com.example.wavewash.presentation.helpers.popups

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.R
import com.example.wavewash.domain.model.Service
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.helpers.popups.components.NewAddPopup
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseServiceDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        CustomServiceDialogUI(openDialogCustom = openDialogCustom)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomServiceDialogUI(openDialogCustom: MutableState<Boolean>) {
    val data = mutableListOf<Service>()
    for (i in 0..10) {
        data.add(
            Service(1, "Moyka \nMashin", 60, 10000),
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .fillMaxHeight()
            //.fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ComposeString.resource(R.string.choose_service).value(),
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
                    NewAddPopup()
                } else {
                    ServiceItemPopup(data[index])
                }
            }
        }
    }
}


@Composable
fun ServiceItemPopup(
    service: Service
) {

    Card(
        modifier = Modifier
            .background(Color.White)
            .height(255.dp),
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
                modifier = Modifier.height(75.dp),
                text = service.name,
                maxLines = 2,
                fontFamily = nunitoSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = service.duration.toString()
                        + " "// + ComposeString.resource(R.string.minutes).value(),
                ,
                style = MaterialTheme.typography.h2,
                fontSize = 14.sp
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = service.duration.toString(),
                style = TextStyle(
                    color = GreyTextColor,
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(
                        shape = Shapes.medium
                    )
                    .border(
                        width = 0.dp,
                        color = HeaderButtonStroke,
                        shape = Shapes.medium
                    )
                    .clickable {

                    }
                    .background(
                        color = SupportAnswerBorder
                    )
            ) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 34.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color.White,
                        text = ComposeString.resource(R.string.add).value(),
                        style = TextStyle(
                            fontFamily = nunitoSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )

                    Image(
                        painter = painterResource(R.drawable.add_order),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }
            }


        }

    }
}