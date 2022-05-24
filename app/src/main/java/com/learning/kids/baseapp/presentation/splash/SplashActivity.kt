package com.learning.kids.baseapp.presentation.splash

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.learning.kids.baseapp.MainActivity
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.core.activities.BigListItemActivity
import com.learning.kids.baseapp.core.activities.HomeScreenActivity
import com.learning.kids.baseapp.core.activities.LoginActivity

import com.learning.kids.baseapp.core.activities.SwipeReadActivity
import com.learning.kids.baseapp.core.custom.AudioManager
import kotlinx.android.synthetic.main.ac_splash.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        }, 2000)
}

    override fun onBackPressed() {
        // No impl
    }
}
