package com.guesscountry.di

import android.content.Context
import androidx.room.Room
import com.guesscountry.data.CountryDao
import com.guesscountry.data.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideCountryDatabase(@ApplicationContext applicationContext: Context): CountryDatabase =
        Room.databaseBuilder(
            applicationContext,
            CountryDatabase::class.java, "user-database"
        ).build()


    @Singleton
    @Provides
    fun provideCountryDao(CountryDatabase: CountryDatabase): CountryDao = CountryDatabase.countryDao()
}