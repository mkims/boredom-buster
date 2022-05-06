package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.model.Result
import javax.inject.Inject

class GetRandomActivityImpl @Inject constructor(
    private val activityRepository: ActivityRepository
) : GetRandomActivity {

    override suspend fun invoke(): Result<Activity> {
        return activityRepository.getNewActivity()
    }
}