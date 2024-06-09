package com.ysw.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ysw.presentation.ui.AlarmListScreen
import com.ysw.presentation.ui.AlarmSettingScreen
import com.ysw.presentation.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

/**
 * App nav host
 *
 * @param navController
 */
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "AlarmListScreen"
    ) {
        composable(route = "AlarmListScreen") {
            AlarmListScreen(navController = navController)
        }
        composable(route = "AlarmSettingScreen") {
            AlarmSettingScreen(navController = navController)
        }
    }
}

