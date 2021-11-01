package com.monika.kisshttask.di

import com.monika.kisshttask.network.ApiService
import com.monika.kisshttask.persistence.PosterDao
import com.monika.kisshttask.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        apiService: ApiService,
        posterDao: PosterDao
    ): MainRepository {
        return MainRepository(apiService, posterDao)
    }
}
