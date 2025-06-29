package com.example.gymjournal.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.local.dao.GoalDao
import com.example.gymjournal.data.local.dao.ProfileDao
import com.example.gymjournal.data.local.dao.RoutineDao
import com.example.gymjournal.data.local.dao.RoutineDayDao
import com.example.gymjournal.data.local.dao.RoutineExerciseDao
import com.example.gymjournal.data.local.db.AppDatabase
import com.example.gymjournal.data.preference.PreferencesManager
import com.example.gymjournal.data.preference.ThemePreferenceManager
import com.example.gymjournal.data.repository.ExerciseRepositoryImpl
import com.example.gymjournal.data.repository.GoalRepositoryImpl
import com.example.gymjournal.data.repository.OnboardingRepositoryImpl
import com.example.gymjournal.data.repository.ProfileRepositoryImpl
import com.example.gymjournal.data.repository.ThemeRepositoryImpl
import com.example.gymjournal.data.source.local.ProfileLocalDataSource
import com.example.gymjournal.data.source.remote.FirestoreExerciseSource
import com.example.gymjournal.domain.repository.ExerciseRepository
import com.example.gymjournal.domain.repository.GoalRepository
import com.example.gymjournal.domain.repository.OnboardingRepository
import com.example.gymjournal.domain.repository.ProfileRepository
import com.example.gymjournal.domain.repository.ThemeRepository
import com.example.gymjournal.domain.usecase.exercise.DeleteExercise
import com.example.gymjournal.domain.usecase.exercise.ExerciseUseCases
import com.example.gymjournal.domain.usecase.exercise.GetExerciseById
import com.example.gymjournal.domain.usecase.exercise.GetExercises
import com.example.gymjournal.domain.usecase.exercise.InsertExercise
import com.example.gymjournal.domain.usecase.exercise.SyncExercisesUseCase
import com.example.gymjournal.domain.usecase.exercise.UpdateExercise
import com.example.gymjournal.domain.usecase.goal.DeleteGoal
import com.example.gymjournal.domain.usecase.goal.GetAllGoals
import com.example.gymjournal.domain.usecase.goal.GetGoalById
import com.example.gymjournal.domain.usecase.goal.GoalUseCases
import com.example.gymjournal.domain.usecase.goal.InsertGoal
import com.example.gymjournal.domain.usecase.goal.UpdateGoal
import com.example.gymjournal.domain.usecase.user.GetProfileUseCase
import com.example.gymjournal.domain.usecase.user.ProfileUseCases
import com.example.gymjournal.domain.usecase.user.SaveProfileUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile("settings") }
        )
    }

    // ✅ Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "gymjournal_db"
        )
            .build()
    }

    // ✅ DAOs
    @Provides
    @Singleton
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    @Singleton
    fun provideGoalDao(db: AppDatabase): GoalDao = db.goalDao()

    @Provides
    @Singleton
    fun provideRoutineDao(db: AppDatabase): RoutineDao = db.routineDao()

    @Provides
    @Singleton
    fun provideRoutineDayDao(db: AppDatabase): RoutineDayDao = db.routineDayDao()

    @Provides
    @Singleton
    fun provideRoutineExerciseDao(db: AppDatabase): RoutineExerciseDao = db.routineExerciseDao()

    // ✅ Repositories
    @Provides
    fun provideExerciseRepository(
        remote: FirestoreExerciseSource,
        dao: ExerciseDao
    ): ExerciseRepository {
        return ExerciseRepositoryImpl(
            firestoreSource = remote,
            dao = dao
        )
    }

    @Provides
    fun provideExerciseRemoteSource(
        firestore: FirebaseFirestore
    ): FirestoreExerciseSource = FirestoreExerciseSource(firestore)

    @Provides
    @Singleton
    fun provideGoalRepository(dao: GoalDao): GoalRepository =
        GoalRepositoryImpl(dao)

    // ✅ UseCases
    @Provides
    @Singleton
    fun provideExerciseUseCases(repository: ExerciseRepository): ExerciseUseCases {
        return ExerciseUseCases(
            getExercises = GetExercises(repository),
            getExerciseById = GetExerciseById(repository),
            insertExercise = InsertExercise(repository),
            updateExercise = UpdateExercise(repository),
            deleteExercise = DeleteExercise(repository),
            syncExercises = SyncExercisesUseCase(repository)
        )
    }

    @Provides
    fun provideOnboardingRepository(
        preferencesManager: PreferencesManager
    ): OnboardingRepository = OnboardingRepositoryImpl(preferencesManager)

    @Provides
    @Singleton
    fun provideThemePreferenceManager(
        dataStore: DataStore<Preferences>
    ): ThemePreferenceManager = ThemePreferenceManager(dataStore)

    @Provides
    @Singleton
    fun provideThemeRepository(
        preferenceManager: ThemePreferenceManager
    ): ThemeRepository = ThemeRepositoryImpl(preferenceManager)

    @Provides
    fun provideProfileDao(appDatabase: AppDatabase): ProfileDao = appDatabase.profileDao()

    @Provides
    fun provideProfileRepository(
        local: ProfileLocalDataSource
    ): ProfileRepository = ProfileRepositoryImpl(local)

    @Provides
    fun provideProfileUseCases(repo: ProfileRepository): ProfileUseCases =
        ProfileUseCases(
            getProfile = GetProfileUseCase(repo),
            saveProfile = SaveProfileUseCase(repo)
        )

    @Provides
    fun provideGoalUseCases(repository: GoalRepository): GoalUseCases =
        GoalUseCases(
            getAllGoals = GetAllGoals(repository),
            insertGoal = InsertGoal(repository),
            updateGoal = UpdateGoal(repository),
            deleteGoal = DeleteGoal(repository),
            getGoalById = GetGoalById(repository)
        )
}
