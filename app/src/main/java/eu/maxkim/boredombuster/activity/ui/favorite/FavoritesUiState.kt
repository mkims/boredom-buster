package eu.maxkim.boredombuster.activity.ui.favorite

import eu.maxkim.boredombuster.activity.model.Activity

sealed class FavoritesUiState {
    data class List(val activityList: kotlin.collections.List<Activity>): FavoritesUiState()
    object Empty : FavoritesUiState()
}