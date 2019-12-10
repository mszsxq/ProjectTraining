package com.example.catchtime.backgroundService;
import android.content.Context
import com.example.catchtime.backgroundService.utils.AppSettings


const val S_START_TIME = "s_start_time"
const val S_SERVICE_WORKING_TIME = "s_service_working_time"
const val S_SERVICE_LAST_ACTIVE_TIME = "s_service_last_active_time"
const val S_JOB_LAST_ACTIVE_TIME = "s_job_last_active_time"
const val S_CALL_COUNT_ALARM = "s_call_count_alarm"
const val S_CALL_COUNT_JOB_SERVICE = "s_call_count_job_service"

/**
 * Created by Neo on 2018-12-03.
 * http://m3n.ir/
 */
class Setting(context: Context) {

    private var appSetting: AppSettings = AppSettings(context)


    fun setStartTime(time: Long) {
        appSetting.setLong(S_START_TIME, time)
    }

    fun resetStartTime() {
        appSetting.setLong(S_START_TIME, 0)
    }

    fun getStartTime() : Long = appSetting.getLong(S_START_TIME)

    fun isStarted() : Boolean {
        if(appSetting.getLong(S_START_TIME)==0.toLong()) {
            return false
        }
        return true
    }

    fun incrementServiceWorkingTime(time: Long) {
        appSetting.setLong(S_SERVICE_WORKING_TIME, appSetting.getLong(S_SERVICE_WORKING_TIME) + time)
    }

    fun getServiceWorkingTime() : Long {
        return appSetting.getLong(S_SERVICE_WORKING_TIME)
    }

    fun resetServiceWorkingTime() {
        appSetting.setLong(S_SERVICE_WORKING_TIME, 0)
    }

    fun updateServiceLastActiveTime() {
        appSetting.setLong(S_SERVICE_LAST_ACTIVE_TIME, System.currentTimeMillis())
    }

    fun getServiceLastActiveTime() : Long {
        return appSetting.getLong(S_SERVICE_LAST_ACTIVE_TIME)
    }


    fun updateJobLastActiveTime() {
        appSetting.setLong(S_JOB_LAST_ACTIVE_TIME, System.currentTimeMillis())
    }

    fun getJobLastActiveTime() : Long {
        return appSetting.getLong(S_JOB_LAST_ACTIVE_TIME)
    }

    fun incrementCountCallJobByAlarm() {
        appSetting.setInt(S_CALL_COUNT_ALARM, appSetting.getInt(S_CALL_COUNT_ALARM) + 1)
    }

    fun getCountCallJobByAlarm() : Int {
        return appSetting.getInt(S_CALL_COUNT_ALARM)
    }

    fun incrementCountCallJobByJobService() {
        appSetting.setInt(S_CALL_COUNT_JOB_SERVICE, appSetting.getInt(S_CALL_COUNT_JOB_SERVICE) + 1)
    }

    fun getCountCallJobByJobService() : Int {
        return appSetting.getInt(S_CALL_COUNT_JOB_SERVICE)
    }

    fun resetAll(){
        appSetting.resetAll()
    }

}