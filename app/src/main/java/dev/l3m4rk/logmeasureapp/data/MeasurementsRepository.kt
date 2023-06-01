package dev.l3m4rk.logmeasureapp.data

import kotlinx.coroutines.flow.Flow

interface MeasurementsRepository {

    val measurements: Flow<List<Measurement>>

    suspend fun addDiameterMeasurement(diameter: Int)

    suspend fun addLengthMeasurement(length: Int)
}




