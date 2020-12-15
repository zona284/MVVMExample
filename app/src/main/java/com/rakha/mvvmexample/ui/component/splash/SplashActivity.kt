package com.rakha.mvvmexample.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.ui.component.login.LoginActivity

/**
 *   Created By rakha
 *   16/09/20
 */
class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            launch()
        }, 3000)

    }

    private fun launch() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}