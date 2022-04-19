package com.learning.kids.baseapp.core.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.Touch
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.custom.AudioManager
import com.learning.kids.baseapp.data.Constants
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        onClick()
    }

    private fun onClick() {
        btnFollow.setOnClickListener {
            playSound("sounds_start_doctheo")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnFollow.text)
                intent.putExtra(Constants.KEY_HOME_FOLLOW, Constants.FOLLOW)
                startActivity(intent)
        }
        btnTouch.setOnClickListener {
            playSound("sounds_start_chamvadoc")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnTouch.text)
                startActivity(intent)
        }
        btnTest.setOnClickListener {
            playSound("sounds_start_test")
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@HomeScreenActivity, TestActivity::class.java)
                startActivity(intent)
            }, 2000)

        }
        btnSwipe.setOnClickListener {
            playSound("sounds_start_vuotvadoc")
                val intent = Intent(this@HomeScreenActivity, BigListItemActivity::class.java)
                intent.putExtra(Constants.KEY_HOME, btnSwipe.text)
                startActivity(intent)

        }
    }
    private fun playSound(name: String) {
            var audioManager = AudioManager(applicationContext, name)
            audioManager.startSound()
    }
}