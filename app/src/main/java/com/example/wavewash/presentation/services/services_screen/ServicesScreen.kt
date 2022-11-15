package com.example.wavewash.presentation.services.services_screen


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.janitors.janitors_screen.JanitorEvents
import com.example.wavewash.presentation.services.new_service.NewServiceEvent
import com.example.wavewash.presentation.services.services_screen.components.ServiceItem
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*

private const val TAG = "ServicesScreen"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServicesScreen(
    navController: NavController,
    viewModel: ServiceViewModel = hiltViewModel()
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

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
    ) {

        item {
            ScreenHeaders(
                isVisible = true,
                headers = headerServiceButtons,
                selectedOption = ComposeString.resource(R.string.new_service).value(),
                onClick = { index ->
                    when (index) {
                        0 -> {
                            navController.navigate(Screen.NewServiceScreenRoute.route)
                        }
                    }
                }
            )
        }

        stickyHeader {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(bottom = 24.dp)
            ) {
                SearchBar(
                    text = state.searchQuery,
                    onSearch = { text ->
                        viewModel.onTriggerEvent(ServiceEvent.ChangeSearchQueryValue(text))
                    }
                )
            }
        }

        items(state.services.chunked(gridCellsSize).size) { index ->
            val items = state.services.chunked(gridCellsSize)[index]

            if (index >= state.services.chunked(gridCellsSize).size - 1 && !state.endReached && !state.isLoading) {
                viewModel.onTriggerEvent(ServiceEvent.GetServices)
            }

            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for ((index, item) in items.withIndex()) {
                    Box(modifier = Modifier.fillMaxWidth(1f / (gridCellsSize - index))) {
                        ServiceItem(
                            service = item,
                            change = {
                                navController.navigate(Screen.UpdateServiceScreen.route + "/$it")
                            }
                        )
                    }
                }
            }
        }
    }
}

