package com.example.dacs3_admin.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs3_admin.databinding.PendingOrderItemBinding

class PendingAdapter(private val context: Context,
                     private val customerNames:MutableList<String>,
                     private val quantities: MutableList<String>,
                     private val images: MutableList<String>
) : RecyclerView.Adapter<PendingAdapter.PendingViewHolder>() {

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
                val uri= Uri.parse(images[position])
                Glide.with(context).load(uri).into(pendingImage)
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