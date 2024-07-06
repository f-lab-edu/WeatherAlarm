package com.ysw.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ysw.presentation.utilities.AlarmScreen
import java.time.LocalTime


/**
 * Alarm list screen
 *
 * @param navController
 */
@Composable
fun AlarmListScreen(
    navController: NavController,
    alarmUiState: List<AlarmListUi>,
    setOnOffAlarm : (Boolean, LocalTime) -> Unit,
    deleteAlarm : (LocalTime) -> Unit,
) {

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("${AlarmScreen.ALARM_SETTING.name}/null")
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
            alarmData = alarmUiState,
            paddingValues = innerPadding,
            onAlarmItemClick = {navController.navigate( "${AlarmScreen.ALARM_SETTING.name}/${it}")},
            setOnOffAlarm = { isOn, time -> setOnOffAlarm(isOn, time) },
            deleteAlarm = {deleteAlarm(it)}
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
    alarmData: List<AlarmListUi>,
    paddingValues: PaddingValues,
    onAlarmItemClick: (LocalTime) -> Unit,
    setOnOffAlarm: (Boolean, LocalTime) -> Unit,
    deleteAlarm: (LocalTime) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items = alarmData, key = {it.time}) { alarm ->
            AlarmItem(
                alarmData = alarm,
                onItemClick = { onAlarmItemClick(it) },
                setOnOffAlarm = { isOn, time -> setOnOffAlarm(isOn, time) },
            ){
                deleteAlarm(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmItem(
    alarmData: AlarmListUi,
    onItemClick: (LocalTime) -> Unit = {},
    setOnOffAlarm: (Boolean, LocalTime) -> Unit,
    onDismissedToDelete: (LocalTime) -> Unit,
) {

    val dismissState = rememberDismissState(
        confirmValueChange = { dismissedValue ->
            if (dismissedValue == DismissValue.DismissedToEnd) {
                onDismissedToDelete(alarmData.time)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismiss(
        modifier = Modifier.clip(
            RoundedCornerShape(10.dp)
        ),
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {
            DeleteBackGround(dismissState)
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onItemClick(alarmData.time)
                    }
                    .padding(10.dp),
            ) {
                Row(
                    Modifier
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "${alarmData.time}", fontSize = 30.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = alarmData.alarmList.joinToString(), fontSize = 10.sp)
                    Switch(checked = alarmData.isOn, onCheckedChange = {
                        setOnOffAlarm(it, alarmData.time)
                    })
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackGround(
    swipeDismissState: DismissState
) {

    val color = if (swipeDismissState.dismissDirection == DismissDirection.StartToEnd) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
        ,
        contentAlignment = Alignment.CenterStart,
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }

}
