package com.example.wavewash.presentation.janitors.janitors_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.domain.model.Washer
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.janitors.janitors_screen.components.JanitorItem
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*

private const val TAG = "JanitorsScreen"


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JanitorsScreen(navController: NavController, viewModel: JanitorViewModel = hiltViewModel()) {
    val state = viewModel.state
//    val listState = rememberLazyListState()
    val screenResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<String>(REFRESH)

    screenResultState?.let { value ->
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.remove<String>(REFRESH)
        Log.d(TAG, "JanitorsScreen: Value $value")
        if (value == REFRESH) {
            viewModel.onTriggerEvent(JanitorEvents.ReloadWashers)
        }

    }

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
            .fillMaxSize()
            .clip(Shapes.large)
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp)
    ) {
        item {
            ScreenHeaders(
                headerJanitorButtons,
                ComposeString.resource(R.string.new_janitor).value(),
                onClick = { index ->
                    when (index) {
                        0 -> navController.navigate(Screen.NewJanitorScreenRoute.route)
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
                    onSearch = { text ->
                        viewModel.onTriggerEvent(JanitorEvents.ChangeSearchQueryValue(text))
                    }
                )
            }
        }

        items(state.washers.chunked(gridCellsSize).size) { index ->
            val items = state.washers.chunked(gridCellsSize)[index]

            if (index >= state.washers.chunked(gridCellsSize).size - 1 && !state.endReached && !state.isLoading) {
                Log.d(TAG, "JanitorsScreen: NextPage")
                viewModel.onTriggerEvent(JanitorEvents.GetWashers)
            }
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for ((index, item) in items.withIndex()) {
                    Box(modifier = Modifier.fillMaxWidth(1f / (gridCellsSize - index))) {
                        JanitorItem(
                            washer = item,
                            onJanitorClicked = {
                                navController.navigate(Screen.JanitorDetailsScreenRoute.route)
                            }
                        )
                    }
                }
            }
        }
    }

//    listState.OnBottomReached {
//        // do on load more
//        Log.d(TAG, "JanitorsScreen: ")
//        viewModel.onTriggerEvent(JanitorEvents.GetWashers)
//    }
}


@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                Log.d(TAG, "OnBottomReached: $it")
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}