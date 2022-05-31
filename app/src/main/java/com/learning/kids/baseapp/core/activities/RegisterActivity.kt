package com.learning.kids.baseapp.core.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.api.ApiInterface
import com.learning.kids.baseapp.api.RetrofitInstance
import com.learning.kids.baseapp.data.models.LoginRequest
import com.learning.kids.baseapp.data.models.LoginResponse
import com.learning.kids.baseapp.data.models.RegisterRequest
import jp.co.dev.android.util.DialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onClick()
    }

    private fun onClick() {
        btnRegister.setOnClickListener {
            if (edtEmail.text.toString() != "" && edtPass.text.toString() != "" && edtUser.text.toString() != "" && edtName.text.toString() != "") {
                DialogUtil.progressDlgShow(this, "Chờ xíu...")
                doSignUp(edtEmail.text.toString(), edtUser.text.toString(), edtPass.text.toString(), edtName.text.toString())
            } else {
                Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show()
            }
        }
        backToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun doSignUp(email: String, username: String,
                         password: String, name: String){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val registerInfo = RegisterRequest(email = email, username = username , password = password, name = name)

        retIn.signUp(registerInfo).enqueue(object :
            Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                DialogUtil.progressDlgHide()
                Toast.makeText(
                    this@RegisterActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body()?.code == 200) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    DialogUtil.progressDlgHide()
                }
                else{
                    DialogUtil.progressDlgHide()
                    Toast.makeText(this@RegisterActivity, "Thành Phần Thông Tin Đã Tồn Tại! \nVui Lòng Kiểm Tra Lại", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }
}