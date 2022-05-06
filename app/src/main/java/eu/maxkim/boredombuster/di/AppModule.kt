package eu.maxkim.boredombuster.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.maxkim.boredombuster.di.annotation.AppScope
import eu.maxkim.boredombuster.di.annotation.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @AppScope
    fun provideAppScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @IODispatcher
    fun provideIODispatcher() = Dispatchers.IO
}