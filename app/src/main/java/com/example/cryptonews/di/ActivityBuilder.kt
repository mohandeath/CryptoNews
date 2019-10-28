package com.example.cryptonews.di

import com.example.cryptonews.MainActivity
import com.example.cryptonews.NewsDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun bindNewsDetailActivity(): NewsDetailActivity

}
