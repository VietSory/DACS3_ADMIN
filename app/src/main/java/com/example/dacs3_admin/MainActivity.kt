package com.example.dacs3_admin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dacs3_admin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.addMenu.setOnClickListener{
            val intent =  Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allMenuItem.setOnClickListener{
            val intent =  Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.Delivery.setOnClickListener{
            val intent =  Intent(this,DeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener{
            val intent =  Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
        binding.createUser.setOnClickListener{
            val intent =  Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrder.setOnClickListener{
            val intent =  Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}