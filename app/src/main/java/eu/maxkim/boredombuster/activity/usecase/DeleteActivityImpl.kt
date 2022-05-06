package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity
import javax.inject.Inject

class DeleteActivityImpl @Inject constructor(
    private val activityRepository: ActivityRepository
) : DeleteActivity {

    override suspend fun invoke(activity: Activity) {
        activityRepository.deleteActivity(activity)
    }
}