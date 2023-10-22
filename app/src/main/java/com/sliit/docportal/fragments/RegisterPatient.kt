package com.sliit.docportal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sliit.docportal.PatientView
import com.sliit.docportal.databinding.FragmentRegisterPatientBinding
import com.sliit.docportal.database.Patient
import com.sliit.docportal.database.PatientDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class RegisterPatient : Fragment() {
    private lateinit var binding: FragmentRegisterPatientBinding
    private lateinit var patientDatabase: PatientDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientDatabase = PatientDatabase.getDatabase(requireContext())

        binding.btnAddPatient.setOnClickListener onClick@{
            val ageStr = binding.age.text.toString().trim()
            val name = binding.fullname.text.toString().trim()
            val email = binding.emailEt.text.toString().trim()
            val mobile = binding.mobile.text.toString().trim()
            val city = binding.ucity.text.toString().trim()
            val country = binding.country.text.toString().trim()
            val condition = binding.conditionSpinner.selectedItem.toString()

            // Reset error messages
            binding.age.error = null
            binding.fullname.error = null
            binding.emailEt.error = null
            binding.mobile.error = null

            if (ageStr.isEmpty()) {
                binding.age.error = "Age is required"
                return@onClick
            }
            if (name.isEmpty()) {
                binding.fullname.error = "Full name is required"
                return@onClick
            }
            if (email.isEmpty()) {
                binding.emailEt.error = "Email is required"
                return@onClick
            }
            if (mobile.isEmpty()) {
                binding.mobile.error = "Mobile number is required"
                return@onClick
            }

            // Check age validity
            val age = ageStr.toInt()
            if (age < 0 || age > 120) {
                binding.age.error = "Age must be between 0 and 120"
                return@onClick
            }

            // Check mobile number validity
            if (!mobile.matches(Regex("^0\\d{9}\$"))) {
                binding.mobile.error = "Invalid mobile number. It should start with 0 and have 10 digits."
                return@onClick
            }

            // Check email validity
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEt.error = "Invalid email format"
                return@onClick
            }

            val currentDate = Date().toString()

            GlobalScope.launch {
                val patient = Patient(name = name, age = age, email = email, mobile = mobile, city = city, country = country, condition = condition, admissionDate = currentDate)
                patientDatabase.patientDao().insert(patient)
                showToast("Registration successful!")

                val intent = Intent(requireContext(), PatientView::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
