package eu.maxkim.boredombuster.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityLocalDataSourceImpl
import eu.maxkim.boredombuster.activity.framework.datasource.ActivityRemoteDataSourceImpl
import eu.maxkim.boredombuster.activity.repository.ActivityLocalDataSource
import eu.maxkim.boredombuster.activity.repository.ActivityRemoteDataSource

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindActivityRemoteDataSource(impl: ActivityRemoteDataSourceImpl): ActivityRemoteDataSource

    @Binds
    abstract fun bindActivityLocalDataSource(impl: ActivityLocalDataSourceImpl): ActivityLocalDataSource
}