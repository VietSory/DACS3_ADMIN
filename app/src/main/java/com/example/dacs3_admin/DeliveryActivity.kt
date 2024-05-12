package com.example.dacs3_admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.DeliveryAdapter
import com.example.dacs3_admin.databinding.ActivityDeliveryBinding

class DeliveryActivity : AppCompatActivity() {
    private val binding : ActivityDeliveryBinding by lazy {
        ActivityDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener{
            finish()
        }
        val customerName = arrayListOf(
            "kk","hh","ll",
        )
        val moneyStatus = arrayListOf(
            "received","notReceived","Pending",
        )
        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecycleView.adapter=adapter
        binding.deliveryRecycleView.layoutManager=LinearLayoutManager(this)


    }
}