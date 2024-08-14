package com.aavv.asistencia.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.aavv.asistencia.models.Hotel
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelDao {

    @Upsert
    suspend fun upsertHotel(hotel: Hotel)

    @Query("SELECT * FROM hotel")
    fun getAllHotels(): Flow<List<Hotel>>

    @Query("DELETE FROM hotel WHERE id=:id")
    suspend fun deleteHotel(id: Int)
}