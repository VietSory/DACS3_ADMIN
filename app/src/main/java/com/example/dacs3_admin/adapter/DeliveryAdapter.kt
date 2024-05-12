package com.example.dacs3_admin.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs3_admin.databinding.DeliveryItemBinding
import com.example.dacs3_admin.databinding.DrinkItemBinding

class DeliveryAdapter(private val customerNames:ArrayList<String>, private val statusMoneys:ArrayList<String>) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding=DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int =customerNames.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply{
                customerName.text=customerNames[position]
                statusMoney.text=statusMoneys[position]
                val colorMap: Map<String, Int> = mapOf("received" to Color.Green.toArgb(),"notReceived" to Color.Red.toArgb(),"Pending" to Color.Gray.toArgb())
                statusMoney.setTextColor(colorMap[statusMoneys[position]]?:Color.Black.toArgb())
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[statusMoneys[position]]?:Color.Black.toArgb())
            }
        }
    }
}