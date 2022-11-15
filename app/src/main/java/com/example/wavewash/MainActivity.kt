package com.example.wavewash

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.wavewash.nav_graphs.NavGraph
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.WaveWashTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WaveWashTheme {
                val pagerState = rememberPagerState()
                val navController = rememberNavController()
                Scaffold() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppBackground)
                            .padding(horizontal = 24.dp, vertical = 32.dp)
                    ) {
                        NavGraph(
                            pagerState = pagerState,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
