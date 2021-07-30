package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DrProfileFragmentBinding
import com.iotric.doctorplus.databinding.ProfileFragmentBinding
import com.iotric.doctorplus.model.response.GetDoctorByidResponse
import com.iotric.doctorplus.util.UtilClass
import com.iotric.doctorplus.util.UtilClass.hr
import com.iotric.doctorplus.util.UtilClass.min
import com.iotric.doctorplus.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

const val PICK_IMAGE_REQUEST = 1
const val CAPTURE_IMAGE_REQUEST = 2


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var getDoctorId:GetDoctorByidResponse
    val viewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var binding: DrProfileFragmentBinding

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
        binding.appbar.toolbar.setNavigationOnClickListener { view ->
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
        viewModel.getDoctorById.observe(requireActivity(), Observer {
            getDoctorId = it
            getDoctorId.let {
                if ( it._id == loginDrid) {
                   // Glide.with(requireContext()).load(it.profilepic).into(binding.ivProfilePic)
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
    }

    private fun getDoctorId(): String? {
        val sharePref = requireActivity().getSharedPreferences(
            getString(R.string.share_pref),
            Context.MODE_PRIVATE
        )
        val id = sharePref.getString("DoctorID", "")
        Log.i("ProfileFragment","${id}")
        return id
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