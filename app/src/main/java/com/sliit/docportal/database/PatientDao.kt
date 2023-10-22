package com.sliit.docportal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sliit.docportal.database.Patient

@Dao
interface PatientDao {

    @Insert
    suspend fun insert(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("SELECT * FROM patients_table WHERE id = :id")
    suspend fun getPatientById(id: Int): Patient

    @Query("SELECT * FROM patients_table")
    fun getAllPatients(): List<Patient>

    @Query("SELECT * FROM patients_table WHERE email = :email")
    fun getPatientByEmail(email: String): LiveData<List<Patient>>

//    @Query("SELECT * FROM patient_table WHERE email = :email AND password = :password")
//    suspend fun getStudentByEmailAndPassword(email: String, password: String): Employee?

    @Query("SELECT * FROM patients_table WHERE email = :email")
    suspend fun getPatientByEmail(email: String?): Patient?
}