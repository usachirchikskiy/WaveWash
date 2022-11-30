package com.example.wavewash

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.wavewash.domain.use_cases.SessionManager
import com.example.wavewash.nav_graphs.NavGraph
import com.example.wavewash.ui.theme.AppBackground
import com.example.wavewash.ui.theme.WaveWashTheme
import com.example.wavewash.utils.Resource
import com.example.wavewash.utils.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WaveWashTheme {
                val pagerState = rememberPagerState()
                val navController = rememberNavController()
                val startDestination = remember { mutableStateOf("") }

                LaunchedEffect(key1 = true) {
                    when (sessionManager.execute()) {
                        is Resource.Success -> {
                            Log.d("Content", "onCreate: Success")
                            startDestination.value = Screen.MainScreenRoute.route
                        }
                        is Resource.Error -> {
                            Log.d("Content", "onCreate: Error")
                            startDestination.value = Screen.LoginScreenRoute.route
                        }
                    }
                }

                if (startDestination.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppBackground)
                            .padding(horizontal = 24.dp, vertical = 32.dp)
                    ) {
                        NavGraph(
                            pagerState = pagerState,
                            navController = navController,
                            startDestination = startDestination.value
                        )
                    }
                }
            }
        }
    }
}
