package com.learning.kids.baseapp.presentation.splash

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.learning.kids.baseapp.MainActivity
import com.learning.kids.baseapp.R
import com.learning.kids.baseapp.api.SessionManager
import com.learning.kids.baseapp.core.activities.BigListItemActivity
import com.learning.kids.baseapp.core.activities.HomeScreenActivity
import com.learning.kids.baseapp.core.activities.LoginActivity

import com.learning.kids.baseapp.core.activities.SwipeReadActivity
import com.learning.kids.baseapp.core.custom.AudioManager
import kotlinx.android.synthetic.main.ac_splash.*


class SplashActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_splash)
        sessionManager = SessionManager(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.fetchAuthToken() != ""&& sessionManager.fetchAuthToken() != null) {
                startActivity(Intent(this, HomeScreenActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
}

    override fun onBackPressed() {
        // No impl
    }
}
