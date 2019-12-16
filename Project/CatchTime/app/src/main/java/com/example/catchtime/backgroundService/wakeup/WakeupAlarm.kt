package com.example.catchtime.backgroundService.wakeup
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.app.AlarmManager
import android.app.PendingIntent
import com.example.catchtime.backgroundService.Setting
import com.example.catchtime.backgroundService.utils.Utils
import com.example.catchtime.backgroundService.wakeup.KeepAliveHandler
import kotlin.math.log


/**
 * Created
 * http://m3n.ir/
 */
class WakeupAlarm : BroadcastReceiver() {

    /**
     * On Receive Broadcast
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(WakeupAlarm::class.java.simpleName,"WakeupAlarm WORKING!!!")
        Log.e("MainAxtivity","wakeupalarm 执行receive")
        if (context != null) {
            Log.e("MainAxtivity","wakeupalarm 执行receive1")
            Setting(context).incrementCountCallJobByAlarm()
            KeepAliveHandler.wakeUpTheService(context)
        }
    }

    companion object {
        fun setJob(context: Context){
//            Utils.log( "wakeupalarm 开始执行!")

            val myAlarm = Intent(context, WakeupAlarm::class.java)
            val recurringAlarm = PendingIntent.getBroadcast(context, 0, myAlarm, PendingIntent.FLAG_CANCEL_CURRENT)
            val alarms = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarms.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                recurringAlarm
            )
        }

        fun removeJob(context: Context){
            val myAlarm = Intent(context, WakeupAlarm::class.java)
            val recurringAlarm =
                PendingIntent.getBroadcast(context, 0, myAlarm, PendingIntent.FLAG_CANCEL_CURRENT)
            val alarms = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarms.cancel(recurringAlarm)
        }
    }

}