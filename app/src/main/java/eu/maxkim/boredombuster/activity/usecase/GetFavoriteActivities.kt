package eu.maxkim.boredombuster.activity.usecase

import androidx.lifecycle.LiveData
import eu.maxkim.boredombuster.activity.model.Activity

interface GetFavoriteActivities {
    operator fun invoke(): LiveData<List<Activity>>
}