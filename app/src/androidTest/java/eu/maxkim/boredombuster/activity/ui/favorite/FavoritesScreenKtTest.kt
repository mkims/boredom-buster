package eu.maxkim.boredombuster.activity.ui.favorite

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import eu.maxkim.boredombuster.R
import eu.maxkim.boredombuster.activity.androidActivity1
import eu.maxkim.boredombuster.activity.androidActivity2
import eu.maxkim.boredombuster.activity.model.Activity
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

class FavoritesScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun activityNameDisplayedOnAListCard() {
        composeTestRule.setContent {
            ActivityCard(
                modifier = Modifier.fillMaxWidth(),
                activity = androidActivity1,
                onDeleteClick = { }
            )
        }

        composeTestRule.onNodeWithText(androidActivity1.name)
            .assertIsDisplayed()
    }

    @Test
    fun onDeleteClickCallbackIsTriggered() {
        val onDeleteClick: (Activity) -> Unit = mock()

        composeTestRule.setContent {
            ActivityCard(
                modifier = Modifier.fillMaxWidth(),
                activity = androidActivity1,
                onDeleteClick = onDeleteClick
            )
        }

        val contentDescription = ApplicationProvider.getApplicationContext<Context>()
            .getString(R.string.cd_delete_activity)

        composeTestRule.onNodeWithContentDescription(contentDescription)
            .performClick()

        Mockito.verify(onDeleteClick, times(1)).invoke(androidActivity1)
    }

    @Test
    fun activityListDisplaysAllActivities() {
        val activityList = listOf(androidActivity1, androidActivity2)
        composeTestRule.setContent {
            ActivityList(
                modifier = Modifier.fillMaxWidth(),
                activityList = activityList,
                onDeleteClick = { }
            )
        }

        activityList.forEach { activity ->
            composeTestRule.onNodeWithText(activity.name)
                .assertIsDisplayed()
        }
    }
}