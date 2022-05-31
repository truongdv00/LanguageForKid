package com.learning.kids.baseapp.core.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.adapter.TestPagerAdapter
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.database.MyDatabase
import com.learning.kids.baseapp.data.models.DataAlphabetFollow
import com.learning.kids.baseapp.data.models.DataQuiz
import jp.co.dev.android.util.DialogUtil
import kotlinx.android.synthetic.main.activity_detail_big_list_follow.btn_back
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity() {
    private var db: MyDatabase? = null
    var listQuiz: ArrayList<DataAlphabetFollow>? = null
    var quizs: ArrayList<DataQuiz>? = null
    var adapter: TestPagerAdapter? = null
    var data: String? = null
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        DialogUtil.progressDlgHide()
        db = MyDatabase(this)
        onClick()
        setListQuiz()
        getQuiz(position)
    }

    private fun getQuiz(pos: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            var audioManager = AudioManager(applicationContext, quizs!![pos].ans)
            audioManager.startSound()
        }, 1000)
    }
    private fun playAgain(pos: Int) {
            var audioManager = AudioManager(applicationContext, quizs!![pos].ans)
            audioManager.startSound()
    }

    private fun setListQuiz() {
        listQuiz = db?.getTest()
        quizs = ArrayList()
        for (i in 0..9) {
            quizs!!.add(
                DataQuiz(
                    listQuiz!![i * 4].image!!,
                    listQuiz!![i * 4 + 1].image!!,
                    listQuiz!![i * 4 + 2].image!!,
                    listQuiz!![i * 4 + 3].image!!,
                    getRandomSound(i)!!
                )
            )
        }
        adapter = TestPagerAdapter(quizs!!, applicationContext, this)
        vpquiz.adapter = adapter
        vpquiz.setOnTouchListener(OnTouchListener { v, event -> true })
    }

    fun getRandomSound(i: Int): String? {
        val rand = (i * 4..i * 4 + 3).random()
        return listQuiz!![rand].sound
    }

    private fun onClick() {
        btn_back.setOnClickListener {
            finish()
        }
        btn_next.setOnClickListener {
            doNext()


        }
        btn_prev.setOnClickListener {
            doPrev()
        }
        vpquiz.setOnClickListener {
            Log.d("TruongDV19", "clicked")
        }
        btn_speaker.setOnClickListener {
            playAgain(position)
        }
    }

     fun doPrev() {
        if (position>0) {
            vpquiz.setCurrentItem(getItem(-1), true)
            position--
            getQuiz(position)
        } else {
            position = 0
        }
    }

     fun doNext() {
        if (position < quizs!!.size - 1) {
            vpquiz.setCurrentItem(getItem(+1), true)
            position++
            getQuiz(position)
        } else
            position = quizs!!.size - 1
    }

    private fun getItem(i: Int): Int {
             return vpquiz.currentItem + i
    }
}