package eu.maxkim.boredombuster.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import eu.maxkim.boredombuster.R
import eu.maxkim.boredombuster.Tags
import eu.maxkim.boredombuster.activity.framework.datasource.successfulAndroidResponse1
import eu.maxkim.boredombuster.activity.framework.datasource.successfulAndroidResponse2
import eu.maxkim.boredombuster.activity.responseAndroidActivity1
import eu.maxkim.boredombuster.activity.responseAndroidActivity2
import eu.maxkim.boredombuster.framework.AppDatabase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class BoredomBusterAppTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var database: AppDatabase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        database.close()
    }

    @Test
    fun refreshingSavingAndDeletingWorksCorrectly() {
        enqueueActivityResponse(successfulAndroidResponse1)
        waitUntilVisibleWithText(responseAndroidActivity1.name)

        enqueueActivityResponse(successfulAndroidResponse2)
        refreshActivity()
        waitUntilVisibleWithText(responseAndroidActivity2.name)

        saveAsFavorite()
        navigateToFavorites()

        composeTestRule.onNodeWithText(responseAndroidActivity2.name)
            .assertIsDisplayed()

        deleteFromFavorites()

        composeTestRule.onNodeWithText(responseAndroidActivity2.name)
            .assertDoesNotExist()

        val noActivitiesMessage = ApplicationProvider.getApplicationContext<Context>()
            .getString(R.string.message_empty_activity_list)

        composeTestRule.onNodeWithText(noActivitiesMessage)
            .assertIsDisplayed()
    }

    private fun saveAsFavorite() {
        clickOnNodeWithContentDescription(R.string.cd_save_activity)
    }

    private fun deleteFromFavorites() {
        clickOnNodeWithContentDescription(R.string.cd_delete_activity)
    }

    private fun refreshActivity() {
        clickOnNodeWithContentDescription(R.string.cd_refresh_activity)
    }

    private fun navigateToFavorites() {
        composeTestRule.onNodeWithTag(Tags.FavoritesTab)
            .performClick()
    }

    private fun navigateToActivity() {
        composeTestRule.onNodeWithTag(Tags.ActivityTab)
            .performClick()
    }

    private fun waitUntilVisibleWithText(text: String) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithText(text)
                .fetchSemanticsNodes().size == 1
        }
    }

    private fun clickOnNodeWithContentDescription(@StringRes cdRes: Int) {
        val contentDescription = ApplicationProvider.getApplicationContext<Context>()
            .getString(cdRes)

        composeTestRule.onNodeWithContentDescription(contentDescription)
            .performClick()
    }

    private fun enqueueActivityResponse(activityJson: String) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody(activityJson)
        )
    }
}

