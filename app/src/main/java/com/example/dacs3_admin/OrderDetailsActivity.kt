package com.example.dacs3_admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.OrderDetailAdapter
import com.example.dacs3_admin.databinding.ActivityOrderDetailsBinding
import com.example.dacs3_admin.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName:String?=null
    private var address:String?=null
    private var phoneNumber:String?=null
    private var totalPrice:String?=null
    private var drinkNames:ArrayList<String> = arrayListOf()
    private var drinkImages:ArrayList<String> = arrayListOf()
    private var drinkQuantity:ArrayList<Int> = arrayListOf()
    private var drinkPrices:ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button3.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val receiveOrderDetails=intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receiveOrderDetails?.let { orderDetails ->
                userName=receiveOrderDetails.userName
                address=receiveOrderDetails.address
                phoneNumber=receiveOrderDetails.phone
                drinkNames=receiveOrderDetails.drinkNames as ArrayList<String>
                drinkPrices=receiveOrderDetails.drinkPrices as ArrayList<String>
                drinkQuantity=receiveOrderDetails.drinkQuantities as ArrayList<Int>
                drinkImages=receiveOrderDetails.drinkImages as ArrayList<String>
                totalPrice=receiveOrderDetails.totalPrice
                setUserDetail()
                setAdapter()
        }
    }

    private fun setAdapter() {
        binding.orderDetailRecycle.layoutManager=LinearLayoutManager(this)
        val adapder=OrderDetailAdapter(this,drinkNames,drinkImages,drinkQuantity,drinkPrices)
        binding.orderDetailRecycle.adapter=adapder
    }

    private fun setUserDetail() {
        binding.tvtName.text=userName
        binding.tvtPhone.text=phoneNumber
        binding.tvtAddress.text=address
        binding.tvtPrice.text=totalPrice
    }
}