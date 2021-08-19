package com.xnova.digicerto.services.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xnova.digicerto.models.entities.*
import com.xnova.digicerto.models.entities.settings.Settings
import com.xnova.digicerto.services.constants.DatabaseConstants
import com.xnova.digicerto.services.repositories.local.Converters
import com.xnova.digicerto.services.data.daos.*

@Database(
    entities = [
        Driver::class,
        Occurrence::class,
        Producer::class,
        RouteProducer::class,
        Route::class,
        Vehicle::class,
        VehicleCompartment::class,
        Settings::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class DatabaseService : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun settingsDao(): SettingsDao
    abstract fun occurrenceDao(): OccurrenceDao
    abstract fun producerDao(): ProducerDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun routeDao(): RouteDao

    companion object {
        private const val DATABASE_NAME = "DIGIcerto"

        @Volatile
        private lateinit var mDatabaseService: DatabaseService

        fun getDatabase(context: Context): DatabaseService {
            return synchronized(this) {
                if (!::mDatabaseService.isInitialized) {
                    mDatabaseService =
                        Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addCallback(callback())
                            .build()
                }
                mDatabaseService
            }
        }

        private fun callback() =
            object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(DatabaseConstants.INITIAL_INSERTION_QUERY)
                }
            }
    }
}