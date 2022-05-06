package eu.maxkim.boredombuster.activity.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import eu.maxkim.boredombuster.activity.activity1
import eu.maxkim.boredombuster.activity.activity2
import eu.maxkim.boredombuster.activity.model.Activity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetFavoriteActivitiesImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockActivityRepository: ActivityRepository = mock()

    private val activityListObserver: Observer<List<Activity>> = mock()

    @Captor
    private lateinit var activityListCaptor: ArgumentCaptor<List<Activity>>

    @Test
    fun `get favorite activity exposes live data`() = runTest {
        // Arrange
        val liveDataToReturn = MutableLiveData<List<Activity>>()
            .apply { value = listOf(activity1, activity2) }

        val expectedList = listOf(activity1, activity2)

        whenever(mockActivityRepository.getActivityListLiveData()).doReturn(liveDataToReturn)

        val getFavoriteActivities = GetFavoriteActivitiesImpl(mockActivityRepository)

        // Act
        getFavoriteActivities().observeForever(activityListObserver)

        // Assert
        verify(activityListObserver, times(1)).onChanged(activityListCaptor.capture())
        assertEquals(activityListCaptor.value, expectedList)
    }
}