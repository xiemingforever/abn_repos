package com.apprecipe.abngit.di

import android.content.Context
import androidx.room.Room
import com.apprecipe.abngit.data.database.AbnRepoDb
import com.apprecipe.abngit.data.network.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubApi {
        val baseUrl = "https://api.github.com/users/abnamrocoesd/"
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AbnRepoDb =
        Room.databaseBuilder(
            appContext,
            AbnRepoDb::class.java, "abn_repo.db"
        ).build()
}
