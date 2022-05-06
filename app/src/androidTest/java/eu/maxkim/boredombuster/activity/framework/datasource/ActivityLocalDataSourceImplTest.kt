package eu.maxkim.boredombuster.activity.framework.datasource


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import eu.maxkim.boredombuster.activity.androidActivity1
import eu.maxkim.boredombuster.activity.androidActivity2
import eu.maxkim.boredombuster.activity.framework.db.ActivityDao
import eu.maxkim.boredombuster.activity.model.Activity
import eu.maxkim.boredombuster.framework.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ActivityLocalDataSourceImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val activityListObserver: Observer<List<Activity>> = mock()

    @Captor
    private lateinit var activityListCaptor: ArgumentCaptor<List<Activity>>

    private lateinit var activityDao: ActivityDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        activityDao = database.activityDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun canSaveActivityToTheDbAndReadIt() = runTest {
        // Arrange
        val activityLocalDataSource = ActivityLocalDataSourceImpl(activityDao)

        // Act
        activityLocalDataSource.saveActivity(androidActivity1)

        // Assert
        assert(activityLocalDataSource.isActivitySaved(androidActivity1.key))
    }

    @Test
    fun canDeleteActivityFromTheDb() = runTest {
        // Arrange
        val activityLocalDataSource = ActivityLocalDataSourceImpl(activityDao)
        activityLocalDataSource.saveActivity(androidActivity1)

        // Act
        activityLocalDataSource.deleteActivity(androidActivity1)

        // Assert
        assert(!activityLocalDataSource.isActivitySaved(androidActivity1.key))
    }

    @Test
    fun canSaveActivityToTheDbAndObserveTheLiveData() = runTest {
        // Arrange
        val activityLocalDataSource = ActivityLocalDataSourceImpl(activityDao)
        val expectedList = listOf(androidActivity1, androidActivity2)

        // Act
        activityLocalDataSource.getActivityListLiveData().observeForever(activityListObserver)
        activityLocalDataSource.saveActivity(androidActivity1)
        activityLocalDataSource.saveActivity(androidActivity2)

        // Assert
        verify(activityListObserver, times(3)).onChanged(activityListCaptor.capture())
        assertEquals(activityListCaptor.value, expectedList)
    }
}