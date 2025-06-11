package com.example.gymjournal.di

import android.content.Context
import androidx.room.Room
import com.example.gymjournal.data.api.WgerApiService
import com.example.gymjournal.data.local.AppDatabase
import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "gymjournal_db"
        ).build()
    }

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    @Singleton
    fun provideWgerApi(): WgerApiService {
        return Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WgerApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        api: WgerApiService,
        dao: ExerciseDao
    ): ExerciseRepository = ExerciseRepository(api, dao)
}
