package com.example.wavewash.presentation.janitors.new_janitor

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.ui.theme.*
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.REFRESH
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

private const val TAG = "NewJanitorScreen"

@Composable
fun NewJanitorScreen(
    navController: NavController,
    viewModel: NewJanitorViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 24.dp)
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, bottom = 58.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.new_janitor).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(top = 44.dp)
            ) {

                CoilImage(
                    modifier = Modifier
                        .width(248.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {

                        },
                    imageModel = "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square

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

                Column(
                    modifier = Modifier
                        .padding(start = 36.dp, end = 24.dp)
                ) {
                    Text(
                        text = ComposeString.resource(R.string.name).value(),
                        style = TextStyle(
                            color = HeaderButtonColor,
                            fontFamily = nunitoSans,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 0.sp,
                            fontSize = 14.sp,
                        ),
                        color = Color(0xFF303972),
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = state.name,
                        onValueChange = { text ->
                            viewModel.onTriggerEvent(NewJanitorEvents.ChangeNameValue(text))
                        },
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = ComposeString.resource(R.string.telephone_number).value(),
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        color = Color(0xFF303972)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = "998" + state.telephoneNumber,
                        onValueChange = { telephoneNumber ->
                            if(state.telephoneNumber.length<7) {
                                viewModel.onTriggerEvent(
                                    NewJanitorEvents.ChangePhoneNumberValue(
                                        telephoneNumber.replace("998","").toInt()
                                    )
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = ComposeString.resource(R.string.janitor_stake_percent).value(),
                        style = MaterialTheme.typography.h2,
                        fontSize = 14.sp,
                        color = Color(0xFF303972)
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFD3DDEC),
                            unfocusedBorderColor = Color(0xFFD3DDEC),
                        ),
                        singleLine = true,
                        value = state.stake,
                        onValueChange = { stake ->
                            viewModel.onTriggerEvent(NewJanitorEvents.ChangeStakeValue(stake.toInt()))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }

        Row {
            BackButton(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            AddButton(
                onClick = {
                    viewModel.onTriggerEvent(NewJanitorEvents.AddWasher)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(REFRESH, REFRESH)
                    Log.d(TAG, "NewJanitorScreen: Save")
                    navController.popBackStack()
                }
            )
        }
    }
}
