package com.learning.kids.baseapp.core.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.activities.SwipeReadActivity
import com.learning.kids.baseapp.core.activities.TestActivity
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.models.DataQuiz
import java.time.LocalDate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_test.*


class TestPagerAdapter(var list: ArrayList<DataQuiz>, var context: Context, var mContext: TestActivity): PagerAdapter() {
    var choose: String? = null
    var ans: String? = null
    var checkCorrect: Boolean? = null
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container?.context)
                .inflate(R.layout.item_quiz,container,false)

        val imgOption1: ImageView = view.findViewById(R.id.option1)
        val imgOption2: ImageView = view.findViewById(R.id.option2)
        val imgOption3: ImageView = view.findViewById(R.id.option3)
        val imgOption4: ImageView = view.findViewById(R.id.option4)

        getImageId(list[position].option1, context)?.let { imgOption1.setImageResource(it) }
        getImageId(list[position].option2, context)?.let { imgOption2.setImageResource(it) }
        getImageId(list[position].option3, context)?.let { imgOption3.setImageResource(it) }
        getImageId(list[position].option4, context)?.let { imgOption4.setImageResource(it) }
        container?.addView(view)
        imgOption1.setOnClickListener {
            choose = list[position].option1.split("img_".toRegex())[1]
            onClick(position)
        }
        imgOption2.setOnClickListener {
            choose = list[position].option2.split("img_".toRegex())[1]
            onClick(position)
        }
        imgOption3.setOnClickListener {
            choose = list[position].option3.split("img_".toRegex())[1]
            onClick(position)
        }
        imgOption4.setOnClickListener {
            choose = list[position].option4.split("img_".toRegex())[1]
            onClick(position)
        }
        return view
    }
    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        // Remove the view from view group specified position
        parent.removeView(`object` as View)
    }
    fun playSoundCorrect() {
        var audioManager = AudioManager(context, "sounds_effect_chinhxac")
        audioManager.startSound()
        Handler(Looper.getMainLooper()).postDelayed({
            mContext.doNext()
        }, 2000)

    }
    fun playSoundInCorrect() {
        var audioManager = AudioManager(context, "sounds_effect_moibanthulai")
        audioManager.startSound()
    }
    private fun getImageId(name: String?, context: Context): Int? {
        return if (name == null) {
            null
        } else {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }
    fun onClick(position: Int) {
        ans = list[position].ans.split("sounds_".toRegex())[1]
        if (choose == ans) {
            playSoundCorrect()
            mContext.icon_answer.setImageResource(R.drawable.correct)
            setAnimation(mContext.icon_answer)
        } else {
            playSoundInCorrect()
            mContext.icon_answer.setImageResource(R.drawable.incorrect)
            setAnimation(mContext.icon_answer)
        }
    }
    private fun setAnimation(itemView: View) {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.duration = 600
        anim.addUpdateListener { animation ->
            itemView.scaleX = animation.animatedValue as Float
            itemView.scaleY = animation.animatedValue as Float
        }
        anim.repeatCount = 1
        anim.repeatMode = ValueAnimator.REVERSE
        anim.start()
    }

}