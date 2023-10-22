package com.sliit.docportal

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sliit.docportal.database.Patient
import com.sliit.docportal.database.PatientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientAdapter(patients: List<Patient>, repository: PatientRepository, viewModel: ViewPatientData) :
    RecyclerView.Adapter<PatientViewHolder>() {

    var context: Context? = null
    val patients = patients
    val repository = repository
    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_patient, parent, false)
        context = parent.context
        return PatientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return patients.size
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patients[position]

        // Set the condition text
        holder.tvCondition.text = patient.condition

        // Set text color based on the condition
        holder.tvCondition.setTextColor(setConditionTextColor(patient.condition))

        holder.cbName.text = patient.name
        holder.tvMobile.text = patient.mobile
        holder.ivDelete.setOnClickListener {
            val isChecked = holder.cbName.isChecked

            if (isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(patient)
                    val data = repository.getAllPatients()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
                Toast.makeText(context, "Patient removed successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Select the patient to be removed", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Helper function to set text color based on condition
    private fun setConditionTextColor(condition: String): Int {
        return when (condition) {
            "Severe" -> Color.RED
            "Normal" -> Color.GREEN
            "Unidentified" -> Color.GRAY
            "Minor" -> Color.YELLOW
            else -> Color.BLACK
        }
    }
}
