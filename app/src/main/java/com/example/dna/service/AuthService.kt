package com.example.dna.service

import com.example.dna.model.User
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class AuthService {
    private val url: String = "http:localhost:1000/api/auth/"
    private var status: Int = 0

    fun register(user: User):Int{
        val gson = Gson()
        val userJson = gson.toJson(user)
        print(userJson)
        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

        val body: RequestBody = userJson.toRequestBody(JSON)

        val request: Request = Request.Builder()
            .url(url + "register")
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // Handle the error
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string() // Get response body as string
                    print(responseString)
                    status = response.code
                } else {
                    // Handle the error based on the status code
                }
            }
        })
        return status
    }
}