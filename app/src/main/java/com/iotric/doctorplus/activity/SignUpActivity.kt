package com.iotric.doctorplus.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iotric.doctorplus.databinding.ActivitySignUpBinding
import com.iotric.doctorplus.model.request.Doctor
import com.iotric.doctorplus.viewmodel.AddDoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    @SuppressLint("UseSupportActionBar")
    private fun initView() {
        setActionBar(binding.toolbar)
        binding.btnAddDoctor.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()

        if(name.isEmpty()) {
            binding.layoutEditName.setError("Field Can't be Empty")
        }else   binding.layoutEditName.setError(null)

        if(email.isEmpty()) {
            binding.layoutEditEmail.setError("Field Can't be Empty")
        }
        else if (password.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError("Invalid Email Id")
        }
        else binding.layoutEditEmail.setError(null)

        if(phone.isEmpty()) {
            binding.layoutEditPhone.setError("Field Can't be Empty")
        }else if(phone.length < 10) {
            binding.layoutEditPhone.setError(" 10 Number Digit Require")
        } else binding.layoutEditPhone.setError(null)

        if (password.isEmpty()) {
                binding.layoutEditPassword.setError("Field Can't be Empty")
            } else binding.layoutEditPassword.setError(null)

        val doctor = Doctor(
            "",name,email,phone,password,binding.editAddress.toString()
        )
        viewModel.getApiRequest(doctor)

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AddDoctorViewModel::class.java)
        viewModel.addNewDoctor().observe(this, {
            if (it == null) {
                Toast.makeText(this, "Failed to add Doctor", Toast.LENGTH_SHORT).show()
            } else {
                //binding.editName.text =
                Toast.makeText(this, "Successfully Created", Toast.LENGTH_SHORT).show()
            }

        })
    }

}


