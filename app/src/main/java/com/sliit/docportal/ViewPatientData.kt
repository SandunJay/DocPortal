package com.sliit.docportal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sliit.docportal.database.Patient

class ViewPatientData: ViewModel() {
    private val _data = MutableLiveData<List<Patient>>()
    val data:LiveData<List<Patient>> = _data

    fun setData(data:List<Patient>){
        _data.value = data
    }
}