package com.iotric.doctorplus.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.FragmentPrescriptionBinding
import java.io.IOException


class PrescriptionFragment : BaseFragment() {

    private lateinit var binding:FragmentPrescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding=FragmentPrescriptionBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.ivprisDoc.setOnClickListener {
            chooseImage()
        }

    }

    private fun chooseImage() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            val uri: Uri? = data.getData()

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,uri)
                binding.ivprisDoc.setImageBitmap(bitmap)

            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }
}