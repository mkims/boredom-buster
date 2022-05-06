package eu.maxkim.boredombuster.activity.fake.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.repository.ActivityLocalDataSource

class FakeActivityLocalDataSource : ActivityLocalDataSource {

    override suspend fun saveActivity(activity: Activity) {
        // Save
    }

    override suspend fun deleteActivity(activity: Activity) {
        // Delete
    }

    override suspend fun isActivitySaved(key: String): Boolean {
        return false
    }

    override fun getActivityListLiveData(): LiveData<List<Activity>> {
        return MutableLiveData()
    }
}