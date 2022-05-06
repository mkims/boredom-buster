package eu.maxkim.boredombuster.activity.ui.newactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.usecase.DeleteActivity
import eu.maxkim.boredombuster.model.Result
import eu.maxkim.boredombuster.activity.usecase.GetRandomActivity
import eu.maxkim.boredombuster.activity.usecase.IsActivitySaved
import eu.maxkim.boredombuster.activity.usecase.SaveActivity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewActivityViewModel @Inject constructor(
    private val getRandomActivity: GetRandomActivity,
    private val saveActivity: SaveActivity,
    private val deleteActivity: DeleteActivity,
    private val isActivitySaved: IsActivitySaved
): ViewModel() {

    private val _uiState = MutableStateFlow<NewActivityUiState>(NewActivityUiState.Loading)
    val uiState: StateFlow<NewActivityUiState> = _uiState

    private var loadingJob: Job? = null

    init {
        loadNewActivity()
    }

    fun loadNewActivity() {
        _uiState.value = NewActivityUiState.Loading

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            val newUiState = when (val activityResult = getRandomActivity()) {
                is Result.Success -> {
                    val isFavorite = isActivitySaved(activityResult.data.key)
                    NewActivityUiState.Success(activityResult.data, isFavorite)
                }
                is Result.Error -> {
                    activityResult.error
                        .takeUnless { it is CancellationException }
                        ?.let(NewActivityUiState::Error)
                        ?: NewActivityUiState.Loading
                }
            }

            _uiState.value = (newUiState)
        }
    }

    fun setIsFavorite(activity: Activity, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                saveActivity(activity)
            } else {
                deleteActivity(activity)
            }

            _uiState.value = NewActivityUiState.Success(activity, isFavorite)
        }
    }
}