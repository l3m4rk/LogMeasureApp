package dev.l3m4rk.logmeasureapp.data

import dev.l3m4rk.logmeasureapp.data.local.LocalMeasurement
import dev.l3m4rk.logmeasureapp.data.local.MeasurementDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeasurementsRepositoryImpl @Inject constructor(
    private val dao: MeasurementDao,
//    private val
) : MeasurementsRepository {
    override val measurements: Flow<List<Measurement>>
        get() = dao.observeAll().map { it.toExternal() }

    override suspend fun addDiameterMeasurement(diameter: Int) {
        val measurement = LocalMeasurement(diameter = diameter)
        withContext(Dispatchers.IO) {
            dao.insertMeasurement(measurement)
        }
    }

    override suspend fun addLengthMeasurement(length: Int) {
        TODO("Not yet implemented")
    }
}

fun List<LocalMeasurement>.toExternal(): List<Measurement> =
    this.map { Measurement(it.id, it.diameter) }
