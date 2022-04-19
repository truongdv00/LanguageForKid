package com.learning.kids.baseapp.di

import com.learning.kids.baseapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dev.mahmoud_ashraf on 12/10/2019.
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector/*(modules = [MainActivityModule::class])*/
    abstract fun bindMainActivity(): MainActivity

   /* @ContributesAndroidInjector(modules = [SubmitActivityModule::class])
    abstract fun bindSubmitActivity(): SubmitActivity*/
}