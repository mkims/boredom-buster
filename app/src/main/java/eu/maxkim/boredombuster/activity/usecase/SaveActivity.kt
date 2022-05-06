package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity

interface SaveActivity {
    suspend operator fun invoke(activity: Activity)
}