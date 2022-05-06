package eu.maxkim.boredombuster.activity.repository

import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.fake.datasource.FakeActivityLocalDataSource
import eu.maxkim.boredombuster.activity.fake.datasource.FakeActivityRemoteDataSource
import eu.maxkim.boredombuster.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ActivityRepositoryImplTest {

    @Test
    fun `getNewActivity() returns a result after switching the context`() = runTest {
        // Arrange
        val activityRepository = ActivityRepositoryImpl(
            appScope = this,
            ioDispatcher = StandardTestDispatcher(testScheduler),
            remoteDataSource = FakeActivityRemoteDataSource(),
            localDataSource = FakeActivityLocalDataSource()
        )

        val expectedActivity = activity1

        // Act
        val result = activityRepository.getNewActivity()

        // Assert
        assert(result is Result.Success)
        assertEquals((result as Result.Success).data, expectedActivity)
    }

    @Test
    fun `getNewActivityInANewCoroutine correctly calls remote data source`() = runTest {
        // Arrange
        val fakeRemoteRepository = FakeActivityRemoteDataSource()
        val activityRepository = ActivityRepositoryImpl(
            appScope = this,
            ioDispatcher = StandardTestDispatcher(testScheduler),
            remoteDataSource = fakeRemoteRepository,
            localDataSource = FakeActivityLocalDataSource()
        )

        // Act
        activityRepository.getNewActivityInANewCoroutine()
        advanceUntilIdle()

        // Assert
        assert(fakeRemoteRepository.getActivityWasCalled)
    }
}