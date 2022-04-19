package com.learning.kids.baseapp.core.custom

import android.content.Context
import android.media.MediaPlayer
import java.util.*
import kotlin.collections.ArrayList






interface AudioManagerInput {
    fun startSound()
    fun stopSound()
    fun onDestroy()
}

class AudioManager(private val context: Context, var name: String) : AudioManagerInput {
    private var mediaPlayer: MediaPlayer? = null
    var timer:Timer? = null
    override fun startSound() {
        mediaPlayer = MediaPlayer.create(
            context,
            getSoundsId(name, context)!!
        )
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
        }
    }

    override fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }
    override fun onDestroy() {
        if (mediaPlayer!!.isPlaying)
            mediaPlayer!!.stop();
        timer!!.cancel()
    }

    private fun getSoundsId(name: String?, context: Context): Int? {
        return context?.resources?.getIdentifier(name, "raw", context.packageName)
    }
    fun playNext(playlist: ArrayList<Int>) {
        var i =0
        timer.schedule(object : TimerTask() {
            override fun run() {
                mediaPlayer?.reset()
                mediaPlayer = MediaPlayer.create(context, playlist[++i])
                mediaPlayer?.start()
                if (playlist.size > i + 1) {
                    playNext(playlist)
                }
            }
        }, mediaPlayer?.duration?.plus(100))
    }
}

private fun Timer?.schedule(timerTask: TimerTask, plus: Int?) {

}



