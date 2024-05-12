package com.example.dacs3_admin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dacs3_admin.adapter.menuItemAdapter
import com.example.dacs3_admin.databinding.ActivityAllItemBinding
import com.example.dacs3_admin.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database : FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy{
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val drinkRef: DatabaseReference=database.reference.child("menu")
        //fetch data
        drinkRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                //clear data
                menuItems.clear()
                //loop each item
                for (drinkSnapshot in snapshot.children){
                    val menuItem=drinkSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = menuItemAdapter(this@AllItemActivity,menuItems,databaseReference)
        binding.menuRecycleView.layoutManager = LinearLayoutManager(this)
        binding.menuRecycleView.adapter=adapter
    }
}