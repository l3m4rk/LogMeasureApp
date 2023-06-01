package dev.l3m4rk.logmeasureapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.l3m4rk.logmeasureapp.data.MeasurementsRepository
import dev.l3m4rk.logmeasureapp.data.MeasurementsRepositoryImpl
import dev.l3m4rk.logmeasureapp.data.local.AppDatabase
import dev.l3m4rk.logmeasureapp.data.local.MeasurementDao
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val DB_NAME = "measurements.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun measurementsDao(database: AppDatabase): MeasurementDao = database.measurementsDao()

    @Singleton
    @Provides
    fun provideRepository(dao: MeasurementDao): MeasurementsRepository {
        return MeasurementsRepositoryImpl(dao)
    }
}
