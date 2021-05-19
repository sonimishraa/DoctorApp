package com.iotric.doctorplus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatientListViewModel : ViewModel() {

    val patientListLiveData = MutableLiveData<List<DataModel>>()

    fun setRequest() {
        val responseList = getData()
        patientListLiveData.postValue(responseList)
    }

    private fun getData(): ArrayList<DataModel> {
        val list = ArrayList<DataModel>()
        list.add(DataModel("Soni Mishra", "98894676", "2/1/2021"))
        list.add(DataModel("Soni Mishra", "98894676", "2/1/2021"))
        list.add(DataModel("Soni Mishra", "98894676", "2/1/2021"))
        list.add(DataModel("Soni Mishra", "98894676", "2/1/2021"))
        list.add(DataModel("Soni Mishra", "98894676", "2/1/2021"))
        return list
    }
}