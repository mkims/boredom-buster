package eu.maxkim.boredombuster.activity.fake.usecase

import eu.maxkim.boredombuster.activity.usecase.IsActivitySaved

class FakeIsActivitySaved(
    private val isActivitySaved: Boolean = false
) : IsActivitySaved {

    override suspend fun invoke(key: String): Boolean {
        return isActivitySaved
    }
}