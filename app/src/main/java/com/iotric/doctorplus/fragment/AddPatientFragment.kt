package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.AddPatientFragmentBinding
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.util.UtilClass.day
import com.iotric.doctorplus.util.UtilClass.month
import com.iotric.doctorplus.util.UtilClass.year
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class AddPatientFragment : BaseFragment() {
    var hr = 0
    var min = 0

    var pickYear = 0
    var pickMonth = 0
    var pickDay = 0

    val viewModel: AddPatientViewModel by viewModels()
    private lateinit var binding: AddPatientFragmentBinding
    lateinit var image: String
    lateinit var name: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var address: String
    lateinit var appointDate: String
    lateinit var appointTime: String
    lateinit var uri: Uri

    lateinit var timePickerDialog:TimePickerDialog
    lateinit var datePickerDialog:DatePickerDialog

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
        initListener()
        initObserver()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.add_patient_toolbar_title)
    }

    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }
       /* binding.editUploadPris.setOnClickListener {
            pickImage()
        }*/
        binding.editDate.setOnClickListener {
            pickDate()
        }
        binding.editTime.setOnClickListener {
            timePick()
        }
        binding.btnAdd.setOnClickListener {
            registerPatient()
        }
    }

    private fun registerPatient() {
        showLoading()
        if (validateFields()) {
            val patient: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("patientname", name)
                .addFormDataPart("phone", phone)
                .addFormDataPart("email", email)
                .addFormDataPart("address",address)
                .addFormDataPart("nextvisitdate",appointDate)
                .addFormDataPart("nextvisittime",appointTime)
                .build()
            viewModel.getApi(patient, requireActivity().application)
            dismissLoading()
            findNavController().popBackStack()
           /* val multipartParams = MultipartParams.Builder()
            val filePath = File(uri?.path)
            val patient = multipartParams.addFile("images", filePath).add("email ",email).add("patientname ",name).add("phone",phone).add("address",address).add("nextvisitdate", appointDate).add("nextvisittime", appointTime)
            viewModel.getApiResponse(patient, requireActivity().application)*/

        } else {
            snackBar(getString(R.string.mendatory_field_message), binding.root)
            dismissLoading()
        }
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
            }
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        image = binding.editUploadPris.text.toString().trim()
        name = binding.editName.text.toString().trim()
        email = binding.editEmail.text.toString().trim()
        phone = binding.editPhone.text.toString().trim()
        appointDate = binding.editDate.text.toString().trim()
        address = binding.editAddress.text.toString().trim()
        appointTime = binding.editTime.text.toString().trim()

       /* if (image.isEmpty()) {
            binding.layoutEditUploadPris.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else
            binding.layoutEditUploadPris.setError(null)
*/
        if (appointDate.isEmpty()) {
            binding.layoutEditDate.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditDate.setError(null)
        }
        if (appointTime.isEmpty()) {
            binding.layoutEditTime.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutEditTime.setError(null)

        if (name.isEmpty()) {
            binding.layoutEditName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutEditName.setError(null)
        }

        if (email.isEmpty()) {
            binding.layoutEditEmail.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            binding.layoutEditEmail.setError(getString(R.string.invalid_email_message))
            isAllFieldValidate = false
        } else binding.layoutEditEmail.setError(null)

        if (phone.isEmpty()) {
            binding.layoutEditPhone.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else if (phone.length < 10) {
            binding.layoutEditPhone.setError(getString(R.string.Phone_number_validation))
            isAllFieldValidate = false
        } else binding.layoutEditPhone.setError(null)

        if (address.isEmpty()) {
            binding.layoutEditAddress.setError("Field Can't be Empty")
            isAllFieldValidate = false
        } else binding.layoutEditAddress.setError(null)

        return isAllFieldValidate
    }

    private fun timePick() {
        UtilClass.getTimeCalender()
        timePickerDialog =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    hr = hourOfDay
                    min = minute
                    Log.i("start time", "$hourOfDay: $minute")
                    val time = UtilClass.time(hr, min)
                    binding.editTime.setText(time)
                }
            }, hr, min, false)
        timePickerDialog.show()
    }

    private fun pickDate() {
        UtilClass.getDateCalendarInstance()
        datePickerDialog =
            DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    pickYear = year
                    pickMonth = month + 1
                    pickDay = dayOfMonth
                    val date = UtilClass.makeDateString(pickYear, pickMonth, pickDay)
                    binding.editDate.setText(date)
                }
            }, year, month, day)
        datePickerDialog.show()
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
            it.type = "image/*,image/png,image/pdf"
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