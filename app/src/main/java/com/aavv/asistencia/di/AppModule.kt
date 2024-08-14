package com.aavv.asistencia.di

import android.app.Application
import androidx.room.Room
import com.aavv.asistencia.bd.MyDataBase
import com.aavv.asistencia.daos.AgenciaDao
import com.aavv.asistencia.daos.HotelDao
import com.aavv.asistencia.repositories.AgenciaRepository
import com.aavv.asistencia.repositories.HotelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDataBase {
        return Room.databaseBuilder(
            context = app,
            klass = MyDataBase::class.java,
            name = "mydb")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHotelDao(db: MyDataBase): HotelDao {
        return db.hotelDao
    }

    @Provides
    @Singleton
    fun provideHotelRepo(dao: HotelDao): HotelRepository {
        return HotelRepository(dao)
    }

    @Provides
    @Singleton
    fun provideAgenciaDao(db: MyDataBase): AgenciaDao {
        return db.agenciaDao
    }

    @Provides
    @Singleton
    fun provideAgenciaRepo(dao: AgenciaDao): AgenciaRepository {
        return AgenciaRepository(dao)
    }
}