package com.example.dacs3_admin

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.dacs3_admin.databinding.ActivityAddItemBinding
import com.example.dacs3_admin.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var drinkName:String
    private lateinit var drinkPrice:String
    private lateinit var drinkDescription:String
    private var drinkImageUri : Uri?=null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy{
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        binding.btnAddItem.setOnClickListener{
            drinkName=binding.edtName.text.toString().trim()
            drinkPrice=binding.edtPrice.text.toString().trim()
            drinkDescription=binding.edtDescription.text.toString().trim()

            if (!(drinkName.isBlank()||drinkPrice.isBlank()||drinkDescription.isBlank())){
                uploadData()
                Toast.makeText(this,"Thêm Đồ Uống Thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else{
                Toast.makeText(this,"Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgSelect.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        val menuRef : DatabaseReference=database.getReference("menu")
        //Generate a unique key for the menu item
        val newItemKey =menuRef.push().key
        if (drinkImageUri!=null){
            val storageRef= FirebaseStorage.getInstance().reference
            val imageRef =storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(drinkImageUri!!)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val newItem = AllMenu(
                        drinkName = drinkName,
                        drinkPrice = drinkPrice,
                        drinkDescription = drinkDescription,
                        drinkImage = downloadUrl.toString(),
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this, "Thêm Đồ Uống Thành công", Toast.LENGTH_SHORT)
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "Thêm Đồ Uống Thất Bại", Toast.LENGTH_SHORT)
                                    .show()

                            }
                    }
                }
            }    .addOnFailureListener{
                        Toast.makeText(this,"Thêm Hình Ảnh Thất Bại", Toast.LENGTH_SHORT).show()
                    }
            } else {
                    Toast.makeText(this,"Hãy Thêm hình ảnh", Toast.LENGTH_SHORT).show()
            }
    }

    private val pickImage=registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri != null){
            binding.imgSelect.setImageURI(uri)
            drinkImageUri = uri
        }
    }
}