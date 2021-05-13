package com.iotric.doctorplus.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.iotric.doctorplus.R
import java.io.IOException
import java.net.URI


class PrescriptionFragment : BaseFragment() {

    lateinit var ivpriscription: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_prescription, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        ivpriscription = view.findViewById(R.id.ivprisDoc)
        ivpriscription.setOnClickListener {
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
                ivpriscription.setImageBitmap(bitmap)

            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }
}