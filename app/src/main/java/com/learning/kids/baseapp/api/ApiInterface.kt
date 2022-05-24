package com.learning.kids.baseapp.api

import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.models.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST(Constants.SIGNUP_URL)
    fun signUp(@Body request: RegisterRequest): Call<LoginResponse>

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET(Constants.LIST_TOP_PIC_URL)
    fun getListTopPic(@Header("x_authorization") token: String): Call<BigListResponse>

}
class RetrofitInstance {
    companion object {
        val BASE_URL: String = Constants.BASE_URL

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}