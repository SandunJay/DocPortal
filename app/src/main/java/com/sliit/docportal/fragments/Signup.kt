package com.sliit.docportal.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.sliit.docportal.R
import com.sliit.docportal.database.Employee
import com.sliit.docportal.database.EmployeeDatabase
import com.sliit.docportal.databinding.FragmentSignupBinding
import com.sliit.docportal.fragments.SignIn
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Signup : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private lateinit var employeeDatabase: EmployeeDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employeeDatabase = EmployeeDatabase.getDatabase(requireContext())

        binding.btnSignUp.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val name = binding.fullname.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val mobile = binding.mobile.text.toString().trim()
            val city = binding.ucity.text.toString().trim()
            val password = binding.passET.text.toString().trim()
            val confirmPassword = binding.confirmPassEt.text.toString().trim()

            // Reset error states
            clearErrorStates()

            var hasErrors = false

            if (username.isEmpty() || !isValidUsername(username)) {
                setErrorAndFocus(binding.uname, "Invalid Username!")
                hasErrors = true
            }
            if (name.isEmpty()) {
                setErrorAndFocus(binding.fnamelay, "Required!")
                hasErrors = true
            }
            if (email.isEmpty() || !isValidEmail(email)) {
                setErrorAndFocus(binding.emailLayout, "Invalid Email!")
                hasErrors = true
            }
            if (mobile.isEmpty() || !isValidMobile(mobile)) {
                setErrorAndFocus(binding.mobilely, "Invalid Mobile!")
                hasErrors = true
            }
            if (password.isEmpty() || !isValidPassword(password)) {
                setErrorAndFocus(binding.passwordLayout, "Invalid Password!")
                hasErrors = true
            }
            if (confirmPassword != password) {
                setErrorAndFocus(binding.confirmPasswordLayout, "Passwords do not match!")
                hasErrors = true
            }

            if (!hasErrors) {
                val student = Employee(username = username, name = name, email = email, mobile = mobile, password = password, city = city)

                GlobalScope.launch {
                    employeeDatabase.employeeDao().insert(student)
                }

                showToast("Registration successful!")
            }
        }

        binding.tvLoginSelection.setOnClickListener {
            navigateToSignIn()
        }
    }

    private fun isValidUsername(username: String): Boolean {
        // Implement your own validation logic for usernames
        // For example: usernames should have lowercase letters and numbers only
        return username.matches(Regex("^[a-z0-9]+$"))
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidMobile(mobile: String): Boolean {
        return mobile.length >= 10 && mobile.matches(Regex("\\d+"))
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*\\d).{8,}\$".toRegex()
        return password.matches(passwordPattern)
    }

    private fun setErrorAndFocus(layout: TextInputLayout, errorMessage: String) {
        layout.isErrorEnabled = true
        layout.error = errorMessage
        layout.editText?.requestFocus()
    }

    private fun clearErrorStates() {
        binding.uname.isErrorEnabled = false
        binding.fnamelay.isErrorEnabled = false
        binding.emailLayout.isErrorEnabled = false
        binding.mobilely.isErrorEnabled = false
        binding.passwordLayout.isErrorEnabled = false
        binding.confirmPasswordLayout.isErrorEnabled = false
    }

    private fun navigateToSignIn() {
        // Initiate a fragment transaction to replace the current fragment with SignIn
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, SignIn())
        fragmentTransaction.addToBackStack(null) // Add to back stack for navigation
        fragmentTransaction.commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
