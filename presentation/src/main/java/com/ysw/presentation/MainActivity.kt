package com.ysw.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ysw.presentation.compose.AlarmListScreen
import com.ysw.presentation.compose.AlarmSettingScreen
import com.ysw.presentation.compose.AlarmViewModel
import com.ysw.presentation.ui.theme.MyApplicationTheme
import com.ysw.presentation.utilities.ALARM_LIST_SCREEN_ROUTE
import com.ysw.presentation.utilities.ALARM_SETTING_SCREEN_ROUTE
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime


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
    val viewModel: AlarmViewModel = hiltViewModel<AlarmViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = ALARM_LIST_SCREEN_ROUTE
    ) {

        composable(route = ALARM_LIST_SCREEN_ROUTE) {
            AlarmListScreen(navController = navController,
                alarmUiState = uiState ,
                setAlarmOn = viewModel::setAlarmOn
            )

        }
        composable(route = ALARM_SETTING_SCREEN_ROUTE) {
            AlarmSettingScreen(
                onDoneClick = { navController.navigateUp() },
                alarmUiState = uiState,
                getAlarmTime = viewModel::getAlarmTime,
                updateWeekDay = viewModel::updateWeekDays,
                getAlarmVolume = viewModel::getAlarmVolume,
                setAlarmMusic = viewModel::setAlarmMusic
            )
        }
    }
}

