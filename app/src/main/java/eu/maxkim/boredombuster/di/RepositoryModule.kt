package eu.maxkim.boredombuster.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.activity.usecase.ActivityRepository
import eu.maxkim.boredombuster.activity.repository.ActivityRepositoryImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindActivityRepository(impl: ActivityRepositoryImpl): ActivityRepository
}