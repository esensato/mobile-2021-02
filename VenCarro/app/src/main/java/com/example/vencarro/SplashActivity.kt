package com.example.vencarro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        Log.i("Versao", "Versao: ${Build.VERSION.SDK_INT}")

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars()) }
        else { window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN ) }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            fechar()
        }, 5000)
    }

    fun fechar() {

        val para = Intent(this, MainActivity::class.java)
        startActivity(para)
        finish()

    }
}