package com.sliit.docportal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class PatientDatabase: RoomDatabase() {
    abstract fun patientDao(): PatientDao

    companion object {
        @Volatile
        private var INSTANCE: PatientDatabase? = null

        fun getDatabase(context: Context): PatientDatabase {
            synchronized(this){
            return INSTANCE ?: Room.databaseBuilder(
                    context,
                    PatientDatabase::class.java,
                    "doc_portal_patients"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}