package com.example.dacs3_admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

class PendingOrderActivity : AppCompatActivity(),PendingAdapter.OnItemClicked {
    private lateinit var binding: ActivityPendingOrderBinding
    private var listOfName:ArrayList<String> = arrayListOf()
    private var listOfTotalPrice:ArrayList<String> =arrayListOf()
    private var listOfImage:ArrayList<String> = arrayListOf()
    private var listOfOrderItem:ArrayList<OrderDetails> = arrayListOf()
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
        val adapter=PendingAdapter(this,listOfName,listOfTotalPrice,listOfImage,this)
        binding.pendingRecycleView.adapter=adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent= Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails=listOfOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        val childItemPushKey=listOfOrderItem[position].itemPushKey
        val clickItemOrderReference=childItemPushKey?.let{
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }

    private fun updateOrderAcceptStatus(position: Int) {
        val userId=listOfOrderItem[position].userUid
        val pushKey=listOfOrderItem[position].itemPushKey
        val buyHistoryReference=database.reference.child("user").child(userId!!).child("BuyHistory").child(pushKey!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
        databaseOrderDetails.child(pushKey).child("orderAccepted").setValue(true)
    }

    override fun onItemDispatchClickListenr(position: Int) {
        val dispatchItemPushKey=listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference=database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteItemOrderDetails(dispatchItemPushKey)
            }
    }

    private fun deleteItemOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsReference=database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this,"Đơn hàng đã được gửi đi",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Đơn hàng chưa được gửi di",Toast.LENGTH_SHORT).show()
            }
    }


}