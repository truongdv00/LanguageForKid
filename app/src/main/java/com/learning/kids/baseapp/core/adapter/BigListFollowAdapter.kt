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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.MainActivity
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.activities.BigListItemActivity
import com.learning.kids.baseapp.core.activities.DetailBigListItemActivity
import com.learning.kids.baseapp.core.activities.SwipeReadActivity
import com.learning.kids.baseapp.core.activities.TestActivity
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.Constants
import com.learning.kids.baseapp.data.models.DataFollow
import android.view.animation.AnimationSet




class BigListFollowAdapter(var context: Context, var arrayList: ArrayList<DataFollow>, var mContext: BigListItemActivity) :
    RecyclerView.Adapter<BigListFollowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_big_list_follow, parent, false)
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dataFollow: DataFollow = arrayList[position]
        getImageId(dataFollow.img_content, context)?.let { holder.content.setImageResource(it) }
        setAnimation(holder.itemView)
        holder.title.text = dataFollow.title
        holder.itemView.setOnClickListener {
            var audioManager = AudioManager(context, dataFollow.sound)
            audioManager.startSound()
            setAnimation(holder.itemView)
            if (mContext.tvtitle?.text == Constants.SWIPE) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(context, SwipeReadActivity::class.java)
                    intent.putExtra(Constants.KEY_DETAIL, dataFollow.title)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }, 1500)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(context, DetailBigListItemActivity::class.java)
                    intent.putExtra(Constants.KEY_DETAIL, dataFollow.title)
                    intent.putExtra(Constants.KEY_FOLLOW, mContext.key)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }, 1500)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content = itemView.findViewById<ImageView>(R.id.imgContent)
        var title = itemView.findViewById<TextView>(R.id.txtTitle)
    }

    private fun getImageId(name: String?, context: Context): Int? {
        return if (name == null) {
            null
        } else {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }
    fun setAnimation(item: View) {
        AnimationUtils.loadAnimation(context, R.anim.anim_flip).also { hyperspaceJumpAnimation ->
            item.startAnimation(hyperspaceJumpAnimation)
        }
    }

}