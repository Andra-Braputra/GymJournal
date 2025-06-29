package com.example.gymjournal.core.di

import com.example.gymjournal.data.repository.routine.RoutineDayRepositoryImpl
import com.example.gymjournal.data.repository.routine.RoutineExerciseRepositoryImpl
import com.example.gymjournal.data.repository.routine.RoutineRepositoryImpl
import com.example.gymjournal.data.source.local.RoutineDayLocalDataSource
import com.example.gymjournal.data.source.local.RoutineExerciseLocalDataSource
import com.example.gymjournal.data.source.local.RoutineLocalDataSource
import com.example.gymjournal.data.source.local.impl.RoutineDayLocalDataSourceImpl
import com.example.gymjournal.data.source.local.impl.RoutineExerciseLocalDataSourceImpl
import com.example.gymjournal.data.source.local.impl.RoutineLocalDataSourceImpl
import com.example.gymjournal.domain.repository.routine.RoutineDayRepository
import com.example.gymjournal.domain.repository.routine.RoutineExerciseRepository
import com.example.gymjournal.domain.repository.routine.RoutineRepository
import com.example.gymjournal.domain.usecase.routine.DeleteRoutineDayUseCase
import com.example.gymjournal.domain.usecase.routine.DeleteRoutineExerciseUseCase
import com.example.gymjournal.domain.usecase.routine.DeleteRoutineExercisesByDayUseCase
import com.example.gymjournal.domain.usecase.routine.DeleteRoutineUseCase
import com.example.gymjournal.domain.usecase.routine.DeselectAllRoutinesUseCase
import com.example.gymjournal.domain.usecase.routine.GetAllRoutinesUseCase
import com.example.gymjournal.domain.usecase.routine.GetRoutineByIdUseCase
import com.example.gymjournal.domain.usecase.routine.GetRoutineDaysByRoutineIdUseCase
import com.example.gymjournal.domain.usecase.routine.GetRoutineExerciseByIdUseCase
import com.example.gymjournal.domain.usecase.routine.GetRoutineExercisesByDayUseCase
import com.example.gymjournal.domain.usecase.routine.GetSelectedRoutineUseCase
import com.example.gymjournal.domain.usecase.routine.InsertRoutineDayUseCase
import com.example.gymjournal.domain.usecase.routine.InsertRoutineDaysUseCase
import com.example.gymjournal.domain.usecase.routine.InsertRoutineExerciseUseCase
import com.example.gymjournal.domain.usecase.routine.InsertRoutineExercisesUseCase
import com.example.gymjournal.domain.usecase.routine.InsertRoutineUseCase
import com.example.gymjournal.domain.usecase.routine.RoutineDayUseCases
import com.example.gymjournal.domain.usecase.routine.RoutineExercisesUseCases
import com.example.gymjournal.domain.usecase.routine.RoutineUseCases
import com.example.gymjournal.domain.usecase.routine.UpdateRoutineDayUseCase
import com.example.gymjournal.domain.usecase.routine.UpdateRoutineExerciseUseCase
import com.example.gymjournal.domain.usecase.routine.UpdateRoutineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoutineModule {


    // --- DataSources ---
    @Provides
    @Singleton
    fun provideRoutineLocalDataSource(
        impl: RoutineLocalDataSourceImpl
    ): RoutineLocalDataSource = impl

    @Provides
    @Singleton
    fun provideRoutineDayLocalDataSource(
        impl: RoutineDayLocalDataSourceImpl
    ): RoutineDayLocalDataSource = impl

    @Provides
    @Singleton
    fun provideRoutineExerciseLocalDataSource(
        impl: RoutineExerciseLocalDataSourceImpl
    ): RoutineExerciseLocalDataSource = impl

    // --- Repositories ---
    @Provides
    @Singleton
    fun provideRoutineRepository(
        impl: RoutineRepositoryImpl
    ): RoutineRepository = impl

    @Provides
    @Singleton
    fun provideRoutineDayRepository(
        impl: RoutineDayRepositoryImpl
    ): RoutineDayRepository = impl

    @Provides
    @Singleton
    fun provideRoutineExerciseRepository(
        impl: RoutineExerciseRepositoryImpl
    ): RoutineExerciseRepository = impl

    // --- UseCase Groups ---
    @Provides
    @Singleton
    fun provideRoutineUseCases(repository: RoutineRepository): RoutineUseCases {
        return RoutineUseCases(
            insertRoutine = InsertRoutineUseCase(repository),
            updateRoutine = UpdateRoutineUseCase(repository),
            deleteRoutine = DeleteRoutineUseCase(repository),
            getAllRoutines = GetAllRoutinesUseCase(repository),
            getSelectedRoutine = GetSelectedRoutineUseCase(repository),
            deselectAllRoutines = DeselectAllRoutinesUseCase(repository),
            getRoutineById = GetRoutineByIdUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRoutineDayUseCases(repository: RoutineDayRepository): RoutineDayUseCases {
        return RoutineDayUseCases(
            insertDay = InsertRoutineDayUseCase(repository),
            insertDays = InsertRoutineDaysUseCase(repository),
            updateDay = UpdateRoutineDayUseCase(repository),
            deleteDay = DeleteRoutineDayUseCase(repository),
            getDaysForRoutine = GetRoutineDaysByRoutineIdUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRoutineExercisesUseCases(repository: RoutineExerciseRepository): RoutineExercisesUseCases {
        return RoutineExercisesUseCases(
            insert = InsertRoutineExerciseUseCase(repository),
            insertMany = InsertRoutineExercisesUseCase(repository),
            update= UpdateRoutineExerciseUseCase(repository),
            delete = DeleteRoutineExerciseUseCase(repository),
            deleteByDay = DeleteRoutineExercisesByDayUseCase(repository),
            getByDay = GetRoutineExercisesByDayUseCase(repository),
            getById = GetRoutineExerciseByIdUseCase(repository)
        )
    }
}
