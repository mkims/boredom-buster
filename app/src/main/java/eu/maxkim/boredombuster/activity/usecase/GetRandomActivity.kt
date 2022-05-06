package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.model.Result

interface GetRandomActivity {
    suspend operator fun invoke(): Result<Activity>
}