package com.example.wavewash.presentation.helpers.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wavewash.presentation.helpers.common.LogoNameCompany
import com.example.wavewash.presentation.common.Tabs
import com.example.wavewash.presentation.common.TabsContent
import com.example.wavewash.utils.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsScreen(
    pagerState : PagerState,
    navController : NavController
) {
    Column(){
        LogoNameCompany(
            onClick = {
                navController.navigate(Screen.AccountScreenRoute.route)
            }
        )
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState,navController = navController)
    }

}