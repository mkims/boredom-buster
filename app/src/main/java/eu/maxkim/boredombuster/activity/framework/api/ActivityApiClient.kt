package eu.maxkim.boredombuster.activity.framework.api

import eu.maxkim.boredombuster.activity.model.Activity
import retrofit2.http.GET

interface ActivityApiClient {
    @GET("/api/activity")
    suspend fun getActivity(): Activity
}