package com.sliit.docportal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sliit.docportal.R
import com.sliit.docportal.database.Employee
import com.sliit.docportal.database.EmployeeDatabase
import com.sliit.docportal.databinding.FragmentSigninBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignIn : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private lateinit var employeeDatabase: EmployeeDatabase

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employeeDatabase = EmployeeDatabase.getDatabase(requireContext())

        binding.btnLogin.setOnClickListener {
            val username = binding.emailEt.text.toString().trim()
            val password = binding.passET.text.toString().trim()


            GlobalScope.launch(Dispatchers.Main) {
                val employee = isValidLogin(username, password)
                if (employee != null) {

                    navigateToDashboard(username)
                    showToast("Login successful!")
                } else {

                    showToast("Invalid login credentials. Please try again.")
                }
            }
        }

        binding.tvRegSelection.setOnClickListener {
            navigateToSignUp()
        }

    }

    private suspend fun isValidLogin(username: String, password: String): Employee? {
        return employeeDatabase.employeeDao().getEmployeesByUsernameAndPassword(username, password)
    }


    private fun navigateToDashboard(username: String) {

        val dashboardFragment = Home()

        // Create a Bundle to pass data (email in this case) to the fragment
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        dashboardFragment.arguments = bundle

        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, dashboardFragment)
        fragmentTransaction.addToBackStack(null) // Add to back stack for navigation
        fragmentTransaction.commit()
    }

    private fun navigateToSignUp() {
        // Initiate a fragment transaction to replace the current fragment with SignUp
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, Signup())
        fragmentTransaction.addToBackStack(null) // Add to back stack for navigation
        fragmentTransaction.commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
