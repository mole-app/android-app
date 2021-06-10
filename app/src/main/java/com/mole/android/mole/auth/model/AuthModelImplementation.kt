package com.mole.android.mole.auth.model

import androidx.lifecycle.liveData
import com.mole.android.mole.auth.data.AuthData
import com.mole.android.mole.auth.data.AuthDataVkLogin
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthModelImplementation : AuthModel {


    private val data: AuthData = AuthData("vasiapupkin")
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun addUser(user: AuthData): Boolean {
        return user.login != "first"
    }

    override fun getUser(): AuthData {

        val service = RetrofitBuilder.retrofitService
        scope.launch {
            service.getVkAuth().enqueue(object : Callback<AuthDataVkLogin> {
                override fun onFailure(call: Call<AuthDataVkLogin>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AuthDataVkLogin>,
                    response: Response<AuthDataVkLogin>
                ) {
                    val body = response.body()
                    if (body is AuthDataVkLogin) {
                        response.body() as AuthDataVkLogin
                    }
                }
            })
        }
        return data
    }

//    suspend fun fetchDocs() {                      // Dispatchers.Main
//        val result = get("developer.android.com")  // Dispatchers.Main
//        show(result)                               // Dispatchers.Main
//    }

    suspend fun get(url: String) =                 // Dispatchers.Main
        withContext(Dispatchers.IO) {              // Dispatchers.IO (main-safety block)
            /* perform network IO here */          // Dispatchers.IO (main-safety block)
        }                                          // Dispatchers.Main
}
