package com.iotric.doctorplus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.iotric.doctorplus.R
import com.iotric.doctorplus.model.User
import com.iotric.doctorplus.viewmodel.AddPatientViewModel

class PatientUpdateLDialogFragment : BottomSheetDialogFragment() {

    private val args by navArgs<PatientUpdateLDialogFragmentArgs>()

    lateinit var viewModel: AddPatientViewModel

    lateinit var updateEdit_name: TextInputEditText
    lateinit var updateEdit_contact: TextInputEditText
    lateinit var updateEdit_date: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_patient_update, container, false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddPatientViewModel::class.java)
    }

    private fun initView(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val toolbarTitle = view.findViewById<AppCompatTextView>(R.id.toolbarTitle)
        toolbarTitle.setText("UPDATE DETAIL")
        activity?.setActionBar(toolbar)
       val Edit_name = view.findViewById<TextInputEditText>(R.id.edit_name)
        val Edit_contact = view.findViewById<TextInputEditText>(R.id.edit_contact)
        val Edit_date = view.findViewById<TextInputEditText>(R.id.edit_date)
        val updateBtn = view.findViewById<Button>(R.id.btnUpdate)
        Edit_name.setText(args.CurrentUser.name)
        Edit_contact.setText(args.CurrentUser.contact)
        Edit_date.setText(args.CurrentUser.date)
        updateBtn.setOnClickListener {
            updateDetail()
        }
    }

    private fun updateDetail() {
        val name = updateEdit_name.text.toString()
        val contact = updateEdit_contact.text.toString()
        val date = updateEdit_date.text.toString()
        val user = User(name, contact, date)
        viewModel.updateUser(user)
        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
        val action = PatientUpdateLDialogFragmentDirections.actionPatientList()
        findNavController().navigate(action)
    }

}