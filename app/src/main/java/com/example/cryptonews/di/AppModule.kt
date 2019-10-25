package com.example.cryptonews.di

import android.app.Application
import android.util.Log
import com.example.cryptonews.BuildConfig
import com.example.cryptonews.network.CryptoNewsService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
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
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    internal fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Logger())
            .build()
    }


}

/**
 * This class is not essential
 * added this interceptor just to show the responses in the logcat
 * e.g the error with the google api can be recognizable here
 */
class Logger : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {

        val request = chain?.request()
        Log.i("NETLOG", "call ==> " + request?.url())
        val response = chain?.proceed(request!!)
        val responseBody = response?.body()
        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer()
        val strResponse = buffer.clone().readString(Charset.forName("UTF8")).toString()
        Log.e("NETLOG", "status code : ${response.code()} and ret ==> " + strResponse)

        return response

    }
}