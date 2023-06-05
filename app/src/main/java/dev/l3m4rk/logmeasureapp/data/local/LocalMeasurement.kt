package dev.l3m4rk.logmeasureapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurement")
data class LocalMeasurement(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val diameter: Int,
    val createdAt: Long,
)

//enum class MeasurementType {
//    Diameter,
//
//}
