package eu.maxkim.boredombuster.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.activity.framework.db.ActivityDao
import eu.maxkim.boredombuster.framework.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideActivityDao(appDatabase: AppDatabase): ActivityDao {
        return appDatabase.activityDao()
    }
}