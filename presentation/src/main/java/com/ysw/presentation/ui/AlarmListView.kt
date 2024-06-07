package com.ysw.presentation.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ysw.presentation.ui.theme.MyApplicationTheme

@Composable
internal fun AlarmListView(
    modifier: Modifier = Modifier,
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
                onClick = { /*TODO : 알람 추가로 이동하기 */ },
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
            paddingValues = innerPadding
        )
    }


}


@Composable
private fun AlarmListColumn(
    modifier: Modifier = Modifier,
    alarmData: List<DummyDataClass> = emptyList(),
    paddingValues: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(alarmData) { alarm -> SetAlarmItem(alarm) }
    }

}

@Composable
private fun SetAlarmItem(
    item: DummyDataClass
) {
    var checked by remember { mutableStateOf(item.isOn) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            Switch(checked = checked, onCheckedChange = {
                checked = it
                // TODO : 알람을 끈 상태로 변경하는 로직
            })
        }
    }
}

@Preview
@Composable
fun PreAlarmListView(
) {
    MyApplicationTheme {
        AlarmListView()
    }
}


data class DummyDataClass(
    val amPm: String,
    val hour: Int,
    val minute: Int,
    val activeDays: String,
    val isOn: Boolean,
)
