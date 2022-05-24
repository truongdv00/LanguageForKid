package com.learning.kids.baseapp.core.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.api.ApiInterface
import com.learning.kids.baseapp.api.RetrofitInstance
import com.learning.kids.baseapp.api.SessionManager
import com.learning.kids.baseapp.data.models.LoginRequest
import com.learning.kids.baseapp.data.models.LoginResponse
import com.learning.kids.baseapp.data.models.PostsResponse
import com.learning.kids.baseapp.data.models.RegisterRequest
import jp.co.dev.android.util.DialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onClick()
        sessionManager = SessionManager(this)


    }

    private fun onClick() {
        registerAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            if (edtUserName.text.toString() != "" && edtPassWord.text.toString() != "") {
                DialogUtil.progressDlgShow(this, "Loading...")
                doLogin(edtUserName.text.toString(), edtPassWord.text.toString())
            } else {
                Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doLogin(username: String,
                         password: String){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val registerInfo = LoginRequest(username = username , password = password)

        retIn.login(registerInfo).enqueue(object :
            Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                DialogUtil.progressDlgHide()
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body()?.code == 200) {
                    sessionManager.saveAuthToken(response.body()?.result!!.accessToken)
                    val intent = Intent(this@LoginActivity, HomeScreenActivity::class.java)
                    startActivity(intent)
                    DialogUtil.progressDlgHide()
                }
                else{
                    DialogUtil.progressDlgHide()
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }


}