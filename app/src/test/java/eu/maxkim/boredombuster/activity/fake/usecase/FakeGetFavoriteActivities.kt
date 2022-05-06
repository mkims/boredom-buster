package eu.maxkim.boredombuster.activity.fake.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.activity2
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.usecase.GetFavoriteActivities

class FakeGetFavoriteActivities : GetFavoriteActivities {

    override fun invoke(): LiveData<List<Activity>> {
        return MutableLiveData<List<Activity>>().apply {
            value = listOf(activity1, activity2)
        }
    }
}