package com.iotric.doctorplus.ui.fragment

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
import com.iotric.doctorplus.ui.viewmodel.ProfileFragmentViewModel
import java.io.IOException

const val PICK_IMAGE_REQUEST = 1

class ProfileFragment : BaseFragment() {

    lateinit var ivProfile: AppCompatImageView
    lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle("PROFILE")
        initView(view)
    }

    private fun initView(view: View) {
        ivProfile = view.findViewById(R.id.ivProfile)
        ivProfile.setOnClickListener {
            chooseImageReq()
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
                ivProfile.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


}