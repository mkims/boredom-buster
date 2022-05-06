package eu.maxkim.boredombuster.activity.usecase

import eu.maxkim.boredombuster.activity.activity1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteActivityImplTest {

    private val mockActivityRepository: ActivityRepository = mock()

    @Test
    fun `delete activity interacts with repository`() = runTest {
        // Arrange
        val deleteActivity = DeleteActivityImpl(mockActivityRepository)

        // Act
        deleteActivity(activity1)

        // Assert
        verify(mockActivityRepository, times(1)).deleteActivity(activity1)
    }
}