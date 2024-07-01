package com.ysw.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ysw.presentation.compose.AlarmUiState
import com.ysw.presentation.compose.AlarmViewModel
import com.ysw.presentation.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Alarm activity
 * 알람 동작시 보여질 화면
 *
 */
@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {


    private var music: MediaPlayer? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOn()
        setContent {
            MyApplicationTheme {
                val viewModel: AlarmViewModel = hiltViewModel<AlarmViewModel>()
                val uiState by viewModel.uiState.collectAsState()
                AlarmScreen(
                    closeAction = {this@AlarmActivity.finish()},
                    state = uiState
                )
            }
        }
        /*
        playMusicByWeather(
            weather = "맑음",
            music = music
        )
        */
    }

    override fun onDestroy() {
        music?.release()
        music = null
        super.onDestroy()
    }
}


/**
 * Alarm screen
 * 화면 UI
 *
 */
@Composable
fun AlarmScreen(
    closeAction: () -> Unit,
    state : AlarmUiState,

) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.alarm),
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.heightIn(50.dp))

        Text(
            text = state.time.toString(),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.heightIn(500.dp))

        Button(
            modifier = Modifier
                .width(200.dp),
            onClick = {
                closeAction()
            }) {
            Text(text = stringResource(R.string.close))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        AlarmScreen(closeAction = {

        })

    }
}

/**
 * Activity on
 *  화면이 켜지는 역할을 수행하는 함수
 *
 * @param activity
 */
private fun Activity.activityOn() {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
        setTurnScreenOn(true)
        setShowWhenLocked(true)
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(this, null)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }
}

/**
 * Play music by weather
 * 날씨에 따른 음악을 실행하는 함수
 * @param weather
 * @param music
 */
private fun playMusicByWeather(
    weather: String,
    music: MediaPlayer?
) {
    music?.setDataSource("music.mp3 uri")
    music?.prepare()
    music?.start()
}