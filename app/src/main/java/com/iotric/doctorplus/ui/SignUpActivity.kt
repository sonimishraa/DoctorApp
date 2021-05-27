package com.iotric.doctorplus.ui

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iotric.doctorplus.databinding.ActivitySignUpBinding
import com.iotric.doctorplus.model.request.Doctor
import com.iotric.doctorplus.viewmodel.AddDoctorViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: AddDoctorViewModel
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        initViewModel()
    }

    private fun initView() {
        setActionBar(binding.toolbar)
        binding.btnAddDoctor.setOnClickListener {
            createUser()
        }

    }

    private fun createUser() {
         val  noWhiteSpace = "(?=\\s+$)"
        if (binding.editName.text.toString().trim().isEmpty() && binding.editEmail.text.toString()
                .trim().isEmpty() &&
            binding.editPhone.text.toString().trim()
                .isEmpty() && binding.editPassword.text.toString().trim().isEmpty() &&
            binding.editAddress.text.toString().trim().isEmpty()
        ) {
            binding.layoutEditName.setError("Field Can't be Empty")
            binding.layoutEditEmail.setError("Field Can't be Empty")
            binding.layoutEditPhone.setError("Field Can't be Empty")
            binding.layoutEditPassword.setError("Field Can't be Empty")
            binding.layoutEditAddress.setError("Field Can't be Empty")

        } else {
            binding.layoutEditName.setError(null)
            binding.layoutEditEmail.setError(null)
            binding.layoutEditPhone.setError(null)
            binding.layoutEditPassword.setError(null)
            binding.layoutEditAddress.setError(null)
        }
        if (binding.editPhone.text.toString().length < 10) {
            binding.layoutEditPhone.setError(" 10 Number Digit Require")
        }else{
            binding.layoutEditPhone.setError(null)
        }
        if (!binding.editEmail.text.toString().matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError("Invalid Email Id")
        } else {
            binding.layoutEditEmail.setError(null)
        }

        val doctor = Doctor(
            "",
            binding.editName.toString(),
            binding.editEmail.toString(),
            binding.editPhone.toString(),
            binding.editPassword.toString(),
            binding.editAddress.toString()
        )
        viewModel.getApiRequest(doctor)

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddDoctorViewModel::class.java)
        viewModel.addNewDoctor().observe(this, Observer {
            if (it == null) {
                Toast.makeText(this, "Failed to add Doctor", Toast.LENGTH_SHORT).show()
            } else {
                //binding.editName.text =
                Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
            }

        })
    }

}


