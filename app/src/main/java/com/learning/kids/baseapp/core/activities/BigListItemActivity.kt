package com.learning.kids.baseapp.core.activities

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.adapter.BigListFollowAdapter
import com.learning.kids.baseapp.core.adapter.DetailBigListAlphabetAdapter
import com.learning.kids.baseapp.data.database.MyDatabase
import com.learning.kids.baseapp.data.models.DataFollow
import kotlin.math.log
import android.database.sqlite.SQLiteDatabase
import android.widget.TextView
import com.learning.kids.baseapp.data.Constants
import kotlinx.android.synthetic.main.activity_big_list_follow.*
import org.w3c.dom.Text
import android.view.View

import android.view.animation.LinearInterpolator

import android.view.animation.Animation

import android.view.animation.RotateAnimation
import android.widget.Toast
import com.learning.kids.baseapp.api.ApiInterface
import com.learning.kids.baseapp.api.RetrofitInstance
import com.learning.kids.baseapp.api.SessionManager
import com.learning.kids.baseapp.data.models.BigListResponse
import com.learning.kids.baseapp.data.models.DataBigList
import com.learning.kids.baseapp.data.models.LoginResponse
import jp.co.dev.android.util.DialogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BigListItemActivity: AppCompatActivity() {
    private var recyclerview: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var bigListAdapter: BigListFollowAdapter? = null
    var tvtitle: TextView? = null
    var context: Context? = null
    var key: String? = null
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_list_follow)
        sessionManager = SessionManager(this)
        getData()
        recyclerview = findViewById(R.id.listFollow)
        tvtitle = findViewById(R.id.txt_title)
        this.context = context
        initView()
        onClick()
    }

    private fun getData() {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retIn.getListTopPic("${sessionManager.fetchAuthToken()}").enqueue(object:
            Callback<BigListResponse> {
            override fun onFailure(call: Call<BigListResponse>, t: Throwable) {
                DialogUtil.progressDlgHide()
                Toast.makeText(
                    this@BigListItemActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<BigListResponse>, response: Response<BigListResponse>) {
                if (response.body()?.code == 200) {
                    initRecyclerview(response.body()?.result!!.data)
                }
                else{
                    Toast.makeText(this@BigListItemActivity, "Kết Nối Gặp Sự Cố! Vui Lòng Kiểm Tra Lại", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }

    private fun onClick() {
        btnBack.setOnClickListener {
            val intent = Intent(this@BigListItemActivity, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initView() {
        var title = intent.getStringExtra(Constants.KEY_HOME)
        key = intent.getStringExtra(Constants.KEY_HOME_FOLLOW)
        txt_title.text = title
    }

    private fun initRecyclerview(arrayList: ArrayList<DataBigList>) {
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerview?.layoutManager = gridLayoutManager
        recyclerview?.setHasFixedSize(true)
        bigListAdapter = BigListFollowAdapter(applicationContext, arrayList, this)
        recyclerview?.adapter = bigListAdapter
        DialogUtil.progressDlgHide()
    }
    
}