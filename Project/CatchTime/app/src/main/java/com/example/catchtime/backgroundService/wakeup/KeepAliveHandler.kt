package com.example.catchtime.backgroundService.wakeup;

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.catchtime.backgroundService.MyService
import com.example.catchtime.backgroundService.Setting
import com.example.catchtime.backgroundService.utils.Utils


/**
 * Created
 * http://m3n.ir/
 */
class KeepAliveHandler {


    companion object {

        /**
         * WAKEUP THE SERVICE
         */
        fun wakeUpTheService(context: Context){
            if(!Utils.isMyServiceRunning(context, MyService::class.java)){
                context.startService(Intent(context, MyService::class.java))
            }

            Setting(context).updateJobLastActiveTime()
//            toast(context,"KeepAliveHandler WORKING!!!")
//            Utils.playSound(context, R.raw.relentless)
        }

        fun setJob(context: Context){
//            Log.e("LocationService","执行hander setjob 之间")
            WakeupAlarm.setJob(context)
//            WakeupAlarm.Companion.setJob(context)
//            Log.e("LocationService","执行hander setjob 之中")
            WakeupJobService.setJob(context)
//            WakeupJobService.Companion.setJob(context)
//            Log.e("LocationService","执行WakeupJobService")
        }

//        fun setJob(){
//
//        }
        fun removeJob(context: Context){
            WakeupAlarm.removeJob(context)
            WakeupJobService.removeJob(context)
        }
    }

}