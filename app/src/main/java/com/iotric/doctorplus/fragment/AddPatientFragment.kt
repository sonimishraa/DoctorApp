package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.networks.MultipartParams
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class AddPatientFragment : BaseFragment() {

    val viewModel: AddPatientViewModel by viewModels()
    private lateinit var binding: AddPatientFragmentBinding
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var address: String
    lateinit var doctorid: String
    lateinit var nextvisit: String
    lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddPatientFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        binding.editUploadPris.setOnClickListener {
            pickImage()
        }
        binding.btnAdd.setOnClickListener {
            registerPatient()
        }
        binding.appbar.toolbarTitle.text = getString(R.string.add_patient_toolbar_title)
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
    }

    private fun registerPatient() {
        if (validateFields()) {
            val multipartParams = MultipartParams.Builder()
            val filePath = File(uri?.path)
            val patient = multipartParams.addFile("images", filePath).add("patientname", name)
                .add("phone", phone).add("address", address)
            viewModel.getApiResponse(patient, requireActivity().application)

        } else
            snackBar(getString(R.string.mendatory_field_message), binding.root)
    }

    private fun initObserver() {
        viewModel.registerPatientError.observe(requireActivity(), {
            Log.i("Error Message", "${it}")
            if (it != null) {
                snackBar("${it}", binding.root)
            }
        })

        viewModel.registerPatientItem.observe(requireActivity(), {
            Log.i("Succellfully created ", "${it}")
            toastMessage(it.message.toString())
            if (it != null) {
                findNavController().popBackStack()
            }
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        nextvisit = binding.editNextVisit.text.toString().trim()
        address = binding.editAddress.text.toString().trim()
        doctorid = binding.editDoctorid.text.toString().trim()


        /* if (doctorid.isEmpty()) {
             binding.layoutEditDoctorid.setError(getString(R.string.empty_field_message))
             isAllFieldValidate = false
         } else {
             binding.layoutEditName.setError(null)
         }*/

        if (name.isEmpty()) {
            binding.layoutEditName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }

        /*if (email.isEmpty()) {
            binding.layoutEditEmail.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError(getString(R.string.invalid_email_message))
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)*/

        if (phone.isEmpty()) {
            binding.layoutEditPhone.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditPhone.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditPhone.setError(null)

        /*  if (nextvisit.isEmpty()) {
              binding.layoutEditNextVisit.setError(getString(R.string.empty_field_message))
              isAllFieldValidate = false
          } else binding.layoutEditNextVisit.setError(null)
  */
        if (address.isEmpty()) {
            binding.layoutEditAddress.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }

    private fun pickImage() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogeView = inflater.inflate(R.layout.alter_dialoge_profile, null)
        builder.setCancelable(false)
        builder.setView(dialogeView)
        val alertDialoge = builder.create()
        alertDialoge.show()
        val ivCamera = dialogeView.findViewById<AppCompatTextView>(R.id.ivCamera)
        val ivImage = dialogeView.findViewById<AppCompatTextView>(R.id.ivImage)
        val tv_cancel = dialogeView.findViewById<AppCompatTextView>(R.id.tv_cancel)
        val tv_ok = dialogeView.findViewById<AppCompatTextView>(R.id.tv_ok)

        tv_cancel.setOnClickListener {
            alertDialoge.dismiss()
        }
        tv_ok.setOnClickListener {
            chooseImage()
            alertDialoge.cancel()
        }
        ivImage.setOnClickListener {
            chooseImage()
            alertDialoge.cancel()

        }
        ivCamera.setOnClickListener {
            if (checkRequestPermission())
                takePictureFromCamera()
            alertDialoge.cancel()
        }
    }

    private fun checkRequestPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission =
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.CAMERA),
                    2
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()

        } else
            toastMessage(getString(R.string.permission_granted_message))
    }

    private fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, 2)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes =
                arrayOf("image/jpg", "image/png", "image/pdf", "image/doc", "image/jpeg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData()!!
            val file = File(uri.path)

            try {
                //val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.editUploadPris.setText(file.name)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                uri = data.extras?.get("data") as Uri
                val file = File(uri.path)
                binding.editUploadPris.setText(file.name)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}