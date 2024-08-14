package com.aavv.asistencia.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aavv.asistencia.daos.AgenciaDao
import com.aavv.asistencia.daos.HotelDao
import com.aavv.asistencia.models.Agencia
import com.aavv.asistencia.models.Hotel

@Database(entities = [Hotel::class, Agencia::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract val hotelDao: HotelDao
    abstract val agenciaDao: AgenciaDao
}