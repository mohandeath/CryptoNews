package com.example.cryptonews.di

import android.app.Application
import androidx.room.Room
import com.example.cryptonews.BuildConfig
import com.example.cryptonews.DATABASE_NAME
import com.example.cryptonews.common.Utils
import com.example.cryptonews.data.local.Database
import com.example.cryptonews.data.local.NewsItemDAO
import com.example.cryptonews.data.remote.CryptoNewsService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This module provides all the app level and generic dependencies, like app context, networking, db, etc.
 */
@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    internal fun providesApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    internal fun providePlacesService(gson: Gson, httpClient: OkHttpClient): CryptoNewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(CryptoNewsService::class.java)
    }

    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }


    @Provides
    internal fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    internal fun providePicassoClient(application: Application, client: OkHttpClient): Picasso {

        return Picasso.Builder(application).downloader(OkHttp3Downloader(client)).build()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: Database): NewsItemDAO = database.NewsItemDAO()


    @Singleton
    @Provides
    fun provideUtils(): Utils {
        return Utils(application)
    }
}
