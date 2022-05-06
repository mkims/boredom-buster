package eu.maxkim.boredombuster.activity.repository

import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.model.Result

interface ActivityRemoteDataSource {
    suspend fun getActivity(): Result<Activity>
}