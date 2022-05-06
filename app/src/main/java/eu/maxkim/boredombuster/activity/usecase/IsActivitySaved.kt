package eu.maxkim.boredombuster.activity.usecase

interface IsActivitySaved {
    suspend operator fun invoke(key: String): Boolean
}