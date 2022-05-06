package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity

interface DeleteActivity {
    suspend operator fun invoke(activity: Activity)
}