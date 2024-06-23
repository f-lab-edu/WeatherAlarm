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
import com.ysw.presentation.utilities.ALARM_LIST_SCREEN_ROUTE
import com.ysw.presentation.utilities.ALARM_SETTING_SCREEN_ROUTE
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
        startDestination = ALARM_LIST_SCREEN_ROUTE
    ) {
        composable(route = ALARM_LIST_SCREEN_ROUTE) {
            AlarmListScreen(navController = navController)
        }
        composable(route = ALARM_SETTING_SCREEN_ROUTE) {
            AlarmSettingScreen{navController.navigateUp()}
        }
    }
}

