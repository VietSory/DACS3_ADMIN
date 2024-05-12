package com.example.dacs3_admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.DeliveryAdapter
import com.example.dacs3_admin.adapter.PendingAdapter
import com.example.dacs3_admin.databinding.ActivityAdminProfileBinding
import com.example.dacs3_admin.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            finish()
        }
        val orderCustomerName = arrayListOf(
            "kk","hh","ll",
        )
        val orderQuantity = arrayListOf(
            "1","1","1",
        )
        val orderImage= arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3)
        val adapter = PendingAdapter(orderCustomerName,orderQuantity,orderImage,this)
        binding.pendingRecycleView.adapter=adapter
        binding.pendingRecycleView.layoutManager= LinearLayoutManager(this)
    }
 }