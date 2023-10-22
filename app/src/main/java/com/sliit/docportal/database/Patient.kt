package com.sliit.docportal.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "patients_table")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,
    var name: String?,
    var age: Int,
    var email:String,
    var country:String,
    var city:String,
    var mobile:String,
    var condition: String,
    var admissionDate:String
)