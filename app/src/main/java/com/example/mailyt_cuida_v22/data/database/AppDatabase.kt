package com.example.mailyt_cuida_v22.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.mailyt_cuida_v22.data.dao.SignosVitalesDao
import com.example.mailyt_cuida_v22.data.entity.SignosVitalesEntity

@Database(
    entities = [SignosVitalesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun signosVitalesDao(): SignosVitalesDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "maily_cuida_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 