package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity
import javax.inject.Inject

class SaveActivityImpl @Inject constructor(
    private val activityRepository: ActivityRepository
) : SaveActivity {

    override suspend fun invoke(activity: Activity) {
        activityRepository.saveActivity(activity)
    }
}