package com.example.gymjournal.core.di

import com.example.gymjournal.data.source.remote.ExerciseRemoteDataSource
import com.example.gymjournal.data.source.remote.ExerciseRemoteDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideExerciseRemoteDataSource(
        firestore: FirebaseFirestore
    ): ExerciseRemoteDataSource {
        return ExerciseRemoteDataSourceImpl(firestore)
    }
}
