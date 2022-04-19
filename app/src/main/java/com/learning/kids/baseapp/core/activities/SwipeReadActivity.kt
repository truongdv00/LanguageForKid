package com.learning.kids.baseapp.core.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.adapter.MyRecyclerViewAdapter
import com.learning.kids.baseapp.core.adapter.SwipeReadAdapter
import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.database.MyDatabase
import com.learning.kids.baseapp.data.models.DataAlphabetFollow
import com.learning.kids.baseapp.data.models.DataSwipe
import com.learning.kids.baseapp.data.models.DataSwipeAlpha
import kotlinx.android.synthetic.main.activity_detail_big_list_follow.*
import kotlinx.android.synthetic.main.activity_swipe_read.*
import kotlinx.android.synthetic.main.activity_swipe_read.btn_back
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.learning.kids.baseapp.core.custom.AudioManager


class SwipeReadActivity : AppCompatActivity() {
    private var recyclerview: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var arrayList: ArrayList<DataAlphabetFollow>? = null
    private var myRecyclerViewAdapter: MyRecyclerViewAdapter? = null
    private var db: MyDatabase? = null
    private var currentPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_read)
        db = MyDatabase(this)
        initRecyclerview()
        onClick()
    }

    private fun initView() {
        val key = intent.getStringExtra(resources.getString(R.string.key_detail))
        when (key) {
            Constants.ALPHABETS -> {
                arrayList = db?.getAllItemsAlphabet()
            }
            Constants.NUMBER -> {
                arrayList = db?.getAllItemsNumber()
            }
            Constants.ANIMALS -> {
                arrayList = db?.getAllItemsAnimal()
            }
            Constants.FRUITS -> {
                arrayList = db?.getAllItemsFruit()
            }
            Constants.SQUARE -> {
                arrayList = db?.getAllItemsGeometry()
            }
            Constants.COLOR -> {
                arrayList = db?.getAllItemsColor()
            }
            Constants.SCHOOL -> {
                arrayList = db?.getAllItemsSchool()
            }
            Constants.FLOWERS -> {
                arrayList = db?.getAllItemsFlower()
            }
            Constants.VEHICLE -> {
                arrayList = db?.getAllItemsVehicle()
            }
            Constants.COUNTRY -> {
                arrayList = db?.getAllItemsCountry()
            }
        }
    }

    private fun initRecyclerview() {
        recyclerview = findViewById(R.id.rvswipe)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerview?.layoutManager = linearLayoutManager
        recyclerview?.setHasFixedSize(true)
        initView()
        myRecyclerViewAdapter = MyRecyclerViewAdapter(applicationContext, arrayList!!, this)
        recyclerview?.adapter = myRecyclerViewAdapter
        // init viewpager
        val adapter = SwipeReadAdapter(applicationContext, arrayList!!)
        vpswipe.adapter = adapter
        swipeViewpager()
        playSound(0)
    }

    private fun swipeViewpager() {
        vpswipe.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
            }

            override fun onPageSelected(position: Int) {
                playSound(position)
                if (position <= currentPosition) {
                    if (position > 0) {
                        recyclerview!!.post(Runnable { // Call smooth scroll
                            recyclerview!!.smoothScrollToPosition(position - 1)
                        })
                    }
                    if (position == 0) {
                            recyclerview!!.post(Runnable { // Call smooth scroll
                                recyclerview!!.smoothScrollToPosition(position)
                            })
                    }
                } else {
                    if (currentPosition < arrayList!!.size - 1) {
                        recyclerview!!.post(Runnable { // Call smooth scroll
                            recyclerview!!.smoothScrollToPosition(position + 1)
                        })
                    }
                }
            }
        })
    }

    private fun getItem(i: Int): Int {
        return vpswipe.currentItem + i
    }


    private fun onClick() {
        btn_back.setOnClickListener {
            finish()
        }
        btn_next.setOnClickListener {
            vpswipe.setCurrentItem(getItem(+1), true)
        }
        btn_prev.setOnClickListener {
            vpswipe.setCurrentItem(getItem(-1), true)
        }
        btnSpeaker.setOnClickListener {
            playSoundAgain(currentPosition)
        }
    }
    private fun playSound(pos: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            var audioManager = AudioManager(applicationContext, arrayList!![pos].sound)
            audioManager.startSound()
        }, 1000)
    }
    private fun playSoundAgain(pos: Int) {
            var audioManager = AudioManager(applicationContext, arrayList!![pos].sound)
            audioManager.startSound()
    }
}