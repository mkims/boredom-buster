package eu.maxkim.boredombuster.activity.usecase

import javax.inject.Inject

class IsActivitySavedImpl @Inject constructor(
    private val activityRepository: ActivityRepository
) : IsActivitySaved {

    override suspend fun invoke(key: String): Boolean {
        return activityRepository.isActivitySaved(key)
    }
}