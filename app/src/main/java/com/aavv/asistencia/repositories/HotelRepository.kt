package com.aavv.asistencia.repositories

import com.aavv.asistencia.daos.HotelDao
import com.aavv.asistencia.models.Hotel

class HotelRepository (val hotelDao: HotelDao) {

    suspend fun upsertHotel(hotel: Hotel) = hotelDao.upsertHotel(hotel)

    fun getAllHotels() = hotelDao.getAllHotels()

    suspend fun deleteHotel(id: Int) = hotelDao.deleteHotel(id)

}