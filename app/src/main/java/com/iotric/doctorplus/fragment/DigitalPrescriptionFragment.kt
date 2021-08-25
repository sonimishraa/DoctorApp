package com.iotric.doctorplus.fragment

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iotric.doctorplus.R
import com.iotric.doctorplus.databinding.DigitalPrescriptionFragmentBinding
import me.panavtec.drawableview.DrawableView
import me.panavtec.drawableview.DrawableViewConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1

class DigitalPrescriptionFragment : BaseFragment() {

    lateinit var config: DrawableViewConfig
    lateinit var drawableView: DrawableView
    val args: DigitalPrescriptionFragmentArgs by navArgs()
    lateinit var binding: DigitalPrescriptionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DigitalPrescriptionFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview()
        initListener()
    }

    private fun initview() {
        binding.appbar.toolbarTitle.text = "WRITE PRESCRIPTION"
        drawableView = binding.drawingPad
        binding.draw.setOnClickListener {
            config = DrawableViewConfig()
            config.strokeColor = resources.getColor(android.R.color.black)
            config.isShowCanvasBounds =
                true // If the view is bigger than canvas, with this the user will see the bounds (Recommended)
            config.strokeWidth = 5.0f
            config.minZoom = 1.0f
            config.maxZoom = 3.0f
            config.canvasHeight = 1920
            config.canvasWidth = 1920
           drawableView.setConfig(config)
        }

    }
    private fun initListener() {
        binding.appbar.navigationBtn.setOnClickListener { view ->
            findNavController().popBackStack()
        }
        binding.color.setOnClickListener {
            /* val bitmap: Bitmap = resources.getDrawable(R.drawable.rose).toBitmap(1920, 1920, null)
             //val palette: Palette = Palette.from(bitmap).maximumColorCount(numberOfColors).generate()
             createPaletteSync(bitmap)*/
            val random: Random = Random()
            config.strokeColor =
                Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
        binding.erase.setOnClickListener {
            drawableView.clear()
        }
        binding.save.setOnClickListener {
            drawableView.obtainBitmap()
            dialogue()
        }
        binding.back.setOnClickListener {

            drawableView.undo()
        }
    }

    private fun dialogue() {
        val saveDialog: android.app.AlertDialog.Builder =
            android.app.AlertDialog.Builder(requireContext())
        saveDialog.setTitle("Save Prescription")
        saveDialog.setMessage("Save Prescription to device Gallery?")
        saveDialog.setPositiveButton(
            "Yes",
            DialogInterface.OnClickListener { dialog, which -> // First check for permissions
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                    )
                    saveDrawing()

                } else {
                    saveDrawing()
                    val patientId = args.patiensItem
                    val action =
                        DigitalPrescriptionFragmentDirections.actionDigitalPrescriptionFragmentToAddPrescripFragment(
                            patientId
                        )
                    findNavController().navigate(action)
                }

            })
        saveDialog.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> // First check for permissions
                dialog.dismiss()

            })
        saveDialog.show()
    }

    private fun saveDrawing() {
        drawableView.setDrawingCacheEnabled(true)
        val bitmap: Bitmap = drawableView.obtainBitmap()
        drawableView.setConfig(config)
        val storedImagePath: File = generateImagePath("Images", "jpg")!!
        if (compressAndSaveImage(storedImagePath, drawableView.drawToBitmap())) {
            val savedToast = Toast.makeText(
                requireContext(),
                "Prescription saved to Gallery!", Toast.LENGTH_SHORT
            )
            savedToast.show()
        } else {
            toastMessage("Oops! Prescription could not be saved.")
        }

        val url: Uri =
            activity?.contentResolver?.let { addImageToGallery(it, "jpg", storedImagePath) }!!
        drawableView.destroyDrawingCache()
    }

    private fun addImageToGallery(cr: ContentResolver, imgType: String, filepath: File): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "image-drawingfun")
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "drawingfun")
        values.put(MediaStore.Images.Media.DESCRIPTION, "")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/$imgType")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.DATA, filepath.toString())
        return cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    private fun generateImagePath(title: String, imgType: String): File? {
        val sdf = SimpleDateFormat("yyyyMMdd-hhmmss")
        return File(getImagesDirectory(), title + "_" + sdf.format(Date()) + "." + imgType)
    }

    private fun getImagesDirectory(): File? {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + File.separator + "Downloads"
        )
        if (!file.mkdirs() && !file.isDirectory) {
            System.err.println("Directory could not be created")
        }
        return file
    }

    private fun compressAndSaveImage(file: File, bitmap: Bitmap): Boolean {
        var result = false
        try {
            val fos = FileOutputStream(file)
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos).also { result = it }) {
                println("Compression success")
            }
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    saveDrawing()
                } else {
                    // Permission denied
                    toastMessage("Storage permissions denied")
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                return
            }
        }
    }

}