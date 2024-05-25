package com.example.dacs3_admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.DeliveryAdapter
import com.example.dacs3_admin.databinding.ActivityDeliveryBinding
import com.example.dacs3_admin.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DeliveryActivity : AppCompatActivity() {
    private val binding : ActivityDeliveryBinding by lazy {
        ActivityDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private var listCompleteOrder:ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener{
            finish()
        }
        retrieveCompleteOrder()
    }

    private fun retrieveCompleteOrder() {
        database=FirebaseDatabase.getInstance()
        val completeOrderReference=database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listCompleteOrder.clear()
                for(orderSnapshot in snapshot.children){
                    val completeOrder=orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listCompleteOrder.add(it)
                    }
                }
                listCompleteOrder.reverse()
                setDataIntoRecycleView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecycleView() {
        val customerName= mutableListOf<String>()
        val moneyStatus= mutableListOf<Boolean>()
        for(order in listCompleteOrder){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceive)
        }
        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.deliveryRecycleView.adapter=adapter
        binding.deliveryRecycleView.layoutManager=LinearLayoutManager(this)
    }
}