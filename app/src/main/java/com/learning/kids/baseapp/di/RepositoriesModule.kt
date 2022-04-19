package com.learning.kids.baseapp.di

import com.learning.kids.baseapp.data.daos.RemoteServiceDao
import com.learning.kids.baseapp.data.repositories.PostsRepository
import com.learning.kids.baseapp.data.repositories.PostsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by dev.mahmoud_ashraf on 12/11/2019.
 */
@Module
class RepositoriesModule {


    @Provides
    @Singleton
    fun provideRepository(remoteServiceDao : RemoteServiceDao): PostsRepository {
        return PostsRepositoryImpl(remoteServiceDao)
    }
}