package eu.maxkim.boredombuster.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.BuildConfig
import eu.maxkim.boredombuster.di.annotation.BaseUrl
import eu.maxkim.boredombuster.activity.framework.api.ActivityApiClient
import eu.maxkim.boredombuster.activity.framework.api.ActivityTypeAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val HTTP_CACHE_SIZE = 1024 * 1024 * 1024L
    private const val CONNECTION_TIMEOUT_SECONDS = 600L

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val cacheDirName = "okhttp_cache"

        val cacheDirectory = context.getDir(cacheDirName, Context.MODE_PRIVATE)
        return Cache(cacheDirectory, HTTP_CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
        val moshi: Moshi = Moshi.Builder().add(ActivityTypeAdapter()).build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ActivityApiClient {
        return retrofit.create(ActivityApiClient::class.java)
    }
}