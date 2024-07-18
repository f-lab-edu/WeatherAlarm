package com.ysw.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ysw.presentation.compose.AlarmListScreen
import com.ysw.presentation.compose.AlarmListViewModel
import com.ysw.presentation.compose.AlarmSettingScreen
import com.ysw.presentation.compose.AlarmSettingViewModel
import com.ysw.presentation.ui.theme.MyApplicationTheme
import com.ysw.presentation.utilities.AlarmScreen
import com.ysw.presentation.utilities.NAVIGATE_ARGUMENT_ID
import com.ysw.presentation.utilities.NAVIGATE_ARGUMENT_TIME
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


    NavHost(
        navController = navController,
        startDestination = AlarmScreen.ALARM_LIST.name
    ) {

        composable(route = AlarmScreen.ALARM_LIST.name) {
            val viewModel: AlarmListViewModel = hiltViewModel<AlarmListViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            AlarmListScreen(
                navController = navController,
                alarmUiState = uiState,
                setOnOffAlarm = viewModel::setOnOffAlarm,
                deleteAlarm = viewModel::deleteAlarm,
                setAlarmListUI = viewModel::setAlarmListUi
            )

        }
        composable(
            route = "${AlarmScreen.ALARM_SETTING.name}/{id}/{time}",
            arguments = listOf(
                navArgument(NAVIGATE_ARGUMENT_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(NAVIGATE_ARGUMENT_TIME) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { entry ->
            val id = entry.arguments?.getString(NAVIGATE_ARGUMENT_ID)?.toIntOrNull()
            val time = entry.arguments?.getString(NAVIGATE_ARGUMENT_TIME)?.let { LocalTime.parse(it) }
            val viewModel: AlarmSettingViewModel = hiltViewModel<AlarmSettingViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            AlarmSettingScreen(
                alarmId = id,
                time = time,
                onDoneClick = { navController.navigateUp() },
                alarmUiState = uiState,
                setAlarmUi = viewModel::setAlarmUi,
                getAlarmTime = viewModel::getAlarmTime,
                updateWeekDay = viewModel::updateWeekDays,
                getAlarmVolume = viewModel::getAlarmVolume,
                setAlarmMusic = viewModel::setAlarmMusic,
                saveAlarm = viewModel::saveAlarm
            )
        }
    }
}

