package com.example.cryptonews.di


import androidx.lifecycle.ViewModel
import com.example.cryptonews.viewmodel.NewsDetailViewModel
import com.example.cryptonews.viewmodel.NewsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    internal abstract fun provideNewsListViewModel(viewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel::class)
    internal abstract fun provideNewsDetailViewModel(viewModel: NewsDetailViewModel): ViewModel

}
