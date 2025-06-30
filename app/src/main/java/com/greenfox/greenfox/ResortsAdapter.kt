package com.greenfox.greenfox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResortsAdapter(
    private val resorts: List<Resort>,
    private val onResortClick: (Resort) -> Unit
) : RecyclerView.Adapter<ResortsAdapter.ResortViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResortViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resort_card, parent, false)
        return ResortViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResortViewHolder, position: Int) {
        holder.bind(resorts[position])
    }

    override fun getItemCount(): Int = resorts.size

    inner class ResortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMainImage: ImageView = itemView.findViewById(R.id.iv_main_image)
        private val tvResortName: TextView = itemView.findViewById(R.id.tv_resort_name)
        private val tvResortType: TextView = itemView.findViewById(R.id.tv_resort_type)
        private val llOptions: LinearLayout = itemView.findViewById(R.id.ll_options)

        fun bind(resort: Resort) {
            ivMainImage.setImageResource(resort.mainImage)
            tvResortName.text = resort.name
            tvResortType.text = resort.type

            // Clear previous options
            llOptions.removeAllViews()

            // Add resort options
            resort.options.take(2).forEach { option ->
                val optionView = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_resort_option, llOptions, false)

                val ivOption: ImageView = optionView.findViewById(R.id.iv_option)
                val tvOptionName: TextView = optionView.findViewById(R.id.tv_option_name)

                ivOption.setImageResource(option.image)
                tvOptionName.text = option.name

                llOptions.addView(optionView)
            }

            itemView.setOnClickListener {
                onResortClick(resort)
            }
        }
    }
}