package com.sliit.docportal.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sliit.docportal.PatientView
import com.sliit.docportal.R
import com.sliit.docportal.UserProfile
//import com.sliit.docportal.ViewPatients
import com.sliit.docportal.database.Employee
import com.sliit.docportal.database.EmployeeDao
import com.sliit.docportal.database.EmployeeDatabase
import com.sliit.docportal.fragments.SignIn.Companion.EXTRA_USERNAME
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val tvDashboardUserName = view.findViewById<TextView>(R.id.tvDashboardUserName)
        val empUsername = arguments?.getString(EXTRA_USERNAME)

        if (empUsername != null) {
            val employeeDatabase = EmployeeDatabase.getDatabase(requireContext())
            val employeeDao: EmployeeDao = employeeDatabase.employeeDao()

            GlobalScope.launch {
                val employee: Employee? = employeeDao.getEmployeeByUsername(empUsername)
                if (employee != null) {
                    val username = employee.username
                    // Set the username in the TextView
                    tvDashboardUserName.text = "Welcome, $username"
                }
            }
        }

        // Get references to the buttons
        val btnViewPatients = view.findViewById<Button>(R.id.btnViewPatients)
        val btnViewUser = view.findViewById<Button>(R.id.btnViewUser)

        // Set click listener for btnViewPatients
        btnViewPatients.setOnClickListener {
            // Create an intent to start the ViewPatientActivity
            navigateToPatientView()
        }

        // Set click listener for btnViewUser
        btnViewUser.setOnClickListener {
            // Create an intent to start the UserProfileActivity
            val intent = Intent(context, UserProfile::class.java)
            // Pass the employeeId to UserProfileActivity
            intent.putExtra("username", empUsername) // Make sure you have a valid `id` here
            startActivity(intent)
        }

        return view
    }

    private fun navigateToPatientView() {
        val intent = Intent(requireContext(), PatientView::class.java)
        startActivity(intent)
    }

}
