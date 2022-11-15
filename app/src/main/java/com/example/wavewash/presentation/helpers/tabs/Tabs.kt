package com.example.wavewash.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wavewash.R
import com.example.wavewash.ui.theme.ActiveButtonBackground
import com.example.wavewash.ui.theme.Shapes
import com.example.wavewash.utils.ComposeString
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import com.example.wavewash.ui.theme.TextColor
import com.example.wavewash.ui.theme.tabStyle
import com.example.wavewash.utils.tabs

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .clip(Shapes.large)
            .background(Color.White)
            .padding(vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //tabs
        Row(
            modifier = Modifier.padding(
                start = if (pagerState.currentPage == 0) {
                    7.dp
                } else {
                    24.dp
                }
            )
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(
                            start = if (selected) {
                                17.dp
                            } else {
                                0.dp
                            },
                            end = if (selected) {
                                17.dp
                            } else {
                                0.dp
                            }
                        )
                        .clip(
                            Shapes.medium
                        )
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                ) {

                    Text(
                        style = tabStyle,
                        text = tab.title.invoke(),
                        color = if (selected) Color.White
                        else {
                            TextColor
                        },
                        modifier = Modifier
                            .background(
                                if (selected) ActiveButtonBackground
                                else {
                                    Color.White
                                }
                            )
                            .padding(
                                start = if (selected) {
                                    34.dp
                                } else {
                                    17.dp
                                },
                                end = if (selected) {
                                    34.dp
                                } else {
                                    17.dp
                                },
                                top = 10.dp,
                                bottom = 10.dp
                            )
                    )
                }
            }
        }
    }
}