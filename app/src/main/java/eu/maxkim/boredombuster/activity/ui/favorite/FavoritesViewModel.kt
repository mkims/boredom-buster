package eu.maxkim.boredombuster.activity.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.usecase.DeleteActivity
import eu.maxkim.boredombuster.activity.usecase.GetFavoriteActivities
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteActivities: GetFavoriteActivities,
    private val deleteActivity: DeleteActivity
) : ViewModel() {

    // We are using live data instead of state flow just to
    // include live data tests as well
    val uiStateLiveData: LiveData<FavoritesUiState> = getFavoriteActivities()
        .map { list ->
            if (list.isEmpty()) {
                FavoritesUiState.Empty
            } else {
                FavoritesUiState.List(list)
            }
        }

    fun deleteActivity(activity: Activity) {
        viewModelScope.launch {
            deleteActivity.invoke(activity)
        }
    }
}