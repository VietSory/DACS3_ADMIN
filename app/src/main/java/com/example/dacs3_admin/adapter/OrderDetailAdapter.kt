package com.example.dacs3_admin.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dacs3_admin.databinding.OrderDetailItemBinding

class OrderDetailAdapter(
    private var context: Context,
    private var drinkNames: ArrayList<String>,
    private var drinkImages: ArrayList<String>,
    private var drinkQuantity:ArrayList<Int>,
    private var drinkPrices: ArrayList<String>,
    ):RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailAdapter.OrderDetailViewHolder {
        val binding=OrderDetailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailAdapter.OrderDetailViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =drinkNames.size
    inner class OrderDetailViewHolder(private val binding:OrderDetailItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                drinkName.text=drinkNames[position]
                ItemQuantity.text=drinkQuantity[position].toString()
                val uriString=drinkImages[position]
                val uri= Uri.parse(uriString)
                Glide.with(context).load(uri).into(drinkImage)
                drinkPrice.text=drinkPrices[position]

            }
        }

    }
}