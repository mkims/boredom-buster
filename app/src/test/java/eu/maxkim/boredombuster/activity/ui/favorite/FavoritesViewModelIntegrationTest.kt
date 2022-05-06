package eu.maxkim.boredombuster.activity.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.activity2
import eu.maxkim.boredombuster.activity.framework.api.ActivityApiClient
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityLocalDataSourceImpl
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityRemoteDataSourceImpl
import eu.maxkim.boredombuster.activity.framework.db.ActivityDao
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.activity.repository.ActivityRepositoryImpl
import eu.maxkim.boredombuster.activity.usecase.DeleteActivityImpl
import eu.maxkim.boredombuster.activity.usecase.GetFavoriteActivitiesImpl
import eu.maxkim.boredombuster.util.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelIntegrationTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    private val activityListObserver: Observer<FavoritesUiState> = mock()

    @Captor
    private lateinit var activityListCaptor: ArgumentCaptor<FavoritesUiState>

    private val mockApiClient: ActivityApiClient = mock()
    private val mockActivityDao: ActivityDao = mock()

    private val remoteDataSource = ActivityRemoteDataSourceImpl(mockApiClient)
    private val localDataSource = ActivityLocalDataSourceImpl(mockActivityDao)

    private val activityRepository = ActivityRepositoryImpl(
        appScope = testScope,
        ioDispatcher = testDispatcher,
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )

    private val deleteActivity = DeleteActivityImpl(activityRepository)
    private val getFavoriteActivities = GetFavoriteActivitiesImpl(activityRepository)

    @Test
    fun `view model exposes activity list live data as ui state`() {
        // Arrange
        val liveDataToReturn = MutableLiveData<List<Activity>>()
            .apply { value = listOf(activity1, activity2) }

        val expectedList = listOf(activity1, activity2)

        whenever(mockActivityDao.getAll()).doReturn(liveDataToReturn)

        val viewModel = FavoritesViewModel(
            getFavoriteActivities,
            deleteActivity
        )

        // Act
        viewModel.uiStateLiveData.observeForever(activityListObserver)

        // Assert
        verify(activityListObserver, times(1)).onChanged(activityListCaptor.capture())
        assert(activityListCaptor.value is FavoritesUiState.List)

        val actualList = (activityListCaptor.value as FavoritesUiState.List).activityList
        assertEquals(actualList, expectedList)
    }

    @Test
    fun `deleting activity triggers the dao delete`() = runTest {
        // Arrange
        val viewModel = FavoritesViewModel(
            getFavoriteActivities,
            deleteActivity
        )

        // Act
        viewModel.deleteActivity(activity1)
        runCurrent()

        // Assert
        verify(mockActivityDao, times(1)).delete(activity1)
    }
}