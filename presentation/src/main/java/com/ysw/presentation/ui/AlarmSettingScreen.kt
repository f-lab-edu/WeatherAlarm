package com.ysw.presentation.ui

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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.TimeFormat

@Composable
fun AlarmSettingScreen(

) {
    Scaffold(
        bottomBar = {
            BottomButtons(
                onCancelClick = { /* 취소 버튼 클릭 시 동작 */ },
                onDoneClick = { /* 저장 버튼 클릭 시 동작 */ }
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

@Composable
private fun WheelTimerPickerView() {
    Box() {
        WheelTimePicker(
            modifier = Modifier.padding(16.dp),
            timeFormat = TimeFormat.AM_PM,
            size = DpSize(400.dp, 300.dp),
            textStyle = MaterialTheme.typography.displayLarge,
        ) {

        }
    }
    Divider(color = Color.Gray)
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun UIDayChipUsingFlowRow(
    modifier: Modifier = Modifier,
    list: List<String>,
    selectedList: SnapshotStateList<String>,
    onOptionSelected: (String) -> Unit
) {
    FlowRow(
    ) {
        list.forEach {
            FilterChip(
                modifier = modifier.padding(1.dp),
                selected = it in selectedList,
                onClick = { onOptionSelected(it) },
                label = {
                    Text(it, style = MaterialTheme.typography.bodySmall)
                }
            )
        }
    }

}

@Composable
private fun DayChipUI(
    modifier: Modifier = Modifier
) {
    val list = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var selectedList = remember {
        mutableStateListOf<String>()
    }
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        UIDayChipUsingFlowRow(list = list, selectedList = selectedList) {
            if (it in selectedList) {
                selectedList.remove(it)
            } else {
                selectedList.add(it)
            }
        }
    }
    Divider(color = Color.Gray)
}

@Composable
private fun SoundSliderUI(
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
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

@Composable
private fun SoundByWeatherView() {
    val dummySound =
        mapOf("맑음" to "노래제목", "비" to "노래제목", "눈" to "노래제목")
    dummySound.forEach { (weather, song) ->
        SetSoundByWeather(weather, song)
    }
}

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

@Composable
private fun BottomButtons(
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            onClick = onCancelClick
        ) {
            Text(text = "취소", color = Color.Black, style = MaterialTheme.typography.headlineMedium)
        }

        TextButton(
            onClick = onDoneClick
        ) {
            Text(text = "저장", color = Color.Black, style = MaterialTheme.typography.headlineMedium)
        }
    }
}