package com.learning.kids.baseapp.core.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.activities.SwipeReadActivity
import com.learning.kids.baseapp.data.models.DataAlphabetFollow
import kotlinx.android.synthetic.main.activity_swipe_read.*
import kotlin.collections.ArrayList

class MyRecyclerViewAdapter(var context: Context, var arrayList: ArrayList<DataAlphabetFollow>, var mContext: SwipeReadActivity):
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_swipe_read_tow, parent, false)
        return ViewHolder(itemHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dataAlphabetFollow: DataAlphabetFollow = arrayList[position]
        getImageId(dataAlphabetFollow.image, context)?.let { holder.image.setImageResource(it)
        holder.itemView.setOnClickListener {
             mContext.vpswipe.currentItem = position
         }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.alpha1)
    }
    private fun getImageId(name: String?, context: Context): Int? {
        return if (name == null) {
            null
        } else {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        }
    }

}