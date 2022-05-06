package eu.maxkim.boredombuster.activity.ui.newactivity

import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.framework.api.ActivityApiClient
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityLocalDataSourceImpl
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityRemoteDataSourceImpl
import eu.maxkim.boredombuster.activity.framework.db.ActivityDao
import eu.maxkim.boredombuster.activity.repository.ActivityRepositoryImpl
import eu.maxkim.boredombuster.activity.usecase.*
import eu.maxkim.boredombuster.util.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class NewActivityViewModelIntegrationTest {

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

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

    private val getRandomActivity = GetRandomActivityImpl(activityRepository)
    private val saveActivity = SaveActivityImpl(activityRepository)
    private val deleteActivity = DeleteActivityImpl(activityRepository)
    private val isActivitySaved = IsActivitySavedImpl(activityRepository)

    @Test
    fun `calling loadNewActivity() triggers the api client`() = runTest {
        // Arrange
        val viewModel = NewActivityViewModel(
            getRandomActivity,
            saveActivity,
            deleteActivity,
            isActivitySaved
        )

        // Act
        viewModel.loadNewActivity()
        runCurrent()

        // Assert
        verify(mockApiClient, times(1)).getActivity()
    }

    @Test
    fun `calling save activity triggers the dao insert`() = runTest {
        // Arrange
        val viewModel = NewActivityViewModel(
            getRandomActivity,
            saveActivity,
            deleteActivity,
            isActivitySaved
        )

        // Act
        viewModel.setIsFavorite(activity1, true)
        runCurrent()

        // Assert
        verify(mockActivityDao, times(1)).insert(activity1)
    }

    @Test
    fun `calling delete activity triggers the dao delete`() = runTest {
        // Arrange
        val viewModel = NewActivityViewModel(
            getRandomActivity,
            saveActivity,
            deleteActivity,
            isActivitySaved
        )

        // Act
        viewModel.setIsFavorite(activity1, false)
        runCurrent()

        // Assert
        verify(mockActivityDao, times(1)).delete(activity1)
    }

    @Test
    fun `loading activity triggers the dao exists`() = runTest {
        // Arrange
        whenever(mockActivityDao.isActivitySaved(activity1.key)).doReturn(true)
        whenever(mockApiClient.getActivity()).doReturn(activity1)

        val viewModel = NewActivityViewModel(
            getRandomActivity,
            saveActivity,
            deleteActivity,
            isActivitySaved
        )

        //Act
        runCurrent()

        // Assert
        val actualState = viewModel.uiState.value as NewActivityUiState.Success
        assertEquals(actualState.isFavorite, true)
    }
}