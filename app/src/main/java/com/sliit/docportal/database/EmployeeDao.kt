package com.sliit.docportal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employee_table")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employee_table WHERE email = :email")
    fun getEmployeesByEmail(email: String): LiveData<List<Employee>>

    @Query("SELECT * FROM employee_table WHERE email = :email AND password = :password")
    suspend fun getEmployeesByEmailAndPassword(email: String, password: String): Employee?

    @Query("SELECT * FROM employee_table WHERE username = :username AND password = :password")
    suspend fun getEmployeesByUsernameAndPassword(username: String, password: String): Employee?

    @Query("SELECT * FROM employee_table WHERE id = :id")
    suspend fun getEmployeeById(id: Long?): Employee?

    @Query("SELECT * FROM employee_table WHERE username = :username")
    suspend fun getEmployeeByUsername(username: String?): Employee?

//    @Insert
//    suspend fun insertcourse(course: Course)
//

}