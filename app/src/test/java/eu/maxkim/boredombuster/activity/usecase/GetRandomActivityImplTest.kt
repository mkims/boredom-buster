package eu.maxkim.boredombuster.activity.usecase

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
class GetRandomActivityImplTest {

    private val mockActivityRepository: ActivityRepository = mock()

    @Test
    fun `get random activity interacts with repository`() = runTest {
        // Arrange
        val getRandomActivity = GetRandomActivityImpl(mockActivityRepository)

        // Act
        getRandomActivity()

        // Assert
        verify(mockActivityRepository, times(1)).getNewActivity()
    }
}