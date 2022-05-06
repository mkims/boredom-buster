package eu.maxkim.boredombuster.ui

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import eu.maxkim.boredombuster.Tags
import eu.maxkim.boredombuster.model.Destination
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class BottomNavigationBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun currentDestinationIsSelected() {
        composeTestRule.setContent {
            BottomNavigationBar(
                currentDestination = Destination.Favorites,
                onNavigate = { }
            )
        }

        composeTestRule.onNodeWithTag(Tags.FavoritesTab)
            .assertIsSelected()
    }

    @Test
    fun onNavigationClickCallbackIsTriggered() {
        val onNavigate: (Destination) -> Unit = mock()

        composeTestRule.setContent {
            BottomNavigationBar(
                currentDestination = Destination.Activity,
                onNavigate = onNavigate
            )
        }

        composeTestRule.onNodeWithTag(Tags.FavoritesTab)
            .performClick()

        verify(onNavigate, times(1)).invoke(Destination.Favorites)
    }
}