package com.iotric.doctorplus.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.ProfileFragmentBinding
import com.iotric.doctorplus.viewmodel.AddPatientViewModel
import com.iotric.doctorplus.viewmodel.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.ArrayList

const val PICK_IMAGE_REQUEST = 1

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    val viewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        viewModel.getDoctorApi(requireActivity().application)
        binding.appbar.toolbarTitle.text = getString(R.string.menu_profile)
        binding.appbar.toolbar.setNavigationOnClickListener {view ->
            findNavController().popBackStack()
        }
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_editDoctorProfileFragment)
        }
        binding.ivProfile.setOnClickListener {
            chooseProfilePic()
        }
    }

    private fun initObserver() {
        val sharedPreferences = activity?.getSharedPreferences(getString(R.string.share_pref), Context.MODE_PRIVATE)
        val id = sharedPreferences?.getString("_id", "")
        viewModel.getDoctorById.observe(requireActivity(), Observer {
            it.doctors.forEachIndexed { index, doctorsItem ->
               val doctorId =  doctorsItem.id
                if(doctorId == id){
                    binding.tvName.text = doctorsItem.doctorname
                    binding.tvType.text = doctorsItem.role
                    binding.tvEmail.text = doctorsItem.email
                    binding.tvPhone.text = doctorsItem.phone
                    //binding.tvAddress.text = doctorsItem.adddress.toString()
                }
            }
        })
    }


    private fun chooseProfilePic() {
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
            chooseImageReq()
            alertDialoge.cancel()
        }
        ivImage.setOnClickListener {
            chooseImageReq()
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
            Toast.makeText(requireContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show()
    }

    private fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, 2)
        }catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }
    }

    private fun chooseImageReq() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.getData() != null
        ) {
            val uri: Uri? = data.getData()

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.ivProfile.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        if(requestCode == 2 && resultCode == Activity.RESULT_OK  && data != null && data.getData() != null){

            try {
                val imageBitmap = data.extras?.get("data") as Bitmap
                binding.ivProfile.setImageBitmap(imageBitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}