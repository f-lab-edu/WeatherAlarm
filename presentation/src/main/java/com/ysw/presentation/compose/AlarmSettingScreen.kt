package com.ysw.presentation.compose

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.ysw.presentation.R
import com.ysw.presentation.alarm.AlarmHandler
import com.ysw.presentation.utilities.findActivity
import com.ysw.presentation.utilities.getFileName
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime

/**
 * 알람 추가, 설정 화면
 *
 * @param navController
 */

@Composable
fun AlarmSettingScreen(
    onDoneClick: () -> Unit,
    alarmUiState: AlarmUiState,
    getAlarmTime: (LocalTime) -> Unit,
    updateWeekDay: (String) -> Unit,
    getAlarmVolume: (Float) -> Unit,
    setAlarmMusic: (String,Uri) -> Unit

) {


    Scaffold(
        bottomBar = {
            BottomButtons(
                onCancelClick = { onDoneClick() },
                onDoneClick = {
                    onDoneClick()
                }
            )
        }
    ) { contentPadding ->

        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            Column(
                Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WheelTimerPickerView(
                    //시간 추출
                    getAlarmTime = { getAlarmTime(it) }
                )
                DayChipUI(
                    selectedList = alarmUiState.alarmList,
                    updateSelectedList = { updateWeekDay(it) }
                )
                SoundSliderUI(
                    volume = alarmUiState.volume,
                    getVolume = { getAlarmVolume(it) }
                    //사운드 크기 추출
                )
                SoundByWeatherView(
                    alarmMusic = alarmUiState.musicListByWeather,
                    getAlarmMusic = { weather, uri ->
                        setAlarmMusic(weather, uri)
                    }
                )
                //uri 추출

            }
        }
    }
}

/**
 * TimePicker View
 *
 */
@Composable
private fun WheelTimerPickerView(
    getAlarmTime: (LocalTime) -> Unit
) {
    Box() {
        WheelTimePicker(
            modifier = Modifier.padding(16.dp),
            timeFormat = TimeFormat.AM_PM,
            size = DpSize(400.dp, 300.dp),
            textStyle = MaterialTheme.typography.displayLarge,
            startTime = LocalTime.now(),
        ) { localTime ->
            getAlarmTime(localTime)
        }
    }
    Divider(color = Color.Gray)
}


/**
 * 알람이 울릴 날짜를 선택하는 UI
 *
 */
@Composable
private fun DayChipUI(
    selectedList: List<String>,
    updateSelectedList: (String) -> Unit
) {

    val dayList = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        UIDayChipUsingFlowRow(
            list = dayList,
            selectedList = selectedList,
            onOptionSelected = {
                updateSelectedList(it)
            }
        )
    }
    Divider(color = Color.Gray)
}


/**
 *  알람이 울릴 날짜를 선택하는 UI의 Chip 부분
 *
 * @param list
 * @param selectedList      알람이 울리는 요일 리스트
 * @param onOptionSelected  chip 클릭시 이벤트
 * @receiver
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun UIDayChipUsingFlowRow(
    list: List<String>,
    selectedList: List<String>,
    onOptionSelected: (String) -> Unit
) {
    FlowRow(
    ) {
        list.forEach { week ->
            FilterChip(
                modifier = Modifier.padding(1.dp),
                selected = week in selectedList,
                onClick = { onOptionSelected(week) },
                label = {
                    Text(week, style = MaterialTheme.typography.bodySmall)
                }
            )
        }
    }
}

/**
 * 음량 조절 View
 *
 */
@Composable
private fun SoundSliderUI(
    volume: Float,
    getVolume: (Float) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
            Slider(
                modifier = Modifier.padding(8.dp),
                value = volume,
                onValueChange = { getVolume(it) },
                steps = 10
            )
        }
    }
    Divider(color = Color.Gray)
}

/**
 *  날씨 별 음악 View
 *
 */
@Composable
private fun SoundByWeatherView(
    alarmMusic: Map<String, Uri>,
    getAlarmMusic: (String,Uri) -> Unit
) {

    val context = LocalContext.current
    val weatherList = listOf("맑음", "비", "눈")

    weatherList.forEach { weather ->
        val uri = alarmMusic[weather] ?: Uri.EMPTY
        SetSoundByWeather(
            weather = weather,
            song = getFileName(uri = uri, context = context)
        ) {
            getAlarmMusic(weather, it)
        }
    }

}

/**
 * 날씨별 소리 설정
 *
 * @param weather 날씨
 * @param song    해당하는 노래
 */
@Composable
private fun SetSoundByWeather(
    weather: String,
    song: String,
    getSongUri: (Uri) -> Unit
) {

    val context = LocalContext.current
    var songName by remember { mutableStateOf(song) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val selectAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    getSongUri(uri)
                    songName = getFileName(uri = uri, context = context)
                }
            }
        }
    )


    val storagePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                getMusicFromStorage{
                    selectAudioLauncher.launch(it)
                }
            } else {
                showPermissionDialog = true
            }
        }
    )

    if (showPermissionDialog) {
        PermissionDialog(StoragePermissionTextProvider(context),
            isPermanentlyDeclined = context.findActivity()
                .shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
                .not(),
            onDismiss = { showPermissionDialog = false },
            onOkClick = {
                storagePermissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                showPermissionDialog = false
            },
            onGoToAppSettingsClick = {
                openAppSettings(context)
                showPermissionDialog = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                storagePermissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = weather)
            Text(text = songName)
        }
    }
    Divider(color = Color.Gray)
}

fun getMusicFromStorage(
    launcher: (Intent) -> Unit
) {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "audio/*"
        addCategory(Intent.CATEGORY_OPENABLE)
    }
    launcher(Intent.createChooser(intent, "Select Audio"))
}


/**
 * 하단 취소, 저장 버튼
 *
 * @param modifier
 * @param onCancelClick 취소 클릭
 * @param onDoneClick 저장 클릭
 */
@Composable
private fun BottomButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit
) {

    val context = LocalContext.current

    var showPermissionDialog by remember { mutableStateOf(false) }

    val gpsPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast
                    .makeText(context, "Permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
            showPermissionDialog = !isGranted
        }
    )

    if (showPermissionDialog) {
        PermissionDialog(GPSPermissionTextProvider(context),
            isPermanentlyDeclined = !context.findActivity()
                .shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION),
            onDismiss = { showPermissionDialog = false },
            onOkClick = {
                gpsPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                showPermissionDialog = false
            },
            onGoToAppSettingsClick = {
                openAppSettings(context)
                showPermissionDialog = false
            }
        )
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = {
                onCancelClick()
            }
        ) {
            Text(
                text = stringResource(id = R.string.cancel),
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        TextButton(
            onClick = {
                gpsPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                AlarmHandler().setContext(context)
                /**
                 * Room에 저장, AlarmManager 등록
                 */
            }
        ) {
            Text(
                text = stringResource(id = R.string.save),
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}


private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}

