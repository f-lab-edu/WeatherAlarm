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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ysw.presentation.ui.theme.MyApplicationTheme

/**
 * Alarm activity
 * 알람 동작시 보여질 화면
 *
 */
class AlarmActivity : ComponentActivity() {

    //private val alarmViewModel: AlarmViewModel by viewModels()
    private var music: MediaPlayer? = null

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOn(this)
        setContent {
            MyApplicationTheme {
                AlarmScreen()
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

) {
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
            text = "02:22",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.heightIn(500.dp))

        Button(
            modifier = Modifier
                .width(200.dp),
            onClick = {
                AlarmActivity().finish()
            }) {
            Text(text = "Close")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        AlarmScreen()
    }
}

/**
 * Activity on
 *  화면이 켜지는 역할을 수행하는 함수
 *
 * @param activity
 */
private fun activityOn(activity: Activity) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
        activity.setTurnScreenOn(true)
        activity.setShowWhenLocked(true)
        val keyguardManager = activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        keyguardManager.requestDismissKeyguard(activity, null)
    } else {
        activity.window.addFlags(
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
    music?.setDataSource("music.mp3")
    music?.prepare()
    music?.start()
}