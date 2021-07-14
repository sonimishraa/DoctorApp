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
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.UploadPatientReportFragmentBinding
import com.iotric.doctorplus.networks.MultipartParams
import com.iotric.doctorplus.util.UtilClass.getFileName
import com.iotric.doctorplus.viewmodel.UploadPatientReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class UploadPatientReportFragment : BaseFragment() {

    val viewModel: UploadPatientReportViewModel by viewModels()
    lateinit var binding: UploadPatientReportFragmentBinding
    var selectedImage: Uri? = null
    val args: UploadPatientReportFragmentArgs by navArgs()
    lateinit var repoName: String
    lateinit var reportDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UploadPatientReportFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.upload_report_fragment_title)
    }


    private fun initListener() {
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.addReportButton.setOnClickListener {
            pickImage()
        }
        binding.uploadReport.setOnClickListener {
            reportUpload()
        }
    }


    private fun initObserver() {
        viewModel.uploadReport.observe(requireActivity(), {

            it?.let {
                toastMessage("${it.message}")
                findNavController().popBackStack()
            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {

            toastMessage("${it}")
        })
    }

    private fun reportUpload() {
        if (selectedImage == null) {
            snackBar("Select An Image", binding.root)
        } else {
            if (validateFields()) {
                //imageUpload()
                val multipartParams = MultipartParams.Builder()
                val filePath = File(selectedImage?.path)
                val report =
                    multipartParams.addFile("images", filePath).add("patientid", args.patientId.id)
                        .add("reportname", repoName)
                        .add("dateofreport", reportDate)
                viewModel.getUploadReportApi(report, requireActivity().application)
            }
        }
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        repoName = binding.reportName.text.toString()
        reportDate = binding.reportDate.text.toString().trim()

        if (repoName.isEmpty()) {
            binding.layoutReportName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutReportName.setError(null)
        }
        if (reportDate.isEmpty()) {
            binding.layoutReoprtDate.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutReoprtDate.setError(null)

        return isAllFieldValidate
    }


    private fun imageUpload() {
        val contentResolver = requireContext().contentResolver
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImage!!, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(
            Environment.getRootDirectory(),
            contentResolver.getFileName(selectedImage!!)
        )
        Log.i("UploadPatientFragment", "file${file}")
        val outputStream = FileOutputStream(file)
        val inchannel = inputStream.channel
        val outchannel = outputStream.channel
        inchannel.transferTo(0, inchannel.size(), outchannel)
        inputStream.close()
        outputStream.close()
        Log.i("UploadPatientFragment", "outpustream${outputStream}")
        inputStream.copyTo(outputStream, DEFAULT_BUFFER_SIZE)
        //binding.progressbar.progress = 0
        Log.i("Upload", "patientId:${args.patientId.id}")
        /* val body = UploadReportRequestBody(file, "images", this)
         val patientReportImage = MultipartBody.Part.createFormData("images", file.name, body)
         val patientid = RequestBody.create("patientid".toMediaTypeOrNull(), args.patientId.id!!)
         viewModel.getUploadReportApi(
             patientReportImage,
             patientid,
             requireActivity().application
         )*/
    }

    /* override fun onProgressUpdate(percentage: Int) {
         binding.progressbar.progress = percentage
     }
 */
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
        //intent.setType("image/*")
        intent.setAction(Intent.ACTION_PICK).also {
            it.type = "image/png"
            val mimeTypes =
                arrayOf("image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData()!!
            val file = File(selectedImage?.path).absolutePath

            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImage)
                binding.image.setImageURI(selectedImage)
                binding.reportName.setText(file)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            try {
                selectedImage = data.extras?.get("data") as Uri
                val file = File(selectedImage?.path).absolutePath
                binding.reportName.setText(file)
                binding.image.setImageURI(selectedImage)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
