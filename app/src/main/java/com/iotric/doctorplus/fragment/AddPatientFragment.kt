package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.iotric.doctorplus.R
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel

class AddPatientFragment : BaseFragment() {

    lateinit var name: TextInputEditText
    lateinit var contact: TextInputEditText
    lateinit var date: TextInputEditText
    lateinit var btnAdd: Button
    lateinit var viewModel: AddPatientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_patient_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("ADD RECORD")
        initView(view)
        initViewModel()
    }
    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)

    }

    private fun insertDatatoDatabase() {
        val addname = name.text.toString()
        val addContact = contact.text.toString()
        val date = date.text.toString()
        val user = User(addname, addContact, date)
        viewModel.insertUser(user)
        Toast.makeText(requireContext(), "Patient Added successfully", Toast.LENGTH_SHORT).show()
    }

    private fun initView(view: View) {
        name = view.findViewById(R.id.tvName)
        contact = view.findViewById(R.id.tvContact)
        date = view.findViewById(R.id.tvDate)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            insertDatatoDatabase()
            findNavController().navigate(R.id.action_addfragment_to_patient_list)
        }
    }
}