package com.iotric.doctorplus.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.PatientRecordFragmentsBinding
import com.iotric.doctorplus.util.DateTimeUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class PatientRecordFragment : BaseFragment(), PopupMenu.OnMenuItemClickListener {

    val args: PatientRecordFragmentArgs by navArgs()
    lateinit var multiPartImageBody: MultipartBody.Part

    private lateinit var binding: PatientRecordFragmentsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = PatientRecordFragmentsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        setArgs()
    }

    private fun initView() {
        binding.appbar.toolbarTitle.text = getString(R.string.patient_record)
    }

    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.floatingActionButton.setOnClickListener {
            imagepick()
        }
        binding.tvPrescription.setOnClickListener {
            prisDropDown()

        }
        binding.tvReport.setOnClickListener {
            reportDropdown()
        }
    }

    private fun imagepick() {
        ImagePicker.with(requireActivity())
            .crop()
            .cropOval()
            .galleryMimeTypes(
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
                binding.profileImage.setImageURI(uri)
            }
        }

    private fun setArgs() {
        val argsItem = args.result
        Glide.with(requireContext()).load(argsItem.pic).into(binding.profileImage)
        binding.tvPatientId.text = argsItem.uniqueid
        binding.startDate.text = DateTimeUtil.getSimpleDateFromUtc(argsItem.createdAt)
        binding.tvName.text = argsItem.pname
        binding.tvContact.text = argsItem.pphone
        binding.tvEmail.text = argsItem.pemail
        binding.tvBloogGroup.text = argsItem.bloodgroup
        binding.tvAge.text = argsItem.age + " " + "Years"
        argsItem.address?.forEach {
            binding.tvAddress.text = it

        }
        if (argsItem.gender == "m") {
            binding.tvGender.text = "Male"
        } else
            binding.tvGender.text = "Female"
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.add_manual_prescrip -> {
                val patientId = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToAddPrescripFragment(
                        patientId
                    )
                findNavController().navigate(action)
            }
            R.id.add_digital_prescrip -> {
                val patientId = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToDigitalPrescriptionFragment(
                        patientId
                    )
                findNavController().navigate(action)

            }
            R.id.view_prescrip -> {
                val id = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToViewPrescripFragment(
                        id
                    )
                findNavController().navigate(action)
                /* val action =
                     PatientRecordFragmentDirections.actionPatientRecordFragmentToPrescriptionFragment()
                 findNavController().navigate(action)*/
            }
            R.id.add_report -> {
                val patientId = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToReportUploadFragment(
                        patientId
                    )
                findNavController().navigate(action)
            }
            R.id.view_report -> {
                val id = args.result
                val action =
                    PatientRecordFragmentDirections.actionPatientRecordFragmentToViewPatientReportFragment(
                        patientId = id
                    )
                findNavController().navigate(action)

            }
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun reportDropdown() {
        val popup = PopupMenu(requireContext(), binding.tvReport)
        popup.getMenuInflater().inflate(R.menu.report_dropdown, popup.getMenu())
        MenuCompat.setGroupDividerEnabled(popup.menu, true)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    private fun prisDropDown() {
        val popup = PopupMenu(requireContext(), binding.tvPrescription)
        popup.getMenuInflater().inflate(R.menu.prescription_dropdown, popup.getMenu())
        MenuCompat.setGroupDividerEnabled(popup.menu, true)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Result for Image Selection
            data?.data?.let {
                setImageUriOnPick(it)
                binding.ivProfilePic.setImageURI(it)
            } ?: toastMessage("image invalid selection")
        }
        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivProfilePic.setImageBitmap(imageBitmap)
        }
    }

    private fun setImageUriOnPick(uri: Uri) {
        val body = FileUtil.selectFileName(requireContext(), uri)
        body?.let {
            multiPartImageBody = it
        }
    }
*/
}