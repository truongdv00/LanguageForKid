package com.learning.kids.baseapp.di

import android.content.Context
import com.learning.kids.baseapp.MyApplication
import dagger.Binds
import dagger.Module

/**
 * Created by dev.mahmoud_ashraf on 12/10/2019.
 */
@Module
abstract class AppModule {

    @Binds
    abstract fun provideApplication(application: MyApplication): Context
}