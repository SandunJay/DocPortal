package com.sliit.docportal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val username: String,
    val email:String,
    val password:String,
    val city:String,
    val mobile:String
    )