package com.ysw.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


/**
 * Alarm list screen
 *
 * @param navController
 */
@Composable
fun AlarmListScreen(
    navController: NavController,
    alarmUiState: AlarmUiState,
    setAlarmOn : (Boolean) -> Unit
) {

    val dummyList: List<DummyDataClass> = listOf(
        DummyDataClass(
            amPm = "오전",
            hour = 12,
            minute = 12,
            activeDays = "월, 화, 수, 목, 금, 토",
            isOn = true
        ),
        DummyDataClass(
            amPm = "오후",
            hour = 12,
            minute = 30,
            activeDays = "월, 화, 토",
            isOn = false
        ),
        DummyDataClass(
            amPm = "오전",
            hour = 12,
            minute = 45,
            activeDays = "월, 토",
            isOn = true
        ),
    )

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("AlarmSettingScreen")
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "알람 추가"
                )
            }
        }
    ) { innerPadding ->
        AlarmListColumn(
            alarmData = dummyList,
            paddingValues = innerPadding,
            onAlarmItemClick = {navController.navigate("AlarmSettingScreen")},
            isOn = alarmUiState.isOn,
            changeAlarmOn = { setAlarmOn(it) }
        )
    }

}

/**
 * Alarm list column
 *
 * @param alarmData
 * @param paddingValues
 * @param navController
 */
@Composable
private fun AlarmListColumn(
    alarmData: List<DummyDataClass> = emptyList(),
    paddingValues: PaddingValues = PaddingValues(),
    onAlarmItemClick: () -> Unit,
    isOn: Boolean,
    changeAlarmOn: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(alarmData) { alarm ->
            AlarmItem(
                item = alarm,
                isOn = isOn,
                changeAlarmOn = { changeAlarmOn(it) }
            ) {
                onAlarmItemClick()
            }
        }
    }
}

/**
 * Alarm LazyColumn item
 *
 * @param item
 * @param onClick
 * @receiver
 */
@Composable
private fun AlarmItem(
    item: DummyDataClass,
    isOn: Boolean,
    changeAlarmOn: (Boolean) -> Unit,
    onClick: () -> Unit = {}
) {
    var checked by remember { mutableStateOf(item.isOn) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(10.dp),
    ) {
        Row(
            Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = item.amPm, fontSize = 15.sp)
            Text(text = "${item.hour}:${item.minute}", fontSize = 30.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = item.activeDays, fontSize = 10.sp)
            Switch(checked = isOn, onCheckedChange = {
                changeAlarmOn(it)
                // TODO : 알람을 끈 상태로 변경하는 로직
            })
        }
    }
}

/**
 * Dummy Data Class
 *
 */
data class DummyDataClass(
    val amPm: String,
    val hour: Int,
    val minute: Int,
    val activeDays: String,
    val isOn: Boolean,
)
