package com.iotric.doctorplus.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.adapter.PatientReportAdapter
import com.iotric.doctorplus.databinding.PatientRecordFragmentsBinding
import com.iotric.doctorplus.viewmodel.ViewPatientRecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class PatientRecordFragment : BaseFragment() {

    val args: PatientRecordFragmentArgs by navArgs()

    private lateinit var binding: PatientRecordFragmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = PatientRecordFragmentsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        setArgs()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_record)
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.ivProfilePic.setOnClickListener {
            pickImage()
        }
        binding.tvAddReport.setOnClickListener {
            val patientId = args.result
            val action =
                PatientRecordFragmentDirections.actionPatientRecordFragmentToReportUploadFragment(
                    patientId
                )
            findNavController().navigate(action)
        }
        binding.tvViewReport.setOnClickListener {
            val id = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToViewPatientReportFragment(
                        patientId = id
                    )
                findNavController().navigate(action)
        }
    }

    private fun setArgs() {
        val argsItem = args.result
        binding.tvPatientId.text = argsItem.uniqueid
        binding.tvName.text = argsItem.pname
        binding.tvContact.text = argsItem.pphone
        binding.tvEmail.text = argsItem.pemail
        binding.tvAge.text = argsItem.age + " " + "Years"
        if (argsItem.gender == "m") {
            binding.tvGender.text = "Male"
        } else
            binding.tvGender.text = "Female"
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.getData() != null
        ) {
            val uri: Uri? = data.getData()

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.ivProfilePic.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            try {
                val imageBitmap = data.extras?.get("data") as Bitmap
                binding.ivProfilePic.setImageBitmap(imageBitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

