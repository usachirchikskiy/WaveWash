package com.example.wavewash.presentation.services.update_service

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.AddButton
import com.example.wavewash.presentation.helpers.common.BackButton
import com.example.wavewash.presentation.helpers.common.Logo
import com.example.wavewash.presentation.services.new_service.NewServiceEvent
import com.example.wavewash.presentation.services.new_service.NewServiceViewModel
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString
import com.example.wavewash.utils.REFRESH_SERVICES

@Composable
fun UpdateServiceScreen(
    navController: NavController,
    viewModel: UpdateServiceViewModel = hiltViewModel()
) {

    val state = viewModel.state
    if(state.changeCompleted){
        viewModel.onTriggerEvent(UpdateServiceEvent.Back)
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(REFRESH_SERVICES, REFRESH_SERVICES)
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Logo()
        Column(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 24.dp)
                .fillMaxWidth()
                .clip(Shapes.large)
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, bottom = 58.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = ComposeString.resource(R.string.new_service).value(),
                style = MaterialTheme.typography.body1,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.services_name).value(),
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
                value = state.name,
                onValueChange = { name ->
                    viewModel.onTriggerEvent(UpdateServiceEvent.ChangeNameValue(name))
                },
            )

            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.execution_time).value(),
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
                value = state.duration,
                onValueChange = { duration ->
                    if (duration.length < 5) {
                        viewModel.onTriggerEvent(UpdateServiceEvent.ChangeDurationValue(duration))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Text(
                modifier = Modifier.padding(top = 36.dp),
                text = ComposeString.resource(R.string.service_price).value(),
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
                value = state.price,
                onValueChange = { price ->
                    if (state.price.length < 7) {
                        viewModel.onTriggerEvent(UpdateServiceEvent.ChangePriceValue(price))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

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
                    viewModel.onTriggerEvent(UpdateServiceEvent.UpdateService)
                }
            )
        }
    }
}
