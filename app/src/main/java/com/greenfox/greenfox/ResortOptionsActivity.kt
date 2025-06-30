package com.greenfox.greenfox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResortOptionsDetailAdapter(
    private val options: List<ResortOption>,
    private val onOptionSelect: (ResortOption) -> Unit
) : RecyclerView.Adapter<ResortOptionsDetailAdapter.OptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resort_option_detail, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivOption: ImageView = itemView.findViewById(R.id.iv_option)
        private val tvOptionName: TextView = itemView.findViewById(R.id.tv_option_name)
        private val tvOptionDescription: TextView = itemView.findViewById(R.id.tv_option_description)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val btnSelect: Button = itemView.findViewById(R.id.btn_select)

        fun bind(option: ResortOption) {
            ivOption.setImageResource(option.image)
            tvOptionName.text = option.name
            tvOptionDescription.text = getOptionDescription(option.name)
            tvPrice.text = getOptionPrice(option.name)

            btnSelect.setOnClickListener {
                onOptionSelect(option)
            }
        }

        private fun getOptionDescription(optionName: String): String {
            return when (optionName) {
                "Финский домик" -> "Уютный деревянный домик с сауной и террасой"
                "VIP Коттедж" -> "Роскошный коттедж с бассейном и видом на горы"
                "Семейный дом" -> "Просторный дом для большой семьи"
                "Студия" -> "Компактная студия для пары"
                "Горный шале" -> "Альпийский шале с камином"
                "Апартаменты" -> "Современные апартаменты с балконом"
                "Эко домик" -> "Экологически чистый домик в лесу"
                "Глэмпинг" -> "Комфортная палатка с удобствами"
                else -> "Комфортное размещение"
            }
        }

        private fun getOptionPrice(optionName: String): String {
            return when (optionName) {
                "Финский домик" -> "от 25,000 ₸/сутки"
                "VIP Коттедж" -> "от 45,000 ₸/сутки"
                "Семейный дом" -> "от 35,000 ₸/сутки"
                "Студия" -> "от 15,000 ₸/сутки"
                "Горный шале" -> "от 40,000 ₸/сутки"
                "Апартаменты" -> "от 30,000 ₸/сутки"
                "Эко домик" -> "от 20,000 ₸/сутки"
                "Глэмпинг" -> "от 18,000 ₸/сутки"
                else -> "от 20,000 ₸/сутки"
            }
        }
    }
}