package com.sliit.docportal

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sliit.docportal.database.Employee
import com.sliit.docportal.database.EmployeeDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserProfile : AppCompatActivity() {

    private lateinit var employeeDatabase: EmployeeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userprofile)

        val username = intent.getStringExtra("username")?:""

        employeeDatabase = EmployeeDatabase.getDatabase(this)

        // Retrieve employee data from the database
        GlobalScope.launch {
            val employee = getEmployeeByUsername(username)

            runOnUiThread {
                if (employee != null) {
                    val usernameTextView = findViewById<TextView>(R.id.tvUserName)
                    val fullNameTextView = findViewById<TextView>(R.id.fullname)
                    val emailTextView = findViewById<TextView>(R.id.tvEmail)
                    val mobileTextView = findViewById<TextView>(R.id.tvViewMobile)
                    val cityTextView = findViewById<TextView>(R.id.tvCity)

                    usernameTextView.text = username
                    fullNameTextView.text = employee.name
                    emailTextView.text = employee.email
                    mobileTextView.text = employee.mobile
                    cityTextView.text = employee.city

                    showToast("Data retrieved successfully!")
                } else {
                    showToast("Error: Employee data not found.")
                }
            }
        }
    }

    private suspend fun getEmployeeByUsername(username: String): Employee? {
        return employeeDatabase.employeeDao().getEmployeeByUsername(username)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
