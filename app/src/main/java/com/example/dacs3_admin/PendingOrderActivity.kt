package com.example.dacs3_admin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.PendingAdapter
import com.example.dacs3_admin.databinding.ActivityPendingOrderBinding
import com.example.dacs3_admin.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName:MutableList<String> = mutableListOf()
    private var listOfTotalPrice:MutableList<String> = mutableListOf()
    private var listOfImage:MutableList<String> = mutableListOf()
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()
    private lateinit var database:FirebaseDatabase
    private lateinit var databaseOrderDetails:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            finish()
        }
        database=FirebaseDatabase.getInstance()
        databaseOrderDetails=database.reference.child("OrderDetails")
        getOrdersDetails()
    }

    private fun getOrdersDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    val orderDetails=orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataRecycle()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun addDataRecycle() {
        for (orderItem in listOfOrderItem){
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.drinkImages?.filterNot{ it.isEmpty() }?.forEach {
                listOfImage.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingRecycleView.layoutManager=LinearLayoutManager(this)
        val adapter=PendingAdapter(this,listOfName,listOfTotalPrice,listOfImage)
        binding.pendingRecycleView.adapter=adapter
    }

}