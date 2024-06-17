package com.ysw.presentation.compose

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat
import com.ysw.presentation.R
import java.time.LocalTime

/**
 * 알람 추가, 설정 화면
 *
 * @param navController
 */
@Composable
fun AlarmSettingScreen(
    onDoneClick: () -> Unit
) {

    val viewModel = AlarmViewModel()

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
                WheelTimerPickerView()
                DayChipUI()
                SoundSliderUI()
                SoundByWeatherView()
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

) {
    val context = LocalContext.current

    Box() {
        WheelTimePicker(
            modifier = Modifier.padding(16.dp),
            timeFormat = TimeFormat.AM_PM,
            size = DpSize(400.dp, 300.dp),
            textStyle = MaterialTheme.typography.displayLarge,
            startTime = LocalTime.now(),
        ) { localTime ->
            Toast.makeText(context, localTime.toString(), Toast.LENGTH_SHORT).show()
        }
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
    selectedList: SnapshotStateList<String>,
    onOptionSelected: (String) -> Unit
) {
    FlowRow(
    ) {
        list.forEach {
            FilterChip(
                modifier = Modifier.padding(1.dp),
                selected = it in selectedList,
                onClick = { onOptionSelected(it) },
                label = {
                    Text(it, style = MaterialTheme.typography.bodySmall)
                }
            )
        }
    }

}

/**
 * 알람이 울릴 날짜를 선택하는 UI
 *
 */
@Composable
private fun DayChipUI(

) {
    val dummyList = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var selectedList = remember {
        mutableStateListOf<String>()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        UIDayChipUsingFlowRow(list = dummyList, selectedList = selectedList) {
            if (it in selectedList) {
                selectedList.remove(it)
            } else {
                selectedList.add(it)
            }
        }
    }
    Divider(color = Color.Gray)
}

/**
 * 음량 조절 View
 *
 */
@Composable
private fun SoundSliderUI(

) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

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
                value = sliderPosition,
                onValueChange = { sliderPosition = it })
        }
    }
    Divider(color = Color.Gray)
}

/**
 *  날씨 별 음악 View
 *
 */
@Composable
private fun SoundByWeatherView() {
    val dummySound =
        mapOf("맑음" to "맑음 노래제목", "비" to "비 노래제목", "눈" to "눈 노래제목")
    dummySound.forEach { (weather, song) ->
        SetSoundByWeather(weather, song)
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
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // TODO 음악파일을 가져오는 코드 , 노래제목 변경
            },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = weather)
            Text(text = song)
        }
    }
    Divider(color = Color.Gray)
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = {
                onCancelClick
            }
        ) {
            Text(
                text = stringResource(id = R.string.save),
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        TextButton(
            onClick = {
                onDoneClick
                /*
                alarmHandler.setAlarm(
                )
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