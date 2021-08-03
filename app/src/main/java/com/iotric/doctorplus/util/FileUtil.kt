package com.iotric.doctorplus.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


object FileUtil {
    fun selectFileName(context: Context, uri: Uri): MultipartBody.Part? {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            // STEP 1: Create a tempFile for storing the image from scoped storage.
            val tempFile = createTempFile(context, "hello", ".jpg")

            // STEP 2: copy inputStream into the tempFile
            copyStreamToFile(inputStream, tempFile)

            // STEP 3: get file path from tempFile for further upload process.
            val filePath = tempFile.absolutePath

            val requestFile: RequestBody =
                File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body: MultipartBody.Part =
                MultipartBody.Part.createFormData("images", "hello.jpg", requestFile)
            return body
        }
        return null
    }

    @Throws(IOException::class)
    fun createTempFile(context: Context, fileName: String?, extension: String?): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File(storageDir, "$fileName.$extension")
    }

    fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

    fun saveImage(
        context: Context,
        folderName: String,
        fileName: String
    ): Uri? {
        var fos: OutputStream? = null
        var imageFile: File? = null
        var imageUri: Uri? = null
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                contentValues.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + folderName
                )
                imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (imageUri == null) throw IOException("Failed to create new MediaStore record.")
                fos = resolver.openOutputStream(imageUri)
            } else {
                val imagesDir = File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                    ).toString() + File.separator + folderName
                )
                if (!imagesDir.exists()) imagesDir.mkdir()
                imageFile = File(imagesDir, "$fileName.png")
                fos = FileOutputStream(imageFile)
            }
          /*  if (!bitmap.compress(
                    Bitmap.CompressFormat.PNG,
                    100,
                    fos
                )
            ) throw IOException("Failed to save bitmap.")*/
            fos?.flush()
        } finally {
            if (fos != null) fos.close()
        }
        if (imageFile != null) { //pre Q
            MediaScannerConnection.scanFile(context, arrayOf(imageFile.toString()), null, null)
            imageUri = Uri.fromFile(imageFile)
        }
        return imageUri
    }
}