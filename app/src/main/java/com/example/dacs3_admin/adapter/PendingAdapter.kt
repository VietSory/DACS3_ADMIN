package com.example.dacs3_admin.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3_admin.databinding.DrinkItemBinding
import com.example.dacs3_admin.databinding.PendingOrderItemBinding

class PendingAdapter(private val customerNames:ArrayList<String>, private val quantities:ArrayList<String>,private val images:ArrayList<Int>,private val context:Context) : RecyclerView.Adapter<PendingAdapter.PendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding=PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int =customerNames.size

    inner class PendingViewHolder(private val binding: PendingOrderItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply{
                nameCustomer.text=customerNames[position]
                ItemQuantity.text=quantities[position]
                pendingImage.setImageResource(images[position])
                btnAccept.apply {
                    setOnClickListener {
                        if (!isAccepted){
                            text="Từ Chối"
                            isAccepted=true
                            showtoast("Đơn Hàng Đã được chấp nhận")
                        }
                        else {
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showtoast("Đơn hàng đã bị từ chối")
                        }
                    }
                }
            }
        }
        private fun showtoast(message:String){
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
}