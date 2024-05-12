package com.example.dacs3_admin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs3_admin.databinding.DrinkItemBinding
import com.example.dacs3_admin.model.AllMenu
import com.google.firebase.database.DatabaseReference

class menuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<menuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray (menuList.size) {1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=DrinkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int =menuList.size

    inner class AddItemViewHolder(private val binding: DrinkItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply{
                val quantity= itemQuantities[position]
                val menuItem=menuList[position]
                val uriString =menuItem.drinkImage
                val uri= Uri.parse(uriString)
                drinkName.text=menuItem.drinkName
                drinkPrice.text=menuItem.drinkPrice
                Glide.with(context).load(uri).into(menuImage)
                menuItemQuantity.text=quantity.toString()

                btnminus.setOnClickListener {
                    deceaseQuantity(position)
                }

                btnplus.setOnClickListener {
                    increaseQuantity(position)
                }
                btndelete.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.menuItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deceaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.menuItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem (position : Int){
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }
    }
}