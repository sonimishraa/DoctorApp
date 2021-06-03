package com.rakuten.common.core.intercept

import android.app.Application
import android.util.Log
import com.iotric.doctorplus.AppApplication
import com.rakuten.common.utils.AssertsManager
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation
import javax.inject.Inject

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class MockJson(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class FakeUnAuth

class MockInterceptor @Inject constructor(val context: Application) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var message: String? = null
        val request = chain.request()
        request.tag(Invocation::class.java)?.let {
            it.method().getAnnotation(MockJson::class.java)?.let { tag ->
                message = loadJson(tag.value, context)
                Log.i("Mock data", "message = $message")
            }
        }

        message?.let {
            return chain.proceed(request)
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(it)
                .body(it.toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("Content-Type", "application/json")
                .build()
            /*
             If there is no annotation for @MockJson we should not change any thing
              */
        } ?: return chain.proceed(request)
    }

    private fun loadJson(filename: String, context: Application) =
        AssertsManager.openStringFile(filename, context)
}
