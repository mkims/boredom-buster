package eu.maxkim.boredombuster.activity.fake.usecase

import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.usecase.SaveActivity

class FakeSaveActivity : SaveActivity {

    var wasCalled = false
        private set

    override suspend fun invoke(activity: Activity) {
        wasCalled = true
    }
}