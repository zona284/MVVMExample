package com.rakha.mvvmexample.feature.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.service.autofill.UserData
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.feature.login.LoginActivity

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