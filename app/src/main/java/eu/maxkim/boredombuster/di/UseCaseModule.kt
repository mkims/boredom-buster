package eu.maxkim.boredombuster.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.activity.usecase.*

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetRandomActivity(impl: GetRandomActivityImpl): GetRandomActivity

    @Binds
    abstract fun bindSaveActivity(impl: SaveActivityImpl): SaveActivity

    @Binds
    abstract fun bindDeleteActivity(impl: DeleteActivityImpl): DeleteActivity

    @Binds
    abstract fun bindIsActivitySaved(impl: IsActivitySavedImpl): IsActivitySaved

    @Binds
    abstract fun bindGetFavoriteActivities(impl: GetFavoriteActivitiesImpl): GetFavoriteActivities
}