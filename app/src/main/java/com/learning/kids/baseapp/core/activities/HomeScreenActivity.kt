package com.learning.kids.baseapp.core.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.Touch
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.api.ApiInterface
import com.learning.kids.baseapp.api.RetrofitInstance
import com.learning.kids.baseapp.api.SessionManager
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.models.BigListResponse
import com.learning.kids.baseapp.data.models.UserResponse
import jp.co.dev.android.util.DialogUtil
import kotlinx.android.synthetic.main.activity_home_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        sessionManager = SessionManager(this)
        initUser()
        onClick()
    }


    private fun initUser() {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.getUserInfo("${sessionManager.fetchAuthToken()}", sessionManager.fetchAuthUserName().toString() ).enqueue(object:
            Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                DialogUtil.progressDlgHide()
                Toast.makeText(
                    this@HomeScreenActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.body()?.code == 200) {
                    DialogUtil.progressDlgHide()
                        txt_userName.text = response.body()?.result!!.data[0].name
                }
                else{
                    Toast.makeText(this@HomeScreenActivity, "Kết Nối Gặp Sự Cố! Vui Lòng Kiểm Tra Lại", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    private fun onClick() {
        btnFollow.setOnClickListener {
            DialogUtil.progressDlgShow(this, "Chờ xíu...")
            playSound("sounds_start_doctheo")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnFollow.text)
                intent.putExtra(Constants.KEY_HOME_FOLLOW, Constants.FOLLOW)
                startActivity(intent)
        }
        btnTouch.setOnClickListener {
            DialogUtil.progressDlgShow(this, "Chờ xíu...")
            playSound("sounds_start_chamvadoc")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnTouch.text)
                startActivity(intent)
        }
        btnTest.setOnClickListener {
            DialogUtil.progressDlgShow(this, "Chờ xíu...")
            playSound("sounds_start_test")
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@HomeScreenActivity, TestActivity::class.java)
                startActivity(intent)
            }, 2000)

        }
        btnSwipe.setOnClickListener {
            DialogUtil.progressDlgShow(this, "Chờ xíu...")
            playSound("sounds_start_vuotvadoc")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnSwipe.text)
                startActivity(intent)

        }
        btn_logout.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Bạn muốn đăng xuất?")
                .setPositiveButton("Đồng ý") {_,_->
                    doLogout()
                }
                .setCancelable(true)
                .show()
        }
    }
    private fun playSound(name: String) {
            var audioManager = AudioManager(applicationContext, name)
            audioManager.startSound()
    }
    fun doLogout() {
        sessionManager.clearCache()
        DialogUtil.progressDlgShow(this, "Chờ xíu...")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@HomeScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            DialogUtil.progressDlgHide()
        }, 1500)

    }
}