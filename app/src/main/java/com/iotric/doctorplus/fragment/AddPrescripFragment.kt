package com.iotric.doctorplus.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.github.drjacky.imagepicker.ImagePicker
import androidx.activity.result.contract.ActivityResultContracts
import com.iotric.doctorplus.databinding.AddPrescripFragmentBinding
import com.iotric.doctorplus.util.FileUtil
import com.iotric.doctorplus.viewmodel.AddPrescripViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class AddPrescripFragment : BaseFragment() {
    lateinit var binding: AddPrescripFragmentBinding
    val viewModel: AddPrescripViewModel by viewModels()
    val args: AddPrescripFragmentArgs by navArgs()
    lateinit var prescripName: String
    lateinit var description: String
    lateinit var multiPartImageBody: MultipartBody.Part


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddPrescripFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = "ADD NEW PRESCRIP"

    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }

        binding.addPrescripButton.setOnClickListener {
            imagepick()
        }
        binding.uploadPrescrip.setOnClickListener {
            if (validateFields()) {
                reportUpload()
            }
        }
    }

    private fun initObserver() {
        viewModel.uploadPrescrip.observe(requireActivity(), {
            it?.let {
                toastMessage("${it.message}")
                view?.post {
                    findNavController().popBackStack()
                }

            }
        })
        viewModel.apiErrorMessage.observe(requireActivity(), {
            Log.i("UploadImage", "Error Message:${it}")
            toastMessage("${it}")
        })
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true
        prescripName = binding.prescripName.text.toString()
        description = binding.description.text.toString().trim()

        if (prescripName.isEmpty()) {
            binding.layoutPrescripName.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else {
            binding.layoutPrescripName.setError(null)
        }
        if (description.isEmpty()) {
            binding.layoutDescription.setError(getString(R.string.empty_field_message))
            isAllFieldValidate = false
        } else binding.layoutDescription.setError(null)

        if (::multiPartImageBody.isInitialized.not()) {
            isAllFieldValidate = false
        }
        return isAllFieldValidate
    }

    private fun imagepick() {
        ImagePicker.with(requireActivity())
            .crop()
            .cropOval()
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                binding.image.setImageURI(uri)
                setImageUriOnPick(uri)
            }
        }

    private fun setImageUriOnPick(uri: Uri) {
        val body = FileUtil.selectFileName(requireContext(), uri)
        body?.let {
            multiPartImageBody = it
        }
    }

    private fun setCameraClickUri(uri: Uri) {
        val body = FileUtil.cameraClickFile(requireContext(), uri)
        body?.let {
            multiPartImageBody = it
        }
    }


    private fun reportUpload() {
        // add another part within the multipart request
        val fullName =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.prescripName.text.toString()
            )
        val patientId =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                args.patientId.id.orEmpty()
            )
        val description =
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.description.text.toString()
            )
        viewModel.getUploadPrescripApi(
            multiPartImageBody,
            fullName,
            patientId,
            description,
            requireActivity().application
        )
    }
}