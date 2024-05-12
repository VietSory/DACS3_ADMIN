package com.example.dacs3_admin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import com.example.dacs3_admin.R

@Suppress("DEPRECATION")
class welcome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)
        val handler = Handler()
        handler.postDelayed({
            val intent= Intent(this,loginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}

