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
class IsActivitySavedImplTest {

    private val mockActivityRepository: ActivityRepository = mock()

    @Test
    fun `is activity saved interacts with repository`() = runTest {
        // Arrange
        val isActivitySaved = IsActivitySavedImpl(mockActivityRepository)

        // Act
        isActivitySaved(activity1.key)

        // Assert
        verify(mockActivityRepository, times(1)).isActivitySaved(activity1.key)
    }
}