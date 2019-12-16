package com.example.catchtime.backgroundService.utils;
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Neo on 2018-12-02.
 * http://m3n.ir/
 */
class Utils {
    companion object {

        fun formatInterval(l: Long): String {
            val hr = TimeUnit.MILLISECONDS.toHours(l)
            val min = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr))
            val sec = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min))
//        val ms = TimeUnit.MILLISECONDS.toMillis(
//            l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec)
//        )
            return String.format("%02d:%02d:%02d", hr, min, sec)
        }


        val times: MutableList<Long> = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1)
        )!!
        private val timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second")

        fun toDuration(duration: Long): String {

            val res = StringBuffer()
            for (i in 0 until times.size) {
                val current = times[i]
                val temp = duration / current
                if (temp > 0) {
                    res.append(temp).append(" ").append(timesString[i]).append(if (temp != 1L) "s" else "")
                        .append(" ago")
                    break
                }
            }
            return if ("" == res.toString())
                "just now!"
            else
                res.toString()
        }

        fun isMyServiceRunning(c: Context, serviceClass: Class<*>): Boolean {
            val manager = c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            for (service in manager!!.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
            return false
        }


        fun playSound(c: Context, rawID: Int) {
            val am = c.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            when (am.ringerMode) {
                //                case AudioManager.RINGER_MODE_SILENT:
                //                    break;
                //                case AudioManager.RINGER_MODE_VIBRATE:
                //                    break;
                AudioManager.RINGER_MODE_NORMAL -> {
                    val mp = MediaPlayer.create(c, rawID)
                    mp.start()
                }
            }
        }


        @SuppressLint("ShowToast")
        fun toast(c: Context, s: String) {
            Toast.makeText(c, s, Toast.LENGTH_SHORT).show()
        }

        fun log(s: String) {
            Log.wtf("MainActivity", s)
        }
    }
}