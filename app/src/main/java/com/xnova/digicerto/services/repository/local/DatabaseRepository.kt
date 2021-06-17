package com.xnova.digicerto.services.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xnova.digicerto.models.*
import com.xnova.digicerto.models.daos.DriverDao
import com.xnova.digicerto.models.settings.Settings

@Database(
    entities = [
        Driver::class,
        Occurrence::class,
        Producer::class,
        ProducerRoute::class,
        Route::class,
        Vehicle::class,
        VehicleCompartments::class,
        Settings::class
    ], version = 1
)
abstract class DatabaseRepository : RoomDatabase() {
    abstract fun driverDao(): DriverDao

    companion object {
        private const val mDatabaseName = "DIGIcerto"
        private lateinit var mDatabaseRepository: DatabaseRepository

        fun getDatabase(context: Context): DatabaseRepository {
            if (!Companion::mDatabaseRepository.isInitialized) {
                mDatabaseRepository =
                    Room.databaseBuilder(context, DatabaseRepository::class.java, mDatabaseName)
                        .allowMainThreadQueries()
                        .build()
            }
            return mDatabaseRepository
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DatabaseRepository::class.java, "$mDatabaseName.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        getDatabase(context).driverDao()
                    }
                })
                .build()
    }
}