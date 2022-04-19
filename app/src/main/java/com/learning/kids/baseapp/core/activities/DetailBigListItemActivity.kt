package com.learning.kids.baseapp.core.activities

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.adapter.DetailBigListAlphabetAdapter
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.database.MyDatabase
import com.learning.kids.baseapp.data.models.DataAlphabetFollow
import com.learning.kids.baseapp.data.models.DataSound
import kotlinx.android.synthetic.main.activity_detail_big_list_follow.*
import kotlinx.coroutines.handleCoroutineException
import okhttp3.internal.wait
import java.util.*
import kotlin.collections.ArrayList
import com.learning.kids.baseapp.MainActivity
import java.lang.Exception


class DetailBigListItemActivity : AppCompatActivity() {
    private var recyclerview: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList: ArrayList<DataAlphabetFollow>? = null
    private var playlist: ArrayList<Int>? = null
    private var detailBigListAlphabetAdapter: DetailBigListAlphabetAdapter? = null
    private var db: MyDatabase? = null
    private var checkDestroy: Boolean = false
    var mediaPlayer: MediaPlayer? = null
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_big_list_follow)
        recyclerview = findViewById(R.id.listDetailAlphabet)
        db = MyDatabase(this)
        initView()
        initRecyclerview()
        onClick()
    }
    private fun initView() {
        val title = intent.getStringExtra(Constants.KEY_DETAIL)
        val key = intent.getStringExtra(Constants.KEY_FOLLOW)
        when (title) {
            Constants.ALPHABETS -> {
                arrayList = db?.getAllItemsAlphabet()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsAlphabet(this)
                    playListSound()
                }
            }
            Constants.NUMBER -> {
                arrayList = db?.getAllItemsNumber()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsNumber(this)
                    playListSound()
                }
            }
            Constants.ANIMALS -> {
                arrayList = db?.getAllItemsAnimal()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsAnimal(this)
                    playListSound()
                }
            }
            Constants.FRUITS -> {
                arrayList = db?.getAllItemsFruit()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsFruit(this)
                    playListSound()
                }
            }
            Constants.SQUARE -> {
                arrayList = db?.getAllItemsGeometry()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsGeometry(this)
                    playListSound()
                }
            }
            Constants.SCHOOL -> {
                arrayList = db?.getAllItemsSchool()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemSchool(this)
                    playListSound()
                }
            }
            Constants.COLOR -> {
                arrayList = db?.getAllItemsColor()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsColor(this)
                    playListSound()
                }
            }
            Constants.FLOWERS -> {
                arrayList = db?.getAllItemsFlower()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsFlower(this)
                    playListSound()
                }
            }
            Constants.VEHICLE -> {
                arrayList = db?.getAllItemsVehicle()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsVehicle(this)
                    playListSound()
                }
            }
            Constants.COUNTRY -> {
                arrayList = db?.getAllItemsCountry()
                txtTitle.text = title
                if (key == Constants.FOLLOW) {
                    playlist = db?.getSoundtemsCountry(this)
                    playListSound()
                }
            }
        }
    }
    private fun playListSound() {
        mediaPlayer = MediaPlayer.create(this, playlist!![0])
        mediaPlayer?.start()
        if (playlist!!.size > 1) playNext()
    }

    private fun playNext() {
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer!!.release()
                if (!checkDestroy) {
                    mediaPlayer = MediaPlayer.create(this, playlist!![++i])
                    mediaPlayer?.start()
                    recyclerview!!.smoothScrollToPosition(i+12)
                }
                if (playlist!!.size > i +1 && !checkDestroy) playNext()
            }, 2000)
    }

    override fun onDestroy() {
        checkDestroy = true
        super.onDestroy()
    }

    private fun onClick() {
        btn_back.setOnClickListener {
            finish()
        }
    }
    private fun setAnimation(itemView: View) {
        val anim = ValueAnimator.ofFloat(1f, 1.5f)
        anim.duration = 300
        anim.addUpdateListener { animation ->
            itemView.scaleX = animation.animatedValue as Float
            itemView.scaleY = animation.animatedValue as Float
        }
        anim.repeatCount = 1
        anim.repeatMode = ValueAnimator.REVERSE
        anim.start()
    }

    private fun initRecyclerview() {
        gridLayoutManager =
            GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recyclerview?.layoutManager = gridLayoutManager
        recyclerview?.setHasFixedSize(true)
        detailBigListAlphabetAdapter = DetailBigListAlphabetAdapter(applicationContext, arrayList!!)
        recyclerview?.adapter = detailBigListAlphabetAdapter
    }
}

