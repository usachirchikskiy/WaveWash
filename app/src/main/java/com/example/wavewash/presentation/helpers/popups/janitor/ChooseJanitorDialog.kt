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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.helpers.popups.components.NewAddPopup
import com.example.wavewash.presentation.helpers.popups.components.PopupExit
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseJanitorDialog(
    openDialogCustom: MutableState<Boolean>,
    washers: List<Washer>,
    addWasher: (washer: Washer) -> Unit,
    isLoading: Boolean,
    endReached: Boolean,
    newJanitorClicked: () -> Unit,
    loadMore: () -> Unit,
    onSearchQueryValue: (String) -> Unit
) {
    Dialog(
        onDismissRequest = {
            openDialogCustom.value = false
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        CustomJanitorDialogUI(
            openDialogCustom = openDialogCustom,
            washers = washers,
            addWasher = { washer ->
                addWasher.invoke(washer)
            },
            isLoading = isLoading,
            endReached = endReached,
            newJanitorClicked = {
                newJanitorClicked.invoke()
            },
            loadMore = {
                loadMore.invoke()
            },
            onSearchQueryValue = {
                onSearchQueryValue.invoke(it)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomJanitorDialogUI(
    openDialogCustom: MutableState<Boolean>,
    washers: List<Washer>,
    addWasher: (washer: Washer) -> Unit,
    isLoading: Boolean,
    endReached: Boolean,
    newJanitorClicked: () -> Unit,
    loadMore: () -> Unit,
    onSearchQueryValue: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = 90.dp)
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
        SearchBar(
            text = "",
            onSearch = { text ->
                onSearchQueryValue.invoke(text)
            }
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            cells = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                NewAddPopup(
                    isJanitor = true,
                    onClick = {
                        newJanitorClicked.invoke()
                    }
                )
            }

            items(washers.size) { index ->

                if (index >= washers.size - 1 && !endReached && !isLoading) {
                    loadMore.invoke()
                }

                JanitorItemPopup(
                    washer = washers[index],
                    addWasher = {
                        addWasher.invoke(it)
                    }
                )
            }
        }
    }
}

@Composable
fun JanitorItemPopup(
    washer: Washer,
    addWasher: (washer: Washer) -> Unit
) {
    Card(
        modifier = Modifier
            .background(Color.White),
//            .height(325.dp),
        shape = Shapes.medium,
        elevation = 0.dp,
        border = BorderStroke(1.dp, HeaderButtonStroke),
    ) {
        Column {
            //Image and Status
            Box() {
                CoilImage(
                    modifier = Modifier
                        .height(216.dp),
                    imageModel = "https://media-exp2.licdn.com/dms/image/C4D03AQFB8ojf_-tmiw/profile-displayphoto-shrink_200_200/0/1601490730164?e=2147483647&v=beta&t=lJVY_VeGE6Vp_VPV4yCrgxQgv-1qsDac6Ut9A2Ey4xw",//TODO CHANGE IMAGE
                    contentScale = ContentScale.Crop,

                    shimmerParams = ShimmerParams(
                        baseColor = Color.White,
                        highlightColor = Color.LightGray,
                        durationMillis = 350,
                        dropOff = 0.65f,
                        tilt = 20f
                    ),
                    // shows an error text message when request failed.
                    failure = {
                        Text(text = "image request failed.")
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(
                            16.dp
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .clip(Shapes.medium)
                            .background(BackOfOrdersList)
                            .padding(
                                horizontal = 9.dp, vertical = 5.dp
                            ),
                        text = if (!washer.active) ComposeString.resource(R.string.free).value()
                        else {
                            ComposeString.resource(R.string.busy).value()
                        },
                        fontSize = 12.sp,
                        color = if (!washer.active) StatusFree
                        else {
                            StatusBusy
                        }

                    )
                }
            }

            //Name and phone
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = washer.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    maxLines = 1,
                    modifier = Modifier.padding(top = 8.dp),
                    text = washer.telephoneNumber.toString(),
                    fontFamily = nunitoSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = HeaderButtonColor,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                            addWasher.invoke(washer)
                        }
                        .background(
                            color = SupportAnswerBorder
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
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
}
