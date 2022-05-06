package eu.maxkim.boredombuster.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val path: String,
    val icon: ImageVector? = null
) {
    companion object {
        fun fromString(route: String): Destination {
            return when (route) {
                Activity.path -> Activity
                Favorites.path -> Favorites
                else -> Home
            }
        }
    }

    object Home : Destination("home")
    object Activity : Destination("activity", Icons.Filled.Refresh)
    object Favorites : Destination("favorites", Icons.Filled.Star)

    val title = path.replaceFirstChar(Char::uppercase)
}