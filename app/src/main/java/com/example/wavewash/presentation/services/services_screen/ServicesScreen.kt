package com.example.wavewash.presentation.services

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wavewash.R
import com.example.wavewash.domain.model.Service
import com.example.wavewash.presentation.helpers.common.ScreenHeaders
import com.example.wavewash.presentation.helpers.common.SearchBar
import com.example.wavewash.presentation.services.services_screen.components.ServiceItem
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServicesScreen(navController: NavController) {

    val data = listOf(
        Service(1, "Moyka", 60, 10000),
        Service(1, "Polirovka \nMashin", 60, 10000),
        Service(1, "Moyka Mashin", 60, 10000),
        Service(1, "Moyka Mashin", 60, 10000),
        Service(1, "Moyka \nMashin", 60, 10000),
        Service(1, "123456789012345678901234567890", 60, 10000),
    )

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(Color.White)
            .padding(horizontal = 16.dp).padding(top = 24.dp)
    ) {
//        Column(
//            modifier = Modifier
//                .padding(horizontal = 16.dp, vertical = 24.dp)
//                .fillMaxWidth()
//        ) {

        item {
            ScreenHeaders(
                headerServiceButtons,
                ComposeString.resource(R.string.new_service).value(),
                onClick = { index->
                    when (index){
                        0 -> navController.navigate(Screen.NewServiceScreenRoute.route)
                    }
                }
            )
        }

        stickyHeader {
            Column(modifier = Modifier
                .background(Color.White)
                .padding(bottom = 24.dp)) {
//                SearchBar()
            }
        }

        items(data.chunked(gridCellsSize)) { items ->
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for ((index, item) in items.withIndex()) {
                    //println("$index $item")
                    Box(modifier = Modifier.fillMaxWidth(1f / (gridCellsSize - index))) {
                        ServiceItem(service = item)
                    }
                }
            }
        }
    }
}

//        item {

//            LazyVerticalGrid(
//                modifier = Modifier
//                    .padding(top = 24.dp)
//                    .fillParentMaxHeight()
//                ,
//                cells = GridCells.Fixed(3),
//                verticalArrangement = Arrangement.spacedBy(24.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//                items(data.size) { index ->
//                    ServiceItem(service = data[index])
//                }
//            }
//        }
//        }

