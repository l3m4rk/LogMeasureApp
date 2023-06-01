package dev.l3m4rk.logmeasureapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalMeasurement::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun measurementsDao(): MeasurementDao
}
