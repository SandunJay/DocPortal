
package com.sliit.docportal

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sliit.docportal.fragments.RegisterPatient
import com.sliit.docportal.database.Patient
import com.sliit.docportal.database.PatientDatabase
import com.sliit.docportal.database.PatientRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PatientView : AppCompatActivity() {
    private lateinit var adapter: PatientAdapter
    private lateinit var viewModel: ViewPatientData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_view)

        val recyclerView: RecyclerView = findViewById(R.id.rvPatientList)
        val repository = PatientRepository(PatientDatabase.getDatabase(this))
        viewModel = ViewModelProvider(this)[ViewPatientData::class.java]
        viewModel.data.observe(this) {
            adapter = PatientAdapter(it, repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllPatients()

            runOnUiThread {
                viewModel.setData(data)
            }
        }

        val addPatient: Button = findViewById(R.id.btnAddPatients)

        addPatient.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, RegisterPatient())
            fragmentTransaction.addToBackStack(null) // Optional, for allowing back navigation
            fragmentTransaction.commit()
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
