package com.learning.kids.baseapp.core.adapter

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.activities.DetailBigListItemActivity
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.models.DataAlphabetFollow
import com.learning.kids.baseapp.data.models.DataFollow
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener


class DetailBigListAlphabetAdapter(var context: Context, var arrayList: ArrayList<DataAlphabetFollow>):
    RecyclerView.Adapter<DetailBigListAlphabetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_alphabet, parent, false)
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dataAlphabetFollow: DataAlphabetFollow = arrayList[position]
        getImageId(dataAlphabetFollow.image, context)?.let { holder.image.setImageResource(it) }
        holder.itemView.setOnClickListener {
            var audioManager = AudioManager(context, dataAlphabetFollow.sound)
            audioManager.startSound()
            setAnimation(holder.itemView)
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

    override fun getItemCount(): Int {
        return arrayList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.img_alphabet)
    }
    private fun getImageId(name: String?, context: Context): Int? {
        return if (name == null) {
            null
        } else {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }
}