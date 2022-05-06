package eu.maxkim.boredombuster.activity.fake.datasource

import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.repository.ActivityRemoteDataSource
import eu.maxkim.boredombuster.model.Result

class FakeActivityRemoteDataSource : ActivityRemoteDataSource {

    var getActivityWasCalled = false
        private set

    override suspend fun getActivity(): Result<Activity> {
        getActivityWasCalled = true
        return Result.Success(activity1)
    }
}