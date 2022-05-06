package eu.maxkim.boredombuster.activity.framework.datasource

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import eu.maxkim.boredombuster.activity.framework.api.ActivityApiClient
import eu.maxkim.boredombuster.activity.framework.api.ActivityTypeAdapter
import eu.maxkim.boredombuster.activity.responseActivity
import eu.maxkim.boredombuster.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class ActivityRemoteDataSourceImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiClient: ActivityApiClient

    private val client = OkHttpClient.Builder().build()

    private val moshi: Moshi = Moshi.Builder()
        .add(ActivityTypeAdapter())
        .build()

    @Before
    fun createServer() {
        mockWebServer = MockWebServer()

        apiClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // setting a dummy url
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
            .create(ActivityApiClient::class.java)
    }

    @After
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `correct response is parsed into success result`() = runTest {
        // Arrange
        val response = MockResponse()
            .setBody(successfulResponse)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val activityRemoteDataSource = ActivityRemoteDataSourceImpl(apiClient)
        val expectedActivity = responseActivity

        // Act
        val result = activityRemoteDataSource.getActivity()

        // Assert
        assert(result is Result.Success)
        assertEquals((result as Result.Success).data, expectedActivity)
    }

    @Test
    fun `malformed response returns json error result`() = runTest {
        // Arrange
        val response = MockResponse()
            .setBody(errorResponse)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val activityRemoteDataSource = ActivityRemoteDataSourceImpl(apiClient)

        // Act
        val result = activityRemoteDataSource.getActivity()

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).error is JsonDataException)
    }

    @Test
    fun `error response returns http error result`() = runTest {
        // Arrange
        val response = MockResponse()
            .setBody(errorResponse)
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        val activityRemoteDataSource = ActivityRemoteDataSourceImpl(apiClient)

        // Act
        val result = activityRemoteDataSource.getActivity()

        // Assert
        assert(result is Result.Error)
        assert((result as Result.Error).error is HttpException)
    }
}