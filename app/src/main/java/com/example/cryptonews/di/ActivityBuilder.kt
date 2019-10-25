package com.example.cryptonews.di

import com.example.cryptonews.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    internal abstract fun bindMainActivity(): MainActivity

}
