package com.example.catchtime.backgroundService.wakeup;
import android.annotation.TargetApi
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.app.job.JobInfo
import android.content.ComponentName
import android.app.job.JobScheduler
import android.content.Context
import com.example.catchtime.backgroundService.Setting
import com.example.catchtime.backgroundService.utils.Utils


const val JOB_ID = 123
const val JOB_PERIOD : Long = 1000 * 60 * 15

/**
 * Created by Neo on 2018-12-04.
 * http://m3n.ir/
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class WakeupJobService : JobService() {

    // Called by the Android system when it's time to run the job
    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Utils.log( "WakeupJobService STARTED ! ! !")

        Setting(this).incrementCountCallJobByJobService()
        KeepAliveHandler.wakeUpTheService(this)

        jobFinished(jobParameters, true)

        return false
    }

    // Called if the job was cancelled before being finished
    override fun onStopJob(jobParameters: JobParameters): Boolean {
        Utils.log( "WakeupJobService STOPPED!!")
        jobFinished(jobParameters, true)
        return true
    }

    companion object {

        fun setJob(context: Context){
            val componentName = ComponentName(context, WakeupJobService::class.java)
            val jobInfo = JobInfo.Builder(JOB_ID, componentName).setPersisted(true).setPeriodic(JOB_PERIOD)
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            val resultCode = jobScheduler.schedule(jobInfo.build())

            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Utils.log( "Job scheduled!")
            } else {
                Utils.log( "WakeupJobService not scheduled")
            }
        }

        fun removeJob(context: Context){
            val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(JOB_ID)
            Utils.log( "Job canceled!")
        }

    }

}
