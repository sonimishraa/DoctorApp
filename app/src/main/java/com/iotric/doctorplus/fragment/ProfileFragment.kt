package com.iotric.doctorplus.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DrProfileFragmentBinding
import com.iotric.doctorplus.model.response.GetDoctorByidResponse
import com.iotric.doctorplus.util.FileUtil
import com.iotric.doctorplus.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

const val PICK_IMAGE_REQUEST = 1
const val CAPTURE_IMAGE_REQUEST = 2


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var getDoctorId: GetDoctorByidResponse
    val viewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var binding: DrProfileFragmentBinding
    lateinit var multiPartImageBody: MultipartBody.Part

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DrProfileFragmentBinding.inflate(layoutInflater)
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
        binding.appbar.toolbarTitle.text = getString(R.string.menu_profile)
        val id = getDoctorId()
        viewModel.getDoctorApi(id, requireActivity().application)
    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.editProfile.setOnClickListener {
            val EditDocResult = getDoctorId
            val action =
                ProfileFragmentDirections.actionNavigationProfileToEditDoctorProfileFragment(
                    EditDocResult
                )
            findNavController().navigate(action)
        }
        binding.ivProfilePic.setOnClickListener {
            pickImage()
        }
    }

    private fun initObserver() {
        showLoading()
        val loginDrid = getDoctorId()
        Log.i("ProfileFragment", "_id:${id}")
        viewModel.getDoctorById.observe(requireActivity(), {
            getDoctorId = it
            getDoctorId.let {
                if (it._id == loginDrid) {
                    //Glide.with(requireContext()).load(it.profilepic).into(binding.ivProfilePic)
                    binding.tvName.text = it.doctorname
                    binding.tvGender.text = it.gender
                    binding.tvEmail.text = it.email
                    binding.tvContact.text = it.phone
                    binding.tvHospital.text = it.hospital
                    binding.tvStartTime.text = it.clinicstarttime
                    binding.tvEndTime.text = it.clinicendtime
                    binding.tvEducation.text = it.education
                    binding.tvExperience.text = it.experience
                    binding.tvTitle.text = it.title
                }
            }
            dismissLoading()
        })
        viewModel.uploadProfileImage.observe(viewLifecycleOwner, {
            dismissLoading()
            toastMessage("Sucessfully Uploaded")
        })

    }

    private fun getDoctorId(): String? {
        val sharePref = requireActivity().getSharedPreferences(
            getString(R.string.share_pref),
            Context.MODE_PRIVATE
        )
        val id = sharePref.getString("DoctorID", "")
        Log.i("ProfileFragment", "${id}")
        return id
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Result for Image Selection
            data?.data?.let {
                setImageUriOnPick(it)
                binding.ivProfilePic.setImageURI(it)
                /*if (validateFields()) {
                    uploadImage()
                }*/
            } ?: toastMessage("image invalid selection")
        }
    }

    private fun validateFields(): Boolean {
        var isAllFieldValidate = true

        if (::multiPartImageBody.isInitialized.not()) {
            isAllFieldValidate = false
        }
        return isAllFieldValidate
    }


    private fun setImageUriOnPick(uri: Uri) {
        val body = FileUtil.selectFileName(requireContext(), uri)
        body?.let {
            multiPartImageBody = it
        }
    }


    private fun uploadImage() {
        // add another part within the multipart request
        viewModel.uploadProfileApi(
            multiPartImageBody,
            requireActivity().application
        )
    }

    /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
  */

}