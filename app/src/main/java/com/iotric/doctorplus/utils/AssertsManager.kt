package com.rakuten.common.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson

object AssertsManager {

    fun openStringFile(fileName: String, context: Context): String {
        return try {
            val reader = context.assets.open(fileName).bufferedReader()

            reader.useLines { sequence: Sequence<String> ->
                sequence.reduce { a, b -> "$a\n$b" }
            }
        } catch (e: Exception) {
            Log.i("AssertsManager", "${e.message}")
            ""
        }
    }

    fun <T> convertJsonStringToObject(jsonString: String, clazz: Class<T>): T =
        Gson().fromJson(jsonString, clazz)
}
