package eu.maxkim.boredombuster.activity.repository

import androidx.lifecycle.LiveData
import eu.maxkim.boredombuster.activity.model.Activity

interface ActivityLocalDataSource {
    suspend fun saveActivity(activity: Activity)

    suspend fun deleteActivity(activity: Activity)

    suspend fun isActivitySaved(key: String): Boolean

    fun getActivityListLiveData(): LiveData<List<Activity>>
}