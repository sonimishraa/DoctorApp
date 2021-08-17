package com.iotric.doctorplus.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


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

    fun cameraClickFile(context: Context, uri: Uri): MultipartBody.Part? {
        val tempFile = createTempFile(context, "hello", ".jpg")
        val filePath = tempFile.absolutePath
        val requestFile: RequestBody =
            File(filePath).asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("images", "hello.jpg", requestFile)
        return body
    }
}