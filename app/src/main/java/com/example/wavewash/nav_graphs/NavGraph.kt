package com.example.wavewash.nav_graphs

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
@Composable
fun NavGraph(configuration: Configuration,pagerState: PagerState, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Main_Graph_Route"
    ) {
        mainGraph(configuration = configuration,pagerState = pagerState, navController = navController)
    }
}