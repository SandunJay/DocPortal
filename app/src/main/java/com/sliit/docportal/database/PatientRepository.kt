package com.sliit.docportal.database

import com.sliit.docportal.database.Patient
import com.sliit.docportal.database.PatientDatabase

class PatientRepository(private val db: PatientDatabase) {

    fun getAllPatients(): List<Patient> = db.patientDao().getAllPatients()

    suspend fun insert(patient: Patient) = db.patientDao().insert(patient)
    suspend fun delete(patient: Patient) = db.patientDao().delete(patient)

}