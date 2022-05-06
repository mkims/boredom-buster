package eu.maxkim.boredombuster.activity.usecase

import androidx.lifecycle.LiveData
import eu.maxkim.boredombuster.activity.model.Activity
import javax.inject.Inject

class GetFavoriteActivitiesImpl @Inject constructor(
    private val activityRepository: ActivityRepository
) : GetFavoriteActivities {

    override fun invoke(): LiveData<List<Activity>> {
        return activityRepository.getActivityListLiveData()
    }
}