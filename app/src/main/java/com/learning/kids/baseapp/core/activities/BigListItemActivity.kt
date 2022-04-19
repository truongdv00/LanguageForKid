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





class BigListItemActivity: AppCompatActivity() {
    private var recyclerview: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList: ArrayList<DataFollow>? = null
    private var bigListAdapter: BigListFollowAdapter? = null
    private var db:MyDatabase? = null
    var tvtitle: TextView? = null
    var context: Context? = null
    var key: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_list_follow)
        recyclerview = findViewById(R.id.listFollow)
        tvtitle = findViewById(R.id.txt_title)
        this.context = context
        db = MyDatabase(this)
        arrayList = db?.getAllItemsBigList()
        initView()
        initRecyclerview()
        onClick()
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

    private fun initRecyclerview() {
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerview?.layoutManager = gridLayoutManager
        recyclerview?.setHasFixedSize(true)
        bigListAdapter = BigListFollowAdapter(applicationContext, arrayList!!, this)
        recyclerview?.adapter = bigListAdapter
    }
    
}