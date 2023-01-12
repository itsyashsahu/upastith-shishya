package com.axyz.upasthithshishya.di


import android.content.SharedPreferences
import com.axyz.upasthithshishya.api.APIInterface
import com.axyz.upasthithshishya.repositories.AuthRepository
import com.axyz.upasthithshishya.repositories.DefaultAuthRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AuthModule {

    @Provides
    fun providesAuthRepository(retrofitApi: APIInterface, sharedPreferences: SharedPreferences, gson:Gson) =
        DefaultAuthRepository(retrofitApi, sharedPreferences, gson) as AuthRepository
}