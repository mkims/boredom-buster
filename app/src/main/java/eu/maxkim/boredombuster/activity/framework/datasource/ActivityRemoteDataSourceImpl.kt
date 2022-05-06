package eu.maxkim.boredombuster.activity.framework.datasource

import eu.maxkim.boredombuster.activity.framework.api.ActivityApiClient
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.repository.ActivityRemoteDataSource
import eu.maxkim.boredombuster.model.Result
import eu.maxkim.boredombuster.model.runCatching
import javax.inject.Inject

class ActivityRemoteDataSourceImpl @Inject constructor(
    private val activityApiClient: ActivityApiClient
) : ActivityRemoteDataSource {

    override suspend fun getActivity(): Result<Activity> {
        return runCatching {
            activityApiClient.getActivity()
        }
    }
}